package com.example.myapplication.news
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentNewsScreenBinding
import com.utils.JSONReader
import com.utils.addFragment
import com.utils.runOnUiThread
import java.util.concurrent.Executors

const val NEWS_LIST = "NEWS_LIST"
class NewsScreen : Fragment() {

    lateinit var binding: FragmentNewsScreenBinding
    lateinit var adapter: NewsAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var list: ArrayList<NewsItem>
    lateinit var category: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        category = ""
        arguments?.let {
            category = it.getString(SORT_KEY).toString()
        }
    }
    private fun onItemClick() = { item: NewsItem ->
        val id = item.id
        val bundle = Bundle()
        bundle.putInt("id", id)
        val fragment = NewsDetail()
        fragment.arguments = bundle
        addFragment(activity?.supportFragmentManager, fragment, R.id.fragmentContainer)
    }

    private fun filterNews(): ArrayList<NewsItem> {
        if (category == "") return list
        return list.filter {
            it.categories.contains(category)
        } as ArrayList
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsScreenBinding.inflate(inflater)
        recyclerView = binding.newsRecycler
        adapter = NewsAdapter(requireContext(), onItemClick())
        recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        binding.filterButton.setOnClickListener {
            addFragment(activity?.supportFragmentManager, NewsFilter(), R.id.fragmentContainer)
        }
        val service = Executors.newSingleThreadExecutor()
        activity?.supportFragmentManager?.setFragmentResultListener(FILTER_REQUEST_KEY, viewLifecycleOwner) { _, bundle ->
            category = bundle.getString(SORT_KEY).toString()
            adapter.differ.submitList(filterNews())
        }

        if (savedInstanceState != null) {
            list = savedInstanceState.getParcelableArrayList<NewsItem>(NEWS_LIST) as ArrayList<NewsItem>
            binding.newsProgress.visibility = View.GONE
            adapter.differ.submitList(filterNews())
        } else {
            service.execute {
                Thread.sleep(2000)
                list =
                    JSONReader(requireContext(), resources.getString(R.string.news_file), NewsItem::class.java).getList()
                runOnUiThread {
                    binding.newsProgress.visibility = View.GONE
                    adapter.differ.submitList(filterNews())
                }
            }
        }

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(NEWS_LIST, list)
    }
}
