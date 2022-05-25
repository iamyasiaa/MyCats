package com.example.appwithcats.domain

data class GetVotes(
    val countryCode: Boolean,
    val createdAt: String,
    val id: Int,
    val imageId: String,
    val subId: Boolean,
    val value: Boolean
)
