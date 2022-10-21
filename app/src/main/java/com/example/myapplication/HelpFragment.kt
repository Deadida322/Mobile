package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.FragmentHelpBinding
import com.example.todoapp.HelpAdapter
import com.google.android.flexbox.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HelpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HelpFragment : Fragment() {
    lateinit var binding: FragmentHelpBinding
    lateinit var adapter: HelpAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var list: ArrayList<HelpItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHelpBinding.inflate(inflater)
        recyclerView = binding.RecyclerViewFragment
        adapter = HelpAdapter()
        list = ArrayList<HelpItem>(
            listOf(
                HelpItem(R.drawable.childs, "Дети"),
                HelpItem(R.drawable.adult, "Взрослые"),
                HelpItem(R.drawable.elder, "Пожилые"),
                HelpItem(R.drawable.animals, "Животные"),
                HelpItem(R.drawable.events, "Меропориятия")
            )
        )
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
    companion object {
        @JvmStatic
        fun newInstance() = HelpFragment()
    }
}
