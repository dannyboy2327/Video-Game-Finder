package com.anomalydev.videogamefinder.interactors.game

import com.anomalydev.videogamefinder.BuildConfig
import com.anomalydev.videogamefinder.business.domain.model.Game
import com.anomalydev.videogamefinder.business.interactors.game.BookmarkState
import com.anomalydev.videogamefinder.business.interactors.game.GetGame
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

class BookmarkStateTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl
    private val appDatabaseFake = AppDatabaseFake()
    private val DUMMY_KEY = BuildConfig.GAMES_API_ACCESS_KEY
    private val GAME_ID = 3498


    // system in test
    private lateinit var bookmarkState: BookmarkState

    // dependencies
    private lateinit var getGame: GetGame
    private lateinit var searchGames: SearchGames
    private lateinit var gameService: GameService
    private lateinit var gameDaoFake: GameDaoFake
    private val dtoMapper =  GameDtoMapper()
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

        getGame = GetGame(
            gameDao = gameDaoFake,
            gameService = gameService,
            entityMapper = entityMapper,
            dtoMapper = dtoMapper,
        )

        // instantiate system in test
        bookmarkState = BookmarkState(
            gameDao = gameDaoFake,
            entityMapper = entityMapper,
        )
    }

    /**
     * 1. Try to get a game from network and update it's bookmark state
     * Result should be:
     * a. Game is retrieved from network and inserted into cache
     * b. Game's bookmark value is changed from 0 to 1 and vise versa
     */
    @Test
    fun attemptUpdateGameBookmarkState_getByGameId(): Unit = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(MockWebServerResponses.GAME_RESPONSE_WITH_ID_3498)
        )

        // confirm cache is empty
        assert(gameDaoFake.getAllGames(1, 1).isEmpty())

        // get a game from the network
        val gameAsFlow = getGame.execute(GAME_ID, DUMMY_KEY).toList()

        // confirm cache is no longer empty
        assert(gameDaoFake.getAllGames(1, 1).isNotEmpty())

        // confirm the bookmark state is false for the game
        val game = gameAsFlow[1].data
        assert(game?.isFavorite == false)

        // change a games bookmark state
        val gameUpdatedAsFlow = bookmarkState.execute(game!!).toList()

        // confirm  the games bookmark state was changed
        val updatedGame = gameUpdatedAsFlow[0].data
        assert(updatedGame?.isFavorite == true)
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }
}