package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class SearchAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 2

    private fun getRandomString(length: Int): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

    private fun getRandomList(length: Int): List<String> {
        var list = mutableListOf<String>()
        for (i in 0..length) {
            list.add(getRandomString((8..200).random()))
        }
        return list
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = EventSearchFragment()
        fragment.arguments = Bundle().apply {
            putStringArrayList(ARG_PARAM, ArrayList(getRandomList((4..8).random())))
        }
        return fragment
    }
}
