package com.example.myapplication.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentProfileScreenBinding
import com.utils.JSONReader
import kotlinx.android.synthetic.main.news_item.*

class ProfileScreen : Fragment() {
    lateinit var binding: FragmentProfileScreenBinding
    lateinit var adapter: FriendsListAdapter
    lateinit var friendList: ArrayList<FriendItem>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileScreenBinding.inflate(inflater)
        friendList = JSONReader(
            requireContext(),
            resources.getString(R.string.friends_file),
            FriendItem::class.java
        ).getList()
        binding.apply {
            editButton.setOnClickListener {
                startActivity(Intent(context, EditProfileActivity()::class.java))
            }
            adapter = FriendsListAdapter(requireContext())
            adapter.setInfo(friendList)
            friendsRecycler.adapter = adapter
            friendsRecycler.layoutManager = LinearLayoutManager(context)
        }
        return binding.root
    }
}
