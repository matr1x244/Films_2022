package com.geekbrains.december.model.database

import androidx.room.ColumnInfo
import androidx.room.ColumnInfo.TEXT
import androidx.room.Entity
import androidx.room.PrimaryKey
import coil.size.Precision
import coil.size.Scale
import com.geekbrains.december.R

/**
 * Модели База данных
 *
 */
@Entity
data class HistoryEntity(

        @PrimaryKey(autoGenerate = true)
        val idMovieEntity: Int,
        val nameMovieEntity: String?,
        val tmdbMovieEntity: Double?,
        val yearMovieEntity: Int?,
        val posterMovieEntity: String?
)

