package com.example.appwithcats.domain

data class GetVotes(
    val countryCode: Boolean,
    val createdAt: String,
    val id: Int,
    val image_id: String,
    val subId: Boolean,
    val value: Boolean
)
