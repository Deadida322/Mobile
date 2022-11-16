package com.example.myapplication.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.FragmentEventSearchBinding
import com.example.myapplication.news.NewsItem
import com.utils.JSONReader
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

const val ARG_PARAM = "STRING_ARRAY"

class EventSearchFragment : Fragment() {
    lateinit var binding: FragmentEventSearchBinding
    var list: ArrayList<String> = arrayListOf()
    var isEvent = false
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: SearchItemAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.takeIf { it.containsKey(ARG_PARAM) }?.apply {
            list = getStringArrayList(ARG_PARAM)!!
            Log.i("TAG", list.toString())
        }
        arguments?.takeIf { it.containsKey(EVENTS_KEY) }?.apply {
            isEvent = getString(EVENTS_KEY, EVENTS_KEY) == EVENTS_KEY
            Log.i("TAG", list.toString())
        }
        binding = FragmentEventSearchBinding.inflate(inflater)
        if (isEvent) {
            list = getStringArray(JSONReader(requireContext(), "news.json", NewsItem::class.java).getList())
        }
        recyclerView = binding.searchRecycler
        adapter = SearchItemAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter.setInfo(list)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isEvent) {
            RxBus.listen().subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    adapter.setInfo(searchByQuery(it))
                }
        }
    }
    private fun getStringArray(lst: ArrayList<NewsItem>): ArrayList<String> {
        val result = arrayListOf<String>()
        for (i in lst) {
            i.title?.let { result.add(it) }
        }
        return result
    }
    private fun searchByQuery(query: String): ArrayList<String> {
        return list.filter { it.contains(query) } as ArrayList
    }
}
