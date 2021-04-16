package com.anomalydev.videogamefinder.framework.datasource.cache.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anomalydev.videogamefinder.framework.datasource.cache.model.GameEntity

@Dao
interface GameDao {

    // Will insert a game to the database
    @Insert
    suspend fun insertGame(game: GameEntity): Long

   // Will insert a list of games to the database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGames(games: List<GameEntity>): LongArray

    // Will get a game by it's id
    @Query("SELECT * FROM games WHERE id IN (:id)")
    suspend fun getGameById(id: Int): GameEntity?

    // Will delete a list a games by their id's
    @Query("DELETE FROM games WHERE id IN (:ids)")
    suspend fun deleteGames(ids: List<Int>): Int

    // Will delete all games from the database
    @Query("DELETE FROM games")
    suspend fun deleteAllGames()

//    // Will delete a game by it's id
    @Query("DELETE FROM games WHERE id = :primaryKey")
    suspend fun deleteGame(primaryKey: Int): Int
}