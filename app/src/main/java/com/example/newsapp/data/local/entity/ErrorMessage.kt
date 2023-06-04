package com.example.newsapp.data.local.entity

import com.squareup.moshi.Json

data class ErrorMessage(
    @Json(name = "status")
    val status: String,
    @Json(name = "code")
    val code: String?,
    @Json(name = "message")
    val message: String?,
)
