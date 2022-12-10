package com.example.myapplication.help

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHelpBinding
import com.google.android.flexbox.*
import com.utils.JSONReader
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlin.collections.ArrayList
const val HELP_ITEMS_KEY = "HELP_ITEMS_KEY"
class HelpFragment : Fragment() {
    lateinit var binding: FragmentHelpBinding
    lateinit var adapter: HelpAdapter
    lateinit var recyclerView: RecyclerView
    var list = ArrayList<HelpItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val intentFilter = IntentFilter(
            ACTION_LOAD_CATEGORIES
        )

        intentFilter.addCategory(Intent.CATEGORY_DEFAULT)
        binding = FragmentHelpBinding.inflate(inflater)
        recyclerView = binding.RecyclerViewFragment
        adapter = HelpAdapter(requireContext())
        recyclerView.adapter = adapter
        val layoutManager = FlexboxLayoutManager(context).apply {
            justifyContent = JustifyContent.SPACE_BETWEEN
            alignItems = AlignItems.CENTER
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
        }
        recyclerView.layoutManager = layoutManager
        if (savedInstanceState != null) {
            val res = savedInstanceState.getParcelableArrayList<HelpItem>(HELP_ITEMS_KEY)
            if (res != null) {
                list = res
                adapter.setInfo(res)
                binding.categoriesProgress.visibility = View.GONE
            }
        }
        if (list.size == 0) {
            Observable
                .fromCallable{JSONReader(requireContext(), resources.getString(R.string.categories_file), HelpItem::class.java).getList()}
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    list = it
                    binding.categoriesProgress.visibility = View.GONE
                    adapter.setInfo(list)
                }
        }

        return binding.root
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (list.size != 0) {
            outState.putParcelableArrayList(HELP_ITEMS_KEY, list)
        }
    }
}
