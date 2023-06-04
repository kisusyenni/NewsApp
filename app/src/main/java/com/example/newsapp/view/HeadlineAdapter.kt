package com.example.newsapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.data.local.entity.Article
import com.example.newsapp.databinding.HeadlineItemBinding
import com.example.newsapp.utils.formatDate

class HeadlineAdapter :
    ListAdapter<Article, HeadlineAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemHeadlineBinding =
            HeadlineItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemHeadlineBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = getItem(position)

        if (article != null) {
            holder.bind(article)
        }
    }

    inner class ViewHolder(private val binding: HeadlineItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            with(binding) {
                tvHeadlineTitle.text = article.title
                tvHeadlinePublishedAt.text = formatDate(article.publishedAt)
                tvHeadlineDescription.text = article.description
                tvSource.text = article.source?.name ?: "No Source"
                Glide.with(itemView.context)
                    .load(article.urlToImage)
                    .placeholder(R.drawable.preview)
                    .error(R.drawable.image_error)
                    .into(ivHeadline)

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