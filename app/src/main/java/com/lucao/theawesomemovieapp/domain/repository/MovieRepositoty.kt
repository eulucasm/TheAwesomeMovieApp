package com.lucao.theawesomemovieapp.domain.repository

import com.lucao.theawesomemovieapp.domain.model.Movie
import com.lucao.theawesomemovieapp.domain.model.Poster

interface MovieRepository {

    suspend fun getMovieData(): Result<List<Movie>?>
    suspend fun getMovieDetail(movie: Movie): Result<Movie?>
    suspend fun getMoviePosters(movie: Movie): Result<List<Poster>?>
    suspend fun saveLocalData(movieList: List<Movie>?)
    suspend fun clearLocalData()
}