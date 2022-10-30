package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.FragmentManager
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.help.HelpScreen
import com.example.myapplication.news.NewsScreen
import com.example.myapplication.profile.ProfileScreen
import com.example.myapplication.search.SearchScreen
import com.utils.loadFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val isLoaded = savedInstanceState?.getBoolean("key")
        if (isLoaded == null) {
            loadFragment(supportFragmentManager, HelpScreen(), R.id.fragmentContainer)
        }
        binding.bottomMenu.selectedItemId = R.id.help
        binding.bottomMenu.setOnItemSelectedListener { it ->
            selectFragment(it.itemId)
        }

        setContentView(binding.root)
    }
    override fun onBackPressed() {
        val count = getSupportFragmentManager().getBackStackEntryCount()
        if (count == 0) {
            super.onBackPressed()
        } else {
            getSupportFragmentManager().popBackStack()
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putBoolean("key", true)
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
            else -> false
        }
        return true
    }
}
