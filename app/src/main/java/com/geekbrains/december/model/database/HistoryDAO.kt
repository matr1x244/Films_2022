package com.geekbrains.december.model.database

import androidx.room.*
import kotlin.math.absoluteValue

/**
 * Запросы и т.д в базу данных (интерфейс)
 * onConflict = OnConflictStrategy.REPLACE ??? не работает вставляет одинаковые данные
 */

@Dao
interface HistoryDAO {
    @Query("SELECT * FROM HistoryEntity")
    fun all(): List<HistoryEntity>

    @Query("SELECT * FROM HistoryEntity WHERE nameMovieEntity LIKE :movie")
    fun getDataByWord(movie: String): List<HistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: HistoryEntity)

    @Update
    fun update(entity: HistoryEntity)

    @Delete
    fun delete(entity: HistoryEntity)

    @Query("DELETE FROM HistoryEntity WHERE nameMovieEntity = :nameMovie")
    fun deleteByMovieName(nameMovie: String?)

    @Query("DELETE FROM HistoryEntity")
    fun deleteAll()

}
