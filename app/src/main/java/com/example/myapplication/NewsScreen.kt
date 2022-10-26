package com.example.myapplication

import android.os.Bundle
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
    lateinit var category: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        category = ""
        arguments?.let {
            category = it.getString("key").toString()
        }
    }
    private fun onItemClick() = { item: NewsItem ->
        val id = item.id
        val bundle = Bundle()
        bundle.putInt("id", id)
        val fragment = NewsDetail()
        fragment.setArguments(bundle)
        loadFragment(fragment)
    }
    private fun loadFragment(fragment: Fragment) {
        val fragmentManager = activity?.supportFragmentManager
        val fragmantTransaction = fragmentManager?.beginTransaction()
        fragmantTransaction?.add(R.id.fragmentContainer, fragment)
        fragmantTransaction?.commit()
    }

    private fun filterNews(): ArrayList<NewsItem> {
        if (category == "") return list
        return list.filter {
            it.categories.contains(category)
        } as ArrayList
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsScreenBinding.inflate(inflater)
        recyclerView = binding.newsRecycler
        adapter = NewsAdapter(requireContext(), onItemClick())
        list = JSONReader(requireContext(), "news.json", NewsItem::class.java).getList() as ArrayList<NewsItem>
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
