package com.lucao.theawesomemovieapp.data.model.image

import com.lucao.theawesomemovieapp.domain.model.Poster
import com.lucao.theawesomemovieapp.util.Constants

data class PosterDto(
    val aspect_ratio: Double,
    val file_path: String,
    val height: Int,
    val iso_639_1: String,
    val vote_average: Double,
    val vote_count: Int,
    val width: Int
)

fun PosterDto.toPoster(): Poster {
    return Poster(posterPath = "${Constants.IMG_URL}${Constants.IMG_SIZE}${file_path}")
}
