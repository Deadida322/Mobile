package com.example.myapplication

import NewsBus
import android.os.Bundle
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
import com.google.android.material.badge.BadgeDrawable
import com.utils.JSONReader
import com.utils.loadFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import rx.Observable


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

        NewsBus.listen().subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                binding.bottomMenu.getOrCreateBadge(R.id.news).number = (newsCount - it.toInt())
            }
        Observable.just(1.toString())
            .subscribe {
                NewsBus.publish(it.toString())
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
            else -> false
        }
        return true
    }
}
