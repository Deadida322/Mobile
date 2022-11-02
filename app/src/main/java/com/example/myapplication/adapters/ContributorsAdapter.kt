package com.example.myapplication.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ContributorsItemBinding
import com.utils.LoadImg

class ContributorsAdapter(val context: Context) : RecyclerView.Adapter<ContributorsAdapter.ViewHolder>() {
    lateinit var binding: ContributorsItemBinding
    private var list: ArrayList<String> = mutableListOf<String>() as ArrayList<String>

    inner class ViewHolder() : RecyclerView.ViewHolder(binding.root) {
        fun setData(item: String) {
            LoadImg(context, item, binding.contributorAvatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContributorsAdapter.ViewHolder {
        binding = ContributorsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setInfo(tmpArr: ArrayList<String>) {
        list.clear()
        list.addAll(tmpArr)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(list[position])
    }
}
