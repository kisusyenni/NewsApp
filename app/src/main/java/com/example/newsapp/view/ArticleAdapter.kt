package com.example.newsapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.data.local.entity.Article
import com.example.newsapp.databinding.ArticleItemBinding
import com.example.newsapp.utils.formatDate

class ArticleAdapter :
    ListAdapter<Article, ArticleAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemArticleBinding =
            ArticleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemArticleBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = getItem(position)

        if (article != null) {
            holder.bind(article)
        }
    }

    inner class ViewHolder(private val binding: ArticleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            with(binding) {
                tvArticleTitle.text = article.title
                tvPublishedAt.text = formatDate(article.publishedAt)
                tvArticleDescription.text = article.description
                tvArticleAuthor.text = article.author
                Glide.with(itemView.context)
                    .load(article.urlToImage)
                    .placeholder(R.drawable.preview)
                    .error(R.drawable.image_error)
                    .into(ivArticle)
                
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(
                oldItem: Article,
                newItem: Article
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}