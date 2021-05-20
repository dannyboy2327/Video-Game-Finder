package com.anomalydev.videogamefinder.interactors.game_list

import com.anomalydev.videogamefinder.BuildConfig
import com.anomalydev.videogamefinder.business.interactors.game.BookmarkState
import com.anomalydev.videogamefinder.business.interactors.game_list.GetFavoriteGames
import com.anomalydev.videogamefinder.business.interactors.game_list.SearchGames
import com.anomalydev.videogamefinder.cache.AppDatabaseFake
import com.anomalydev.videogamefinder.cache.GameDaoFake
import com.anomalydev.videogamefinder.framework.datasource.cache.util.GameEntityMapper
import com.anomalydev.videogamefinder.framework.datasource.network.abstraction.GameService
import com.anomalydev.videogamefinder.framework.datasource.network.util.GameDtoMapper
import com.anomalydev.videogamefinder.network.data.MockWebServerResponses
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class GetFavoriteGamesTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl
    private val appDatabaseFake = AppDatabaseFake()
    private val DUMMY_KEY = BuildConfig.GAMES_API_ACCESS_KEY
    private val DUMMY_QUERY = ""

    // system in test
    private lateinit var getFavoriteGames: GetFavoriteGames

    // dependencies
    private lateinit var gameService: GameService
    private lateinit var gameDaoFake: GameDaoFake
    private lateinit var searchGames: SearchGames
    private lateinit var bookmarkState: BookmarkState
    private val dtoMapper = GameDtoMapper()
    private val entityMapper = GameEntityMapper()

    @BeforeEach
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        baseUrl = mockWebServer.url("/api/games/")
        gameService = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(GameService::class.java)

        gameDaoFake = GameDaoFake(appDatabaseFake)

        searchGames = SearchGames(
            gameDao = gameDaoFake,
            gameService = gameService,
            dtoMapper = dtoMapper,
            entityMapper = entityMapper,
        )

        bookmarkState = BookmarkState(
            gameDao = gameDaoFake,
            entityMapper = entityMapper,
        )

        // instantiate system in test
        getFavoriteGames = GetFavoriteGames(
            gameDao = gameDaoFake,
            entityMapper = entityMapper,
        )
    }

    /**
     * 1. Try to get a list of games from the network and update a games bookmark state
     * and retrieve the correct list of of games with isFavorite is true
     * Result should be:
     * a. Games are retrieved from network and inserted into cache
     * b. Fetch a Game from cache and change bookmarks value from 0 to 1
     * c. Update the cache with the updated game
     * d. Retrieve the correct amount of games with isFavorite = 1
     */
    @Test
    fun attemptUpdateGameBookmarkState_getUpdatedGames(): Unit = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(MockWebServerResponses.GAME_LIST_RESPONSE)
        )

        // confirm cache is empty
        assert(gameDaoFake.getAllGames(1, 1).isEmpty())

        // get games from network and insert into cache
        val searchResult = searchGames.execute(DUMMY_KEY, 1,DUMMY_QUERY, 3).toList()

        // confirm cache is no longer empty
        assert(gameDaoFake.getAllGames(1, 3).isNotEmpty())

        // confirm each game is not a favorite
        for (game in gameDaoFake.getAllGames(1, 3)) {
            assert(!game.isFavorite)
        }

        // change a each games isFavorite state
        for (game in gameDaoFake.getAllGames(1, 3)) {
            bookmarkState.execute(entityMapper.mapToDomainModel(game))
        }

        // get all favorite games from the cache
        val getFavoriteGamesAsFlow = getFavoriteGames.execute().toList()

        // first emission should be loading
        assert(getFavoriteGamesAsFlow[0].loading)

        // second emission should be a list of favorite games
        val favoriteGames = getFavoriteGamesAsFlow[1].data
        favoriteGames?.let {
            for (game in it) {

                // confirm each games isFavorite state is true
                assert(game.isFavorite)
            }
        }

        // third emission should be loading is false
        assert(!getFavoriteGamesAsFlow[1].loading)

    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }
}