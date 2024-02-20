package com.lucao.theawesomemovieapp.data.local.datasource

import com.lucao.theawesomemovieapp.data.local.dao.MovieDao
import com.lucao.theawesomemovieapp.domain.datasource.MovieDataSource
import com.lucao.theawesomemovieapp.domain.model.Movie
import com.lucao.theawesomemovieapp.domain.model.Poster
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieDataBaseDataSource @Inject constructor() : MovieDataSource {

    @Inject
    lateinit var movieDao: MovieDao
    override suspend fun getMovieData(): Result<List<Movie>?> = withContext(Dispatchers.IO) {
        Result.success(movieDao.selectAll())
    }

    override suspend fun saveLocalData(movieList: List<Movie>?) {
        withContext(Dispatchers.IO) {
            movieList?.let {
                movieDao.insertFromList(it)
            }
        }
    }

    override suspend fun clearLocalData() {
        withContext(Dispatchers.IO) {
            movieDao.deleteAll()
        }
    }

    override suspend fun getMovieDetails(movie: Movie): Result<Movie?> {
        TODO("NO-OP YET")
    }

    override suspend fun getMoviePosters(movie: Movie): Result<List<Poster>?> {
        TODO("NO-OP YET")
    }

}