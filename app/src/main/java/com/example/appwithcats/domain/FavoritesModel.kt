package com.example.appwithcats.domain

data class FavoritesModel(

    val id: String,
    val user_id: String,
    val image_id: String,
    val sub_id: String,
    val created_at: String,
    val image: List<Image>


){
    data class Image(
        val id: String,
        val url: String
    )
}


