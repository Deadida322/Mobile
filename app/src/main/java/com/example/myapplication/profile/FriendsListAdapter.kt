package com.example.myapplication.profile

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.utils.LoadImg

class FriendsListAdapter(val context: Context) :
    RecyclerView.Adapter<FriendsListAdapter.ViewHolder>() {
    var friendsList = mutableListOf<FriendItem>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pic: ImageView = itemView.findViewById(R.id.friends_item_image)
        val name: TextView = itemView.findViewById(R.id.friends_item_name)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FriendsListAdapter.ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.friends_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FriendsListAdapter.ViewHolder, position: Int) {
        val item = friendsList[position]
        LoadImg(context, item.img, holder.pic)
        holder.name.text = item.name
    }

    override fun getItemCount(): Int {
        return friendsList.size
    }
    @SuppressLint("NotifyDataSetChanged")
    fun setInfo(tmpArr: ArrayList<FriendItem>) {
        friendsList.clear()
        friendsList.addAll(tmpArr)
        notifyDataSetChanged()
    }
}