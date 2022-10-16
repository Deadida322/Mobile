package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.example.myapplication.databinding.ActivitySearchBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class Search : FragmentActivity() {
    lateinit var binding: ActivitySearchBinding
    lateinit var adapter: SearchAdapter
    lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = SearchAdapter(this)
        tabLayout = binding.tabLayout
        binding.viewPager2.adapter = adapter
        TabLayoutMediator(tabLayout, binding.viewPager2) { tab, position ->
            when (position){
                0 -> tab.text = "По мероприятию"
                1 -> tab.text = "По НКО"
            }
        }.attach()
    }
}
