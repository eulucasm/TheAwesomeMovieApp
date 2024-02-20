package com.lucao.theawesomemovieapp.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.lucao.theawesomemovieapp.domain.model.Movie
import com.lucao.theawesomemovieapp.util.Constants

@Dao
interface MovieDao : BaseDao<Movie> {

    @Query("SELECT * FROM ${Constants.MOVIE_TABLE}")
    suspend fun selectAll(): List<Movie>?

    @Query("SELECT * FROM ${Constants.MOVIE_TABLE} WHERE id = :id")
    suspend fun selectOneById(id: Int): Movie?

    @Transaction
    @Query("DELETE FROM ${Constants.MOVIE_TABLE}")
    suspend fun deleteAll()

}