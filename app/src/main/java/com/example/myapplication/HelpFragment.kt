package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.FragmentHelpBinding
import com.example.todoapp.HelpAdapter
import com.google.android.flexbox.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.lang.reflect.Type
import java.util.*

// ktlint-disable no-wildcard-imports

class HelpFragment : Fragment() {
    lateinit var binding: FragmentHelpBinding
    lateinit var adapter: HelpAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var list: ArrayList<HelpItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun <T> getList(jsonArray: String?, clazz: Class<T>?): ArrayList<HelpItem> {
        val typeOfT: Type? = TypeToken.getParameterized(MutableList::class.java, clazz).type
        return Gson().fromJson(jsonArray, typeOfT)
    }

    private fun getJsonDataFromAsset(context: Context, fileName: String): String {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return ""
        }
        return jsonString
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHelpBinding.inflate(inflater)
        recyclerView = binding.RecyclerViewFragment
        adapter = HelpAdapter(requireContext())

        val jsonString: String = getJsonDataFromAsset(requireContext(), "categories.json")
        println(jsonString)
        list = getList(jsonString, HelpItem::class.java)
        Log.i(
            "yebat",
            list.toString()
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
