package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private fun loadFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmantTransaction = fragmentManager.beginTransaction()
        fragmantTransaction.replace(R.id.fragmentContainer, fragment)
        fragmantTransaction.commit()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadFragment(HelpScreen())
        binding.bottomMenu.selectedItemId = R.id.help
        binding.bottomMenu.setOnItemSelectedListener { it ->
            when (it.itemId) {
                R.id.profile -> {
                    loadFragment(ProfileScreen())
                    return@setOnItemSelectedListener true
                }
                R.id.search -> {
                    loadFragment(SearchScreen())
                    return@setOnItemSelectedListener true
                }
                R.id.help -> {
                    loadFragment(HelpScreen())
                    return@setOnItemSelectedListener true
                }
                else -> false
            }
        }
        setContentView(binding.root)
    }
}
