package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityHelpCategoryBinding

class HelpCategory : AppCompatActivity() {
    lateinit var binding: ActivityHelpCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelpCategoryBinding.inflate(layoutInflater)
        binding.bottomMenu.selectedItemId = R.id.help
        binding.bottomMenu.setOnItemReselectedListener {
            when (it.itemId) {
                R.id.profile -> {
                    val intent = Intent(this, Profile::class.java)
                    startActivity(intent)
                }
                R.id.help -> {
                    val intent = Intent(this, HelpCategory::class.java)
                    startActivity(intent)
                }
                R.id.search -> {
                    val intent = Intent(this, Search::class.java)
                    startActivity(intent)
                }
                else -> false
            }
        }

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentFame, HelpFragment())
        fragmentTransaction.commit()
        setContentView(binding.root)
    }
}
