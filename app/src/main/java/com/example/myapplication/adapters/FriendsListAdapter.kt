package com.example.myapplication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.FriendsRecyclerItem
import com.utils.LoadImg

class FriendsListAdapter(val context: Context) :
    RecyclerView.Adapter<FriendsListAdapter.ViewHolder>() {
    //Заполнил от балды, по-хорошему надо вообще это от сервера получать, но это потом
    //Делать список в адаптере - плохо, но мне лень раскидывать
    private val friendsRecyclerData = mutableListOf(
        FriendsRecyclerItem(
            pic = "@drawable/profile_image1",
            name = "Дмитрий Валерьевич"
        ),
        FriendsRecyclerItem(
            pic = "@drawable/profile_image2",
            name = "Не Дмитрий Валерьевич"
        ),
        FriendsRecyclerItem(
            pic = "@drawable/profile_image3",
            name = "Дмитрий Не Валерьевич"
        ),
        FriendsRecyclerItem(
            pic = "@drawable/profile_image1",
            name = "Дмитрич"
        ),
        FriendsRecyclerItem(
            pic = "@drawable/profile_image2",
            name = "Гервант из Рыбли"
        ),
        FriendsRecyclerItem(
            pic = "@drawable/profile_image3",
            name = "Чел"
        )
    )

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pic: ImageView = itemView.findViewById(R.id.friend_profile_pic)
        val name: TextView = itemView.findViewById(R.id.friend_profile_name)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FriendsListAdapter.ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.friend_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FriendsListAdapter.ViewHolder, position: Int) {
        val item = friendsRecyclerData[position]
        LoadImg(context, item.pic, holder.pic)
        holder.name.text = item.name
    }

    override fun getItemCount(): Int {
        return friendsRecyclerData.size
    }

}