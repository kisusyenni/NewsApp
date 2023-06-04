package com.example.newsapp.data.remote.response

import com.example.newsapp.data.local.entity.Article
import com.squareup.moshi.Json

data class NewsResponse(
	@Json(name = "articles")
	var articles: List<Article>?,
	@Json(name = "status")
	var status: String?,
	@Json(name = "totalResults")
	var totalResults: Int?
)