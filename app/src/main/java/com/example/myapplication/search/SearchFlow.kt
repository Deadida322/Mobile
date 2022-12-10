package com.example.myapplication.search

import kotlinx.coroutines.flow.MutableSharedFlow

class SearchFlow {
    companion object {
        private val flow = MutableSharedFlow<String>()

        fun outputFlow(): MutableSharedFlow<String> {
            return flow
        }
    }
}
