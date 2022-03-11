package com.example.appwithcats.domain


data class CatModel(
    val breeds: List<Any>,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int,
    var like: Boolean? = null
)
