package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.FragmentNewsScreenBinding
import com.example.todoapp.NewsAdapter

class NewsScreen : Fragment() {

    lateinit var binding: FragmentNewsScreenBinding
    lateinit var adapter: NewsAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var list: ArrayList<NewsItem>
    private var sortCategory: String = ""

    private fun loadFragment(fragment: Fragment) {
        val fragmentManager = activity?.supportFragmentManager
        val fragmantTransaction = fragmentManager?.beginTransaction()
        fragmantTransaction?.add(R.id.fragmentContainer, fragment)
        fragmantTransaction?.commit()
    }
    private fun filterNews(): ArrayList<NewsItem> {
        Log.e("infoNews", sortCategory.toString())
        if (sortCategory == "") return list
        return list.filter {
            it.categories.contains(sortCategory)
        } as ArrayList
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsScreenBinding.inflate(inflater)
        recyclerView = binding.newsRecycler
        adapter = NewsAdapter(requireContext())
        list = JSONReader(requireContext(), "news.json", NewsItem::class.java).getList() as ArrayList<NewsItem>
        sortCategory = getArguments()?.getString("key", "").toString()
        adapter.differ.submitList(filterNews())
        recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        binding.filterButton.setOnClickListener {
            loadFragment(NewsFilter())
        }
        return binding.root
    }
}
