package com.example.todoapp

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.NewsItem
import com.example.myapplication.databinding.NewsItemBinding

class NewsAdapter(val context: Context) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    lateinit var binding: NewsItemBinding
    private val news = mutableListOf<NewsItem>()
    inner class ViewHolder() : RecyclerView.ViewHolder(binding.root) {
        val img: ImageView = binding.newsImage
        val title: TextView = binding.newsTitle
        val description: TextView = binding.newsDescription
        val date: TextView = binding.newsDate

        @SuppressLint("UseCompatLoadingForDrawables")
        fun setData(item: NewsItem) {
            val id: Int = context.getResources().getIdentifier(item.img, "drawable", context.packageName)
            val img = context.getResources().getDrawable(id, context.getApplicationContext().getTheme())
            binding.apply {
                newsImage.background = img
                newsTitle.text = item.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.ViewHolder {
        binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: NewsAdapter.ViewHolder, position: Int) {
        holder.setData(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount() = differ.currentList.size

    private val differCallback = object : DiffUtil.ItemCallback<NewsItem>() {
        override fun areItemsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)
}
