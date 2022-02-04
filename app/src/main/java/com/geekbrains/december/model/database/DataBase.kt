package com.geekbrains.december.model.database

import androidx.room.Room
import androidx.room.RoomDatabase
import com.geekbrains.december.App

@androidx.room.Database(
    entities = [
        HistoryEntity::class
        //HistoryLike::class //если еще подключать таблицы
    ],
    version = 1,
    exportSchema = false
)

abstract class DataBase: RoomDatabase() {
    abstract fun HistoryDAO(): HistoryDAO

    companion object{
       private const val DB_NAME = "movie_database.db"

        val db: DataBase by lazy {
            Room.databaseBuilder(
                App.appInstance,
                DataBase::class.java,
                DB_NAME
            ).build()
        }

    }


}