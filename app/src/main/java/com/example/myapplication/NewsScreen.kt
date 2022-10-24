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
    private fun loadFragment(fragment: Fragment) {
        val fragmentManager = activity?.supportFragmentManager
        val fragmantTransaction = fragmentManager?.beginTransaction()
        fragmantTransaction?.add(R.id.fragmentContainer, fragment)
        fragmantTransaction?.commit()
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
        adapter = NewsAdapter()
        list = ArrayList(
            listOf(
                NewsItem(1, R.drawable.childs, 1092030302, "Здравствуй осень в сапогах", "Здравствуй небо в облаках")
            )
        )
        recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        adapter.setInfo(list)
        binding.filterButton.setOnClickListener{
            loadFragment(NewsFilter())
        }
        return binding.root
    }
}
