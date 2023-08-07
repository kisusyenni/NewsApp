package com.example.newsapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.data.local.entity.Article
import com.example.newsapp.data.local.entity.ErrorMessage
import com.example.newsapp.data.remote.network.ApiConfig
import com.example.newsapp.data.remote.response.NewsResponse
import com.squareup.moshi.Moshi
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _isHeadlineLoading = MutableLiveData<Boolean>()
    val isHeadlineLoading: LiveData<Boolean> = _isHeadlineLoading

    private val _isArticleLoading = MutableLiveData<Boolean>()
    val isArticleLoading: LiveData<Boolean> = _isArticleLoading

    private val _listHeadlines = MutableLiveData<List<Article>?>()
    val listHeadlines: LiveData<List<Article>?> = _listHeadlines

    private val _listNews = MutableLiveData<List<Article>?>()
    val listNews: LiveData<List<Article>?> = _listNews

    private val _error = MutableLiveData<ErrorMessage?>()
    val error: LiveData<ErrorMessage?> = _error

    fun getTopHeadlines() {
        _isHeadlineLoading.value = true
        val client = ApiConfig.getApiService().loadHeadlines()
        client.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(
                call: Call<NewsResponse>,
                response: Response<NewsResponse>
            ) {
                _isHeadlineLoading.value = false
                if (response.isSuccessful) {
                    _listHeadlines.value = response.body()?.articles
                    _error.value = null
                } else {
                    _error.value = convertErrorBody(response.errorBody())
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                _isHeadlineLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    private fun convertErrorBody(errorBody: ResponseBody?): ErrorMessage? {
        return try {
            errorBody?.source()?.let {
                val moshiAdapter = Moshi.Builder().build().adapter(ErrorMessage::class.java)
                moshiAdapter.fromJson(it)
            }
        } catch (exception: Exception) {
            null
        }
    }

    fun getAllNews() {
        _isArticleLoading.value = true
        val client = ApiConfig.getApiService().loadAllNews()
        client.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(
                call: Call<NewsResponse>,
                response: Response<NewsResponse>
            ) {
                _isArticleLoading.value = false
                if (response.isSuccessful) {
                    _listNews.value = response.body()?.articles
                    _error.value = null
                } else {
                    _error.value = convertErrorBody(response.errorBody())
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                _isArticleLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
    init {
        _isHeadlineLoading.value = false
        _isArticleLoading.value = false
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}