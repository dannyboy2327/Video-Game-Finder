package com.anomalydev.videogamefinder.interactors.game

import com.anomalydev.videogamefinder.BuildConfig
import com.anomalydev.videogamefinder.business.domain.model.Game
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

class GetGameTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl
    private val appDatabaseFake = AppDatabaseFake()
    private val DUMMY_KEY = BuildConfig.GAMES_API_ACCESS_KEY
    private val DUMMY_QUERY = ""

    // system in test
    private lateinit var getGame: GetGame
    private val GAME_ID = 3498

    // dependencies
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

        // instantiate system in test
        getGame = GetGame(
            gameDao = gameDaoFake,
            gameService = gameService,
            entityMapper = entityMapper,
            dtoMapper = dtoMapper,
        )
    }

    /**
     * 1. Get some games from the network and insert into cache
     * 2. Try to retrieve games by their specific game id
     */
    @Test
    fun getGamesFromNetwork_getGameById(): Unit = runBlocking {

        // Condition the response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(MockWebServerResponses.gameListResponse)
        )

        // confirm the cache is empty to start
        assert(gameDaoFake.getAllGames(1, 3).isEmpty())

        // get recipes from network and insert into cache
        val searchResult = searchGames.execute(DUMMY_KEY, 1, DUMMY_QUERY, 3).toList()

        // confirm the cache is no longer empty
        assert(gameDaoFake.getAllGames(1, 3).isNotEmpty())

        // run use case
        val gameAsFlow = getGame.execute(GAME_ID, DUMMY_KEY).toList()

        // first emission should be `loading`
        assert(gameAsFlow[0].loading)

        // second emission should be the recipe
        val game = gameAsFlow[1].data
        assert(game?.id == GAME_ID)

        // confirm it is actually a Recipe object
        assert(game is Game)

        // 'loading' should be false now
        assert(!gameAsFlow[1].loading)
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }
}