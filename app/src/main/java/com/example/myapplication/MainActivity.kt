package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.myapplication.databinding.ActivityMainBinding
import com.utils.loadFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadFragment(supportFragmentManager, HelpScreen(), R.id.fragmentContainer)
        binding.bottomMenu.selectedItemId = R.id.help
        binding.bottomMenu.setOnItemSelectedListener { it ->
            when (it.itemId) {
                R.id.news -> {
                    loadFragment(supportFragmentManager, NewsScreen(), R.id.fragmentContainer)
                    return@setOnItemSelectedListener true
                }
                R.id.profile -> {
                    loadFragment(supportFragmentManager, ProfileScreen(), R.id.fragmentContainer)
                    return@setOnItemSelectedListener true
                }
                R.id.search -> {
                    loadFragment(supportFragmentManager, SearchScreen(), R.id.fragmentContainer)
                    return@setOnItemSelectedListener true
                }
                R.id.help -> {
                    loadFragment(supportFragmentManager, HelpScreen(), R.id.fragmentContainer)
                    return@setOnItemSelectedListener true
                }
                else -> false
            }
        }
        setContentView(binding.root)
    }
}
