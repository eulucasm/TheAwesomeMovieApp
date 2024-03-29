package com.lucao.theawesomemovieapp.data.model.movieDetail

import com.lucao.theawesomemovieapp.domain.model.Movie
import com.lucao.theawesomemovieapp.util.Constants

data class MovieDetailDto(
    val adult: Boolean,
    val backdrop_path: String,
    val belongs_to_collection: Any,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    val imdb_id: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: Any,
    val production_companies: List<ProductionCompany>,
    val production_countries: List<ProductionCountry>,
    val release_date: String,
    val revenue: Int,
    val runtime: Int,
    val spoken_languages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)

fun MovieDetailDto.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        description = overview,
        image = "${Constants.IMG_URL}${Constants.IMG_SIZE}${poster_path}",
        rating = "$vote_average%",
        dateRelease = release_date,
    )
}