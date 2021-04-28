package com.anomalydev.videogamefinder.interactors.game_list

import com.anomalydev.videogamefinder.BuildConfig
import com.anomalydev.videogamefinder.business.domain.model.Game
import com.anomalydev.videogamefinder.business.interactors.game_list.RestoreGames
import com.anomalydev.videogamefinder.business.interactors.game_list.SearchGames
import com.anomalydev.videogamefinder.cache.AppDatabaseFake
import com.anomalydev.videogamefinder.cache.GameDaoFake
import com.anomalydev.videogamefinder.framework.datasource.cache.util.GameEntityMapper
import com.anomalydev.videogamefinder.framework.datasource.network.abstraction.GameService
import com.anomalydev.videogamefinder.framework.datasource.network.util.GameDtoMapper
import com.anomalydev.videogamefinder.network.data.MockWebServerResponses
import com.anomalydev.videogamefinder.util.Constants
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

class RestoreGamesTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl
    private val appDatabaseFake = AppDatabaseFake()
    private val DUMMY_KEY = BuildConfig.GAMES_API_ACCESS_KEY
    private val DUMMY_QUERY = ""

    // system in test
    private lateinit var restoreGames: RestoreGames

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
        restoreGames = RestoreGames(
            gameDao = gameDaoFake,
            entityMapper = entityMapper,
        )
    }

    /**
     * 1. Get some games from the network
     * 2. Restore and show games are retrieved from the cache
     */
    @Test
    fun getGamesFromNetwork_restoreFromCache(): Unit = runBlocking {

        // Condition the response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(MockWebServerResponses.gameListResponse)
        )

        assert(gameDaoFake.getAllGames(page = 1, pageSize = Constants.PAGE_SIZE,).isEmpty())

        val searchResult = searchGames.execute(
            DUMMY_KEY,
            1,
            DUMMY_QUERY,
            Constants.PAGE_SIZE
        ).toList()

        assert(gameDaoFake.getAllGames(1, Constants.PAGE_SIZE).isNotEmpty())

        // Run our use case
        val flowItems = restoreGames.execute(1, DUMMY_QUERY).toList()

        assert(flowItems[0].loading)

        val games = flowItems[1].data
        assert(games?.size?: 0 > 0)

        assert(games?.get(0) is Game)

        assert(!flowItems[1].loading)

    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }
}