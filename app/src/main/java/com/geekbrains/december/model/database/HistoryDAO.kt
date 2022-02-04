package com.geekbrains.december.model.database

import androidx.room.*

/**
 * Запросы и т.д в базу данных
 */
@Dao
interface HistoryDAO {
    @Query("SELECT * FROM HistoryEntity")
    fun all(): List<HistoryEntity>

    @Query("SELECT * FROM HistoryEntity WHERE name LIKE :name")
    fun getDataByWord(name: String): List<HistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: HistoryEntity)

    @Update
    fun update(entity: HistoryEntity)

    @Delete
    fun delete(entity: HistoryEntity)

    @Query("DELETE FROM HistoryEntity WHERE name = :movieName")
    fun deleteBymMovieName(movieName: String)
}
