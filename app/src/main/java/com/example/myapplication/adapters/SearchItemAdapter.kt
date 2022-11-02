package com.example.myapplication.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class SearchItemAdapter() : RecyclerView.Adapter<SearchItemAdapter.ViewHolder>() {

    private val list = mutableListOf<String>()
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text: TextView = itemView.findViewById(R.id.searchItemText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.search_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SearchItemAdapter.ViewHolder, position: Int) {
        val tmpItem = list[position]
        holder.text.text = tmpItem
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setInfo(tmpArr: MutableList<String>) {
        list.clear()
        list.addAll(tmpArr)
        notifyDataSetChanged()
    }
}
