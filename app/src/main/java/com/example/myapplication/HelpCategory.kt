package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityHelpCategoryBinding

class HelpCategory : AppCompatActivity() {
    lateinit var binding: ActivityHelpCategoryBinding

//    lateinit var adapter: HelpAdapter
//    lateinit var recyclerView: RecyclerView
//    lateinit var list: ArrayList<HelpItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        list = ArrayList<HelpItem>(
//            listOf(
//                HelpItem(R.drawable.childs, "Дети"),
//                HelpItem(R.drawable.adult, "Взрослые"),
//                HelpItem(R.drawable.elder, "Пожилые"),
//                HelpItem(R.drawable.animals, "Животные"),
//                HelpItem(R.drawable.events, "Меропориятия")
//            )
//        )
//
        binding = ActivityHelpCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var fragmentManager = supportFragmentManager
        var fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentFame, HelpFragment())
        fragmentTransaction.commit()
//        adapter = HelpAdapter()
//        recyclerView = binding.recyclerView
//        recyclerView.layoutManager = GridLayoutManager(this, 2)
//        recyclerView.adapter = adapter
//        adapter.setInfo(list)
    }
}
