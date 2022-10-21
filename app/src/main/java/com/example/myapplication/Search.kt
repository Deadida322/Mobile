package com.example.myapplication

import android.content.Intent
import android.os.Bundle
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
        binding.bottomMenu.selectedItemId = R.id.help
        binding.bottomMenu.setOnItemReselectedListener {
            when (it.itemId) {
                R.id.profile -> {
                    val intent = Intent(this, Profile::class.java)
                    startActivity(intent)
                }
                R.id.help -> {
                    val intent = Intent(this, HelpCategory::class.java)
                    startActivity(intent)
                }
                R.id.search -> {
                    val intent = Intent(this, Search::class.java)
                    startActivity(intent)
                }
                else -> false
            }
        }
        TabLayoutMediator(tabLayout, binding.viewPager2) { tab, position ->
            when (position) {
                0 -> tab.text = "По мероприятию"
                1 -> tab.text = "По НКО"
            }
        }.attach()
    }
}
