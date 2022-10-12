package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityProfileBinding

class Profile : AppCompatActivity() {
    lateinit var bindingClass: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        bindingClass = ActivityProfileBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(bindingClass.root)
        bindingClass.bottomMenu.selectedItemId = R.id.profile
    }
}
