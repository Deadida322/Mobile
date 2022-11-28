package com.example.myapplication

import kotlinx.coroutines.flow.MutableSharedFlow

class NewsFlow {
    companion object {
        val readNews = arrayListOf<Int>()
        private val flow = MutableSharedFlow<Int>()

        fun inputData(): ArrayList<Int> {
            return readNews
        }

        fun outputData(): MutableSharedFlow<Int> {
            return flow
        }
    }
}
