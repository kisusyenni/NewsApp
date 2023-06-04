package com.example.newsapp.data.local.entity

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    @Json(name = "author")
    var author: String?,
    @Json(name = "content")
    var content: String?,
    @Json(name = "description")
    var description: String?,
    @Json(name = "publishedAt")
    var publishedAt: String,
    @Json(name = "source")
    var source: Source?,
    @Json(name = "title")
    var title: String?,
    @Json(name = "url")
    var url: String?,
    @Json(name = "urlToImage")
    var urlToImage: String?
): Parcelable