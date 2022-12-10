package com.example.myapplication.news

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.NewsFlow
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentNewsDetailBinding
import com.example.myapplication.profile.ContributorsAdapter
import com.utils.JSONReader
import com.utils.LoadImg
import com.utils.toTime
import kotlinx.coroutines.*
import kotlin.collections.ArrayList

class NewsDetail : Fragment() {
    lateinit var binding: FragmentNewsDetailBinding
    private var newsId: Int = 0
    lateinit var news: NewsItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            newsId = it.getInt("id")
        }
        news = JSONReader(requireContext(), resources.getString(R.string.news_file), NewsItem::class.java)
            .getList()
            .filter { it.id == newsId }[0]
    }

    private fun getScope() {
        CoroutineScope(Dispatchers.IO).launch {
            if (!NewsFlow.readNews.contains(newsId)) {
                NewsFlow.readNews.add(newsId)
            }
            try {
                NewsFlow.outputData().emit(NewsFlow.readNews.size)
            } catch (e: Exception) {
                Log.d("tag", e.toString())
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val manager = activity?.supportFragmentManager
        binding = FragmentNewsDetailBinding.inflate(inflater)
        binding.apply {
            backButton.setOnClickListener {
                manager?.popBackStack()
            }
            appbarTitle.text = news.title
            newsDetailTitle.text = news.title
            newsDetailDescription.text = news.description
            newsDetailOrganization.text = news.organization
            newsDetailAddress.text = news.address
            newsDetailDate.text = news.started_at.toTime()
            newsDetailPhone1.text = news.phoneList.joinToString("\n")
            newsDetailMail.text = news.email
            newsDetailSite.text = news.site
            LoadImg(requireContext(), news.gallery[0], newsDetailImg1)
            LoadImg(requireContext(), news.gallery[1], newsDetailImage2)
            LoadImg(requireContext(), news.gallery[2], newsDetailImage3)
            val adapter = ContributorsAdapter(requireContext())
            var contributorsList = news.contributors
            if (contributorsList.size > 5) {
                overflowContributors.text = "+ ${contributorsList.size - 5}"
                contributorsList = ArrayList(contributorsList.subList(0, 5))
            }
            adapter.setInfo(contributorsList)
            contributorsRecycler.adapter = adapter
            contributorsRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            getScope()
        }
        return binding.root
    }
}
