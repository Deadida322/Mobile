package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.FragmentNewsFilterBinding
import com.example.todoapp.FilterCategoriesAdapter

class NewsFilter : Fragment() {
    lateinit var binding: FragmentNewsFilterBinding
    lateinit var recycler: RecyclerView
    lateinit var adapter: FilterCategoriesAdapter
    lateinit var categoriesList: ArrayList<HelpItem>
    private var categorie: String = ""

    private fun loadFragment(fragment: Fragment) {
        val fragmentManager = activity?.supportFragmentManager
        val fragmantTransaction = fragmentManager?.beginTransaction()
        fragmantTransaction?.replace(R.id.fragmentContainer, fragment)
        fragmantTransaction?.commit()
    }

    private fun itemClick() = { item: HelpItem ->
        categorie = item.value
        Unit
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsFilterBinding.inflate(inflater)
        recycler = binding.filterCategoriesRecycler
        recycler.layoutManager = LinearLayoutManager(requireContext())
        adapter = FilterCategoriesAdapter(requireContext(), itemClick())
        categoriesList = JSONReader(requireContext(), "categories.json", HelpItem::class.java).getList()
        adapter.setInfo(categoriesList)
        recycler.adapter = adapter

        binding.filterButton.setOnClickListener {
            val bundle = Bundle()
            val fragment = NewsScreen()
            bundle.putString("key", categorie)
            fragment.setArguments(bundle)
            loadFragment(fragment)
        }
        return binding.root
    }
}
