package com.example.appwithcats.domain.cats.votes

data class VoteModel(
    val id: Int,
    val message: String,
    val like: Boolean
)