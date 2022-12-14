package com.example.myapplication.help

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentHelpScreenBinding

class HelpScreen : Fragment() {
    lateinit var binding: FragmentHelpScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHelpScreenBinding.inflate(layoutInflater)
        return binding.root
    }
}
