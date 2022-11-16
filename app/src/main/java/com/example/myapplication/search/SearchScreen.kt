package com.example.myapplication.search

import RxBus
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSearchScreenBinding
import com.example.myapplication.news.NewsItem
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.jakewharton.rxbinding.widget.RxSearchView
import com.utils.JSONReader
import kotlinx.android.synthetic.main.fragment_search_screen.*
import java.util.concurrent.TimeUnit

class SearchScreen : Fragment() {
    lateinit var binding: FragmentSearchScreenBinding
    lateinit var adapter: SearchAdapter
    lateinit var tabLayout: TabLayout
    lateinit var events: ArrayList<NewsItem>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchScreenBinding.inflate(inflater)
        events = JSONReader(
            requireContext(),
            resources.getString(R.string.news_file),
            NewsItem::class.java
        ).getList()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        RxSearchView.queryTextChanges(searcher)
            .debounce(500, TimeUnit.MILLISECONDS)
            .map { it.toString() }
            .subscribe {
                RxBus.publish(it.toString())
            }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun searchFilter(query: String): ArrayList<NewsItem> {
        if (query == "") return events
        return events.filter {
            it.title.toString().contains(query)
        } as ArrayList
    }
}
