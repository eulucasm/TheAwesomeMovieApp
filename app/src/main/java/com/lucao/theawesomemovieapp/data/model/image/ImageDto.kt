package com.lucao.theawesomemovieapp.data.model.image

data class ImageDto (
    val backdrops: List<Backdrop>,
    val id: Int,
    val posters: List<PosterDto>
)