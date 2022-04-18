package com.example.appwithcats.domain

data class FavoritesModel(

    val id: String,
    val user_id: String,
    val image_id: String,
    val sub_id: String,
    val created_at: String,
    val image: Image,
    var favorites: Boolean?=null

){
    data class Image(
        val id: String,
        val url: String,
        var like: Boolean? = null,


    )
}

