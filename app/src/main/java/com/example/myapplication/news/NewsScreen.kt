package com.example.myapplication.news
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentNewsScreenBinding
import com.example.myapplication.retrofit.Common
import com.utils.JSONReader
import com.utils.addFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        if (category.isEmpty()) return list
        return list.filter {
            it.categories.contains(category)
        } as ArrayList
    }
    private fun getObservableNews() {
        Observable
            .fromCallable { JSONReader(requireContext(), resources.getString(R.string.news_file), NewsItem::class.java).getList() }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                list = it
                binding.newsProgress.visibility = View.GONE
                adapter.differ.submitList(filterNews())
            }
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
        activity?.supportFragmentManager?.setFragmentResultListener(FILTER_REQUEST_KEY, viewLifecycleOwner) { _, bundle ->
            category = bundle.getString(SORT_KEY).toString()
            adapter.differ.submitList(filterNews())
        }

        if (savedInstanceState != null) {
            list = savedInstanceState.getParcelableArrayList<NewsItem>(NEWS_LIST) as ArrayList<NewsItem>
            binding.newsProgress.visibility = View.GONE
            adapter.differ.submitList(filterNews())
        } else {
            Common().retrofitServices.getNewsList().enqueue(object : Callback<MutableList<NewsItem>> {
                override fun onResponse(
                    call: Call<MutableList<NewsItem>>,
                    response: Response<MutableList<NewsItem>>
                ) {
                    if (response.body() == null) {
                        Log.d("errorNetwork", response.toString())
                        getObservableNews()
                    } else {
                        list = response.body() as List<NewsItem> as ArrayList<NewsItem> /* = java.util.ArrayList<com.example.myapplication.news.NewsItem> */
                        adapter.differ.submitList(filterNews())
                    }
                }

                override fun onFailure(call: Call<MutableList<NewsItem>>, t: Throwable) {
                    Log.d("errorNetwork", t.toString())
                }
            })
        }

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(NEWS_LIST, list)
    }
}
