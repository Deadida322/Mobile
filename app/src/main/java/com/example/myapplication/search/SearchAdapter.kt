package com.example.myapplication.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

const val EVENTS_KEY = "EVENTS"

class SearchAdapter(fragment: SearchScreen) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 2

    private fun getRandomString(length: Int): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

    private fun getRandomList(length: Int): List<String> {
        val list = mutableListOf<String>()
        for (i in 0..length) {
            list.add(getRandomString((8..200).random()))
        }
        return list
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = EventSearchFragment()
        if (position == 0) {
            fragment.arguments = Bundle().apply {
                putString(EVENTS_KEY, EVENTS_KEY)
            }
        } else {
            fragment.arguments = Bundle().apply {
                putStringArrayList(ARG_PARAM, ArrayList(getRandomList((4..8).random())))
            }
        }

        return fragment
    }
}
