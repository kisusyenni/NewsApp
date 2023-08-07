package com.example.newsapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.data.local.entity.Article
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setViewModel()
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.getTopHeadlines()
        viewModel.getAllNews()

        viewModel.isHeadlineLoading.observe(this) { loading ->
            showHeadlineLoading(loading)
        }

        viewModel.isArticleLoading.observe(this) { loading ->
            showArticleLoading(loading)
        }

        viewModel.listHeadlines.observe(this) { headlines ->
            if (headlines != null) {
                if(headlines.isNotEmpty()) setHeadlinesData(headlines)
            }
        }

        viewModel.listNews.observe(this) { news ->
            if (news != null) {
                if(news.isNotEmpty()) setNewsData(news)
            }
        }

        viewModel.error.observe(this) { res ->
            if (res != null) {
                if (res.status == "error") {
                    binding.tvError.text = if(res.message != "") res.message else resources.getString(R.string.load_data_error)
                    binding.tvError.visibility = View.VISIBLE
                } else {
                    Toast.makeText(this@MainActivity, resources.getString(R.string.load_data_succeed), Toast.LENGTH_LONG).show()
                    binding.tvError.visibility = View.GONE
                }
            }
        }
    }

    private fun setHeadlinesData(news: List<Article>) {
        val headlineAdapter = HeadlineAdapter()
        headlineAdapter.submitList(news)
        binding.rvHeadlineList.apply {
            adapter = headlineAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }
    }

    private fun setNewsData(news: List<Article>) {
        val articleAdapter = ArticleAdapter()
        articleAdapter.submitList(news)
        binding.rvArticleList.apply {
            adapter = articleAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    private fun showHeadlineLoading(loading: Boolean) {
        binding.pbMainHeadline.visibility = if (loading) View.VISIBLE else View.GONE
    }

    private fun showArticleLoading(loading: Boolean) {
        binding.pbMainArticle.visibility = if (loading) View.VISIBLE else View.GONE
    }
}