package com.anomalydev.videogamefinder.framework.di

import androidx.room.Room
import com.anomalydev.videogamefinder.framework.datasource.cache.database.GameDao
import com.anomalydev.videogamefinder.framework.datasource.cache.database.GameDatabase
import com.anomalydev.videogamefinder.framework.datasource.cache.database.GameDatabase.Companion.DATABASE_NAME
import com.anomalydev.videogamefinder.framework.datasource.cache.util.Converter
import com.anomalydev.videogamefinder.framework.datasource.cache.util.GameEntityMapper
import com.anomalydev.videogamefinder.framework.presentation.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideDatabase(app: BaseApplication): GameDatabase {
        return Room
            .databaseBuilder(app, GameDatabase::class.java, DATABASE_NAME)
            .addTypeConverter(Converter())
            .fallbackToDestructiveMigrationOnDowngrade()
            .build()
    }

    @Singleton
    @Provides
    fun provideGameDao(db: GameDatabase): GameDao {
        return db.gameDao()
    }

    @Singleton
    @Provides
    fun provideCacheGameMapper(): GameEntityMapper {
        return GameEntityMapper()
    }
}