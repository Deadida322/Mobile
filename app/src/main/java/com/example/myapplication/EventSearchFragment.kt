package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.FragmentEventSearchBinding
import com.example.myapplication.adapters.SearchItemAdapter

const val ARG_PARAM = ""

class EventSearchFragment : Fragment() {
    lateinit var binding: FragmentEventSearchBinding
    lateinit var list: List<String>
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: SearchItemAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.takeIf { it.containsKey(ARG_PARAM) }?.apply {
            list = getStringArrayList(ARG_PARAM)!!
        }
        binding = FragmentEventSearchBinding.inflate(inflater)
        recyclerView = binding.searchRecycler
        adapter = SearchItemAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter.setInfo(list as ArrayList<String>)
        return binding.root
    }
}
