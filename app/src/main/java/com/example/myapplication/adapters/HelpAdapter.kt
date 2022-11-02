package com.example.myapplication.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.HelpItem
import com.example.myapplication.R
import com.utils.LoadImg

class HelpAdapter(val context: Context) : RecyclerView.Adapter<HelpAdapter.ViewHolder>() {

    private val categories = mutableListOf<HelpItem>()
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.helpImages)
        val text: TextView = itemView.findViewById(R.id.helpText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelpAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.help_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HelpAdapter.ViewHolder, position: Int) {
        val tmpItem = categories[position]
        LoadImg(context, tmpItem.img, holder.img)
        holder.text.text = tmpItem.txt
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setInfo(tmpArr: ArrayList<HelpItem>) {
        categories.clear()
        categories.addAll(tmpArr)
        notifyDataSetChanged()
    }
}
