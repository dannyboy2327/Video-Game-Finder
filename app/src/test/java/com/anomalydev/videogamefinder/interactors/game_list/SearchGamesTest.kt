package com.anomalydev.videogamefinder.interactors.game_list

import com.anomalydev.videogamefinder.BuildConfig
import com.anomalydev.videogamefinder.business.domain.model.Game
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

class SearchGamesTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl
    private val appDatabaseFake = AppDatabaseFake()
    private val DUMMY_KEY = BuildConfig.GAMES_API_ACCESS_KEY
    private val DUMMY_QUERY = ""

    // system in test
    private lateinit var searchGames: SearchGames

    // dependencies
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

        // instantiate system in test
       searchGames = SearchGames(
           gameDao = gameDaoFake,
           gameService = gameService,
           dtoMapper = dtoMapper,
           entityMapper = entityMapper,
        )
    }

    /**
     * 1. Are the games retrieved from the network
     * 2. Are the games inserted into the cache?
     * 3. Are the games then emitted as a Flow from the cache to the UI
     */
    @Test
    fun getGamesFromNetwork_emitGamesFromCache(): Unit = runBlocking {

        // Condition the response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(MockWebServerResponses.gameListResponse)
        )

        // confirm the cache is empty to start
        assert(gameDaoFake.getAllGames(1, 3).isEmpty())

        val flowItems = searchGames.execute(
            DUMMY_KEY,
            1,
            DUMMY_QUERY,
            3,
        ).toList()

        // Confirm the cache is no longer empty
        assert(gameDaoFake.getAllGames(1, 3).isNotEmpty())

        // first emission should be loading status
        assert(flowItems[0].loading)

        // second emission should be list of games
        val games = flowItems[1].data
        assert(games?.size?: 0 > 0)

        // confirm they are actually game objects
        assert(games?.get(0) is Game)

        // Ensure loading is false now
        assert(!flowItems[1].loading)
    }

    @Test
    fun getRecipesFromNetwork_emitHttpError(): Unit = runBlocking {
        // Condition the response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .setBody("{}")
        )

        val flowItems = searchGames.execute(
            DUMMY_KEY,
            1,
            DUMMY_QUERY,
            3,
        ).toList()

        // first emission should be `loading`
        assert(flowItems[0].loading)

        // Second emission should be the exception
        val error = flowItems[1].error
        assert(error != null)

        // loading should be false now
        assert(!flowItems[1].loading)
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }
}