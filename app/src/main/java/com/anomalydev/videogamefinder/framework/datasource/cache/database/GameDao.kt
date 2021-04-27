package com.anomalydev.videogamefinder.framework.datasource.cache.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anomalydev.videogamefinder.framework.datasource.cache.model.GameEntity
import com.anomalydev.videogamefinder.util.Constants
import com.anomalydev.videogamefinder.util.Constants.PAGE_SIZE

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

    // Will delete a game by it's id
    @Query("DELETE FROM games WHERE id = :primaryKey")
    suspend fun deleteGame(primaryKey: Int): Int

    /**
     *  Retrieve games for a page
     *  Ex. page = 2 retrieves games from 30-60.
     *  Ex. page = 3 retrieves games from 60-90.
     */

    @Query("""
        SELECT * FROM games
        WHERE name LIKE '%' || :query || '%'
        ORDER BY updated DESC LIMIT :pageSize OFFSET ((:page - 1) * :pageSize)
    """)
    suspend fun searchGames(
        query: String,
        page: Int,
        pageSize: Int = PAGE_SIZE,
    ): List<GameEntity>

    /**
     *  Retrieve games for a page with no query
     */
    @Query("""
        SELECT * FROM games
        ORDER BY updated DESC LIMIT :pageSize OFFSET ((:page - 1) * :pageSize)
    """)
    suspend fun getAllGames(
        page: Int,
        pageSize: Int = PAGE_SIZE,
    ): List<GameEntity>

    /**
     * Restore games after process death
     */
    @Query("""
        SELECT * FROM games
        WHERE name LIKE '%' || :query || '%'
        ORDER BY updated DESC LIMIT (:page * :pageSize)
    """)
    suspend fun restoreGames(
        query: String,
        page: Int,
        pageSize: Int = PAGE_SIZE,
    ): List<GameEntity>

    /**
     * Same as 'restoreGames' but with no query
     */
    @Query("""
        SELECT * FROM games
        ORDER BY updated DESC LIMIT (:page * :pageSize)
    """)
    suspend fun restoreAllGames(
        page: Int,
        pageSize: Int = PAGE_SIZE,
    ): List<GameEntity>
}