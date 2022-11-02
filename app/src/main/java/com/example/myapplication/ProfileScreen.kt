package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapters.FriendsListAdapter
import com.example.myapplication.databinding.NewProfileScreenBinding

class ProfileScreen : Fragment() {
    lateinit var binding: NewProfileScreenBinding
    lateinit var adapter: FriendsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = NewProfileScreenBinding.inflate(inflater)
        adapter = FriendsListAdapter(requireContext())
        with(binding) {
            editProfileButton.setOnClickListener {
                startActivity(Intent(context, EditProfileActivity()::class.java))
            }
            friendsRecycler.layoutManager = LinearLayoutManager(context)
            friendsRecycler.adapter = adapter
        }
        return binding.root
    }
}
