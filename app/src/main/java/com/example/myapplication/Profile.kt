package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityProfileBinding

class Profile : AppCompatActivity() {
    lateinit var bindingClass: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        bindingClass = ActivityProfileBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        bindingClass.bottomMenu.selectedItemId = R.id.profile
        bindingClass.bottomMenu.setOnItemReselectedListener {
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
        bindingClass.editButton.setOnClickListener {
            startActivity(Intent(this, EditProfileActivity()::class.java))
        }
        setContentView(bindingClass.root)
    }
}
