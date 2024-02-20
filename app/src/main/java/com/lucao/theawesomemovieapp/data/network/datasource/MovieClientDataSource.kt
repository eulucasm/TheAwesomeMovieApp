package com.lucao.theawesomemovieapp.data.network.datasource

import com.lucao.theawesomemovieapp.data.model.image.toPoster
import com.lucao.theawesomemovieapp.data.model.movie.toMovie
import com.lucao.theawesomemovieapp.data.model.movieDetail.toMovie
import com.lucao.theawesomemovieapp.data.network.MovieService
import com.lucao.theawesomemovieapp.domain.datasource.MovieDataSource
import com.lucao.theawesomemovieapp.domain.model.Movie
import com.lucao.theawesomemovieapp.domain.model.Poster
import com.lucao.theawesomemovieapp.util.Constants
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieClientDataSource @Inject constructor() : MovieDataSource {

    @Inject
    lateinit var service: MovieService

    override suspend fun getMovieData(): Result<List<Movie>?> = withContext(Dispatchers.IO) {

        val response = service.getMovieList(Constants.API_KEY)

        when {
            response.isSuccessful -> Result.success(response.body()?.items?.map { it.toMovie() })
            else -> Result.failure(Throwable(response.message()))
        }
    }

    override suspend fun getMovieDetails(movie: Movie): Result<Movie?> =
        withContext(Dispatchers.IO) {

            val response = service.getMovieDetail(movieId = movie.id, Constants.API_KEY)

            when {
                response.isSuccessful -> Result.success(
                    response.body()?.toMovie()
                )
                else -> Result.failure(Throwable(response.message()))
            }
        }

    override suspend fun getMoviePosters(movie: Movie): Result<List<Poster>?> =
        withContext(Dispatchers.IO) {
            val response = service.getMovieImages(movieId = movie.id, Constants.API_KEY)

            when {
                response.isSuccessful -> Result.success(response.body()?.posters?.map { it.toPoster() })
                else -> Result.failure(Throwable(response.message()))
            }
        }

    override suspend fun saveLocalData(movieList: List<Movie>?) {
        //NO-OP
    }

    override suspend fun clearLocalData() {
        //NO-OP
    }
}