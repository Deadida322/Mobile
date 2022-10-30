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
import com.example.myapplication.databinding.FragmentHelpBinding
import com.google.android.flexbox.*
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
        val intentNewsIntentService = Intent(activity, NewsIntentService::class.java)
        val mMyBroadcastReceiver = MyBroadcastReceiver()
        val intentFilter = IntentFilter(
            ACTION_LOAD_CATEGORIES
        )
        NewsIntentService.enqueueWork(requireContext(), intentNewsIntentService)

        intentFilter.addCategory(Intent.CATEGORY_DEFAULT)
        activity?.registerReceiver(mMyBroadcastReceiver, intentFilter)
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
            activity?.startService(intentNewsIntentService)
        }

        return binding.root
    }
    inner class MyBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val result = intent
                .getParcelableArrayListExtra<HelpItem>(EXTRA_KEY_OUT)
            if (result != null) {
                list = result
                adapter.setInfo(result)
            }
            binding.categoriesProgress.visibility = View.GONE
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (list.size != 0) {
            outState.putParcelableArrayList(HELP_ITEMS_KEY, list)
        }
    }
}
