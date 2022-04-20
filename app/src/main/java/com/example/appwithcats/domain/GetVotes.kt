package com.example.appwithcats.domain

data class GetVotes(
    val country_code: Boolean,
    val created_at: String,
    val id: Int,
    val image_id: String,
    val sub_id: Boolean,
    val value: Boolean
)
