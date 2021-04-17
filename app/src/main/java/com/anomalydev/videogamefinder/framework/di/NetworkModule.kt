package com.anomalydev.videogamefinder.framework.di

import com.anomalydev.videogamefinder.BuildConfig
import com.anomalydev.videogamefinder.framework.datasource.network.abstraction.GameService
import com.anomalydev.videogamefinder.framework.datasource.network.util.GameDtoMapper
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideGameService(): GameService {
        return Retrofit.Builder()
            .baseUrl("https://api.rawg.io/api/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(GameService::class.java)
    }

    @Singleton
    @Provides
    @Named("auth_key")
    fun provideAuthToken(): String {
        return BuildConfig.GAMES_API_ACCESS_KEY
    }

    @Singleton
    @Provides
    fun provideDtoMapper(): GameDtoMapper {
        return GameDtoMapper()
    }
}