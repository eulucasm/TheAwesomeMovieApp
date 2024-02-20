package com.lucao.theawesomemovieapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lucao.theawesomemovieapp.data.local.dao.MovieDao
import com.lucao.theawesomemovieapp.domain.model.Movie

@Database(
    entities = [Movie::class], version = 1, exportSchema = false
)
abstract class MovieDataBase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}