package com.lucao.theawesomemovieapp.data.repository

import com.lucao.theawesomemovieapp.data.local.datasource.MovieDataBaseDataSource
import com.lucao.theawesomemovieapp.data.network.datasource.MovieClientDataSource
import com.lucao.theawesomemovieapp.domain.model.Movie
import com.lucao.theawesomemovieapp.domain.model.Poster
import com.lucao.theawesomemovieapp.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieClientDataSource: MovieClientDataSource,
    private val movieDataBaseDataSource: MovieDataBaseDataSource
) : MovieRepository {

    override suspend fun getMovieData(): Result<List<Movie>?> {

        return try {
            val result = movieClientDataSource.getMovieData()

            if (result.isSuccess) {
                persistData(result.getOrNull())
                result
            } else {
                getLocalData()
            }

        } catch (e: Exception) {
            getLocalData()
        }

    }

    private suspend fun getLocalData(): Result<List<Movie>?> =
        movieDataBaseDataSource.getMovieData()

    private suspend fun persistData(movieList: List<Movie>?) {
        movieDataBaseDataSource.clearLocalData()
        movieList?.let {
            movieDataBaseDataSource.saveLocalData(it)
        }
    }

    override suspend fun saveLocalData(movieList: List<Movie>?) {
        movieList?.let {
            movieDataBaseDataSource.saveLocalData(it)
        }
    }

    override suspend fun clearLocalData() {
        movieDataBaseDataSource.clearLocalData()
    }

    override suspend fun getMovieDetail(movie: Movie): Result<Movie?> {
        return try {
            movieClientDataSource.getMovieDetails(movie)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMoviePosters(movie: Movie): Result<List<Poster>?> {
        return try {
            movieClientDataSource.getMoviePosters(movie)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}