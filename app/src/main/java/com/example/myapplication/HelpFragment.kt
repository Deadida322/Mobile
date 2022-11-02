package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.FragmentHelpBinding
import com.example.myapplication.adapters.HelpAdapter
import com.google.android.flexbox.*
import java.util.*

class HelpFragment : Fragment() {
    lateinit var binding: FragmentHelpBinding
    lateinit var adapter: HelpAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var list: ArrayList<HelpItem>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHelpBinding.inflate(inflater)
        recyclerView = binding.RecyclerViewFragment
        adapter = HelpAdapter(requireContext())

        list = JSONReader(requireContext(), "categories.json", HelpItem::class.java).getList()

        recyclerView.adapter = adapter
        val layoutManager = FlexboxLayoutManager(context).apply {
            justifyContent = JustifyContent.SPACE_BETWEEN
            alignItems = AlignItems.CENTER
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
        }
        recyclerView.layoutManager = layoutManager
        adapter.setInfo(list)
        return binding.root
    }
}
