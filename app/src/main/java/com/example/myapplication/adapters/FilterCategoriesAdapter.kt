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

class FilterCategoriesAdapter(val context: Context, private val onItemClick: ((HelpItem) -> Unit)?) : RecyclerView.Adapter<FilterCategoriesAdapter.ViewHolder>() {
    private val list = mutableListOf<HelpItem>()
    private var selectedPos = RecyclerView.NO_POSITION

    @SuppressLint("ResourceAsColor")
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.filterCategoriesLogo)
        val text: TextView = itemView.findViewById(R.id.filterCategoriesTitle)
        init {
            itemView.setOnClickListener {
                notifyItemChanged(selectedPos)
                selectedPos = layoutPosition
                notifyItemChanged(selectedPos)
                onItemClick?.invoke(list[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterCategoriesAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.filter_categies_item, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: FilterCategoriesAdapter.ViewHolder, position: Int) {
        val tmpItem = list[position]
        holder.itemView.setSelected(selectedPos == position)
        val id: Int = context.getResources().getIdentifier(tmpItem.img, "drawable", context.packageName)
        val img = context.getResources().getDrawable(id, context.getApplicationContext().getTheme())
        holder.img.background = img
        holder.text.text = tmpItem.txt
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setInfo(tmpArr: ArrayList<HelpItem>) {
        list.clear()
        list.addAll(tmpArr)
        notifyDataSetChanged()
    }
}
