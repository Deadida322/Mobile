package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.FragmentManager
import com.example.myapplication.auth.AuthScreen
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.help.HelpScreen
import com.example.myapplication.news.NewsItem
import com.example.myapplication.news.NewsScreen
import com.example.myapplication.profile.ProfileScreen
import com.example.myapplication.search.SearchScreen
import com.utils.JSONReader
import com.utils.loadFragment
import kotlinx.coroutines.*

const val IS_LOADED_KEY = "IS_LOADED"

class MainActivity : AppCompatActivity() {
    private var newsCount = 0
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val isLoaded = savedInstanceState?.getBoolean(IS_LOADED_KEY)

        if (isLoaded == null) {
            loadFragment(supportFragmentManager, AuthScreen(), R.id.fragmentContainer)
        }
        binding.bottomMenu.selectedItemId = R.id.help
        binding.bottomMenu.setOnItemSelectedListener {
            selectFragment(it.itemId)
        }
        newsCount = JSONReader(this, "news.json", NewsItem::class.java).getList().size
        binding.bottomMenu.getOrCreateBadge(R.id.news).number = newsCount
        getScope()
        setContentView(binding.root)
    }

    suspend fun getResult() {
        coroutineScope {
            NewsFlow.outputData().collect {
                binding.bottomMenu.getOrCreateBadge(R.id.news).number = newsCount - it
            }
        }
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    private fun getScope() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                getResult()
            } catch (e: Exception) {
                Log.d("tag", e.toString())
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(IS_LOADED_KEY, true)
    }

    private fun selectFragment(item: Int): Boolean {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        when (item) {
            R.id.news -> {
                loadFragment(supportFragmentManager, NewsScreen(), R.id.fragmentContainer)
                return true
            }
            R.id.profile -> {
                loadFragment(supportFragmentManager, ProfileScreen(), R.id.fragmentContainer)
                return true
            }
            R.id.search -> {
                loadFragment(supportFragmentManager, SearchScreen(), R.id.fragmentContainer)
                return true
            }
            R.id.help -> {
                loadFragment(supportFragmentManager, HelpScreen(), R.id.fragmentContainer)
                return true
            }
        }
        return true
    }
}
