package com.example.myapplication.news

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.NewsItemBinding
import com.utils.LoadImg
import com.utils.toTime

class NewsAdapter(val context: Context, private val onItemClick: ((NewsItem) -> Unit)?) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    lateinit var binding: NewsItemBinding

    inner class ViewHolder() : RecyclerView.ViewHolder(binding.root) {
        fun setData(item: NewsItem) {
            binding.apply {
                item.img?.let { LoadImg(context, it, newsImage) }
                newsTitle.text = item.title
                newsDescription.text = item.description
                newsDate.text = item.started_at.toTime()
            }
        }
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(differ.currentList[position])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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
