package com.lucao.theawesomemovieapp.data.network

import com.lucao.theawesomemovieapp.data.model.image.ImageDto
import com.lucao.theawesomemovieapp.data.model.movie.MovieDto
import com.lucao.theawesomemovieapp.data.model.movieDetail.MovieDetailDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/latest")
    suspend fun getMovieList(
        @Query("api_key") apiKey: String
    ): Response<MovieDto>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<MovieDetailDto>

    @GET("movie/{movie_id}/images")
    suspend fun getMovieImages(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<ImageDto>

}