package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentNewsDetailBinding
import com.example.todoapp.ContributorsAdapter
import com.utils.LoadImg
import com.utils.loadFragment
import com.utils.toTime

class NewsDetail : Fragment() {
    lateinit var binding: FragmentNewsDetailBinding
    private var newsId: Int = 0
    lateinit var news: NewsItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            newsId = it.getInt("id")
        }
        news = JSONReader(requireContext(), "news.json", NewsItem::class.java)
            .getList()
            .filter { it.id == newsId }[0]
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var manager = activity?.supportFragmentManager
        binding = FragmentNewsDetailBinding.inflate(inflater)
        binding.apply {
            backButton.setOnClickListener {
                loadFragment(manager, NewsScreen(), R.id.fragmentContainer)
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
            adapter.setInfo(contributorsList as ArrayList<String>)
            contributorsRecycler.adapter = adapter
            contributorsRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
        return binding.root
    }
}
