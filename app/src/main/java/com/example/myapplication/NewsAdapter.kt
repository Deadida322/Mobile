package com.example.todoapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.NewsItem
import com.example.myapplication.R

class NewsAdapter() : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private val news = mutableListOf<NewsItem>()
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.newsImage)
        val title: TextView = itemView.findViewById(R.id.newsTitle)
        val description: TextView = itemView.findViewById(R.id.newsDescription)
        val date: TextView = itemView.findViewById(R.id.newsDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NewsAdapter.ViewHolder, position: Int) {
        val tmpItem = news[position]
        holder.img.setImageResource(tmpItem.img)
        holder.title.text = tmpItem.title
    }

    override fun getItemCount(): Int {
        return news.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setInfo(tmpArr: ArrayList<NewsItem>) {
        news.clear()
        news.addAll(tmpArr)
        notifyDataSetChanged()
    }
}
