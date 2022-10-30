package com.example.myapplication.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSearchScreenBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class SearchScreen : Fragment() {
    lateinit var binding: FragmentSearchScreenBinding
    lateinit var adapter: SearchAdapter
    lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchScreenBinding.inflate(inflater)
        adapter = SearchAdapter(this)
        tabLayout = binding.tabLayout
        binding.viewPager2.adapter = adapter
        TabLayoutMediator(tabLayout, binding.viewPager2) { tab, position ->
            when (position) {
                0 -> tab.text = resources.getString(R.string.by_events)
                1 -> tab.text = resources.getString(R.string.by_nko)
            }
        }.attach()
        return binding.root
    }
}
