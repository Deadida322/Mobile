package com.example.myapplication.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.help.HelpItem
import com.utils.JSONReader
import com.example.myapplication.databinding.FragmentNewsFilterBinding

const val SORT_KEY = "SORT_KEY"
const val FILTER_REQUEST_KEY = "FILTER_REQUEST_KEY"
class NewsFilter : Fragment() {
    lateinit var binding: FragmentNewsFilterBinding
    lateinit var recycler: RecyclerView
    lateinit var adapter: FilterCategoriesAdapter
    lateinit var categoriesList: ArrayList<HelpItem>
    private var categorie: String = ""

    private fun itemClick() = { item: HelpItem ->
        categorie = item.value as String
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsFilterBinding.inflate(inflater)
        recycler = binding.filterCategoriesRecycler
        recycler.layoutManager = LinearLayoutManager(requireContext())
        adapter = FilterCategoriesAdapter(requireContext(), itemClick())
        categoriesList = JSONReader(requireContext(), resources.getString(R.string.categories_file), HelpItem::class.java).getList()
        adapter.setInfo(categoriesList)
        recycler.adapter = adapter
        val manager = activity?.supportFragmentManager
        binding.apply {
            filterButton.setOnClickListener {
                val bundle = Bundle()
                val fragment = NewsScreen()
                bundle.putString(SORT_KEY, categorie)
                fragment.arguments = bundle
                manager?.setFragmentResult(
                    FILTER_REQUEST_KEY,
                    bundleOf(SORT_KEY to categorie)
                )
                manager?.popBackStack()
            }
            backButton.setOnClickListener {
                manager?.popBackStack()
            }
        }

        return binding.root
    }
}
