package com.geekbrains.december.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Модели База данных
 */
@Entity
data class HistoryEntity(
        @PrimaryKey(autoGenerate = true) val id: Long,
        val name: String
)
