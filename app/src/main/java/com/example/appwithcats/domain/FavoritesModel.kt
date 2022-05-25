package com.example.appwithcats.domain

data class FavoritesModel(

    val id: String,
    val userId: String,
    val imageId: String,
    val subId: String,
    val createdAt: String,
    val image: Image,
    var favorites: Boolean=false

){
    data class Image(
        val id: String,
        val url: String,
        var like: Boolean? = null,


    )
}


