package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.FragmentNewsFilterBinding
import com.example.myapplication.adapters.FilterCategoriesAdapter
import com.utils.loadFragment
const val SORT_KEY = "key"
class NewsFilter : Fragment() {
    lateinit var binding: FragmentNewsFilterBinding
    lateinit var recycler: RecyclerView
    lateinit var adapter: FilterCategoriesAdapter
    lateinit var categoriesList: ArrayList<HelpItem>
    private var categorie: String = ""

    private fun itemClick() = { item: HelpItem ->
        categorie = item.value
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
        categoriesList = JSONReader(requireContext(), "categories.json", HelpItem::class.java).getList()
        adapter.setInfo(categoriesList)
        recycler.adapter = adapter
        val manager = activity?.supportFragmentManager
        binding.apply {
            filterButton.setOnClickListener {
                val bundle = Bundle()
                val fragment = NewsScreen()
                bundle.putString(SORT_KEY, categorie)
                fragment.arguments = bundle
                loadFragment(manager, fragment, R.id.fragmentContainer)
            }
            backButton.setOnClickListener {
                loadFragment(manager, NewsScreen(), R.id.fragmentContainer)
            }
        }

        return binding.root
    }
}
