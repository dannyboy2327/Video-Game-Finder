package com.anomalydev.videogamefinder.framework.di

import com.anomalydev.videogamefinder.business.interactors.game.BookmarkState
import com.anomalydev.videogamefinder.business.interactors.game.GetGame
import com.anomalydev.videogamefinder.business.interactors.game_list.GetFavoriteGames
import com.anomalydev.videogamefinder.business.interactors.game_list.RestoreGames
import com.anomalydev.videogamefinder.business.interactors.game_list.SearchGames
import com.anomalydev.videogamefinder.framework.datasource.cache.database.GameDao
import com.anomalydev.videogamefinder.framework.datasource.cache.util.GameEntityMapper
import com.anomalydev.videogamefinder.framework.datasource.network.abstraction.GameService
import com.anomalydev.videogamefinder.framework.datasource.network.util.GameDtoMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object InteractorsModule {

    @ViewModelScoped
    @Provides
    fun provideSearchGames(
        gameService: GameService,
        gameDao: GameDao,
        gameEntityMapper: GameEntityMapper,
        gameDtoMapper: GameDtoMapper,
    ): SearchGames {
        return SearchGames(
            gameDao = gameDao,
            gameService = gameService,
            entityMapper = gameEntityMapper,
            dtoMapper = gameDtoMapper,
        )
    }

    @ViewModelScoped
    @Provides
    fun provideGetFavoriteGames(
        gameDao: GameDao,
        entityMapper: GameEntityMapper,
    ): GetFavoriteGames {
        return GetFavoriteGames(
            gameDao = gameDao,
            entityMapper = entityMapper,
        )
    }

    @ViewModelScoped
    @Provides
    fun provideRestoreGames(
        gameDao: GameDao,
        entityMapper: GameEntityMapper,
    ): RestoreGames {
        return RestoreGames(
            gameDao = gameDao,
            entityMapper = entityMapper,
        )
    }

    @ViewModelScoped
    @Provides
    fun provideGetGame(
        gameDao: GameDao,
        gameService: GameService,
        entityMapper: GameEntityMapper,
        dtoMapper: GameDtoMapper,
    ): GetGame {
        return GetGame(
            gameDao = gameDao,
            gameService = gameService,
            entityMapper = entityMapper,
            dtoMapper = dtoMapper,
        )
    }

    @ViewModelScoped
    @Provides
    fun provideBookmarkState(
        gameDao: GameDao,
        entityMapper: GameEntityMapper,
    ): BookmarkState{
        return BookmarkState(
            gameDao = gameDao,
            entityMapper = entityMapper,
        )
    }
}