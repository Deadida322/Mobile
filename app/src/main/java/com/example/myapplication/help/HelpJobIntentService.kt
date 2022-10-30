package com.example.myapplication.help

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService
import com.example.myapplication.R
import com.utils.JSONReader

const val ACTION_LOAD_CATEGORIES = "LOAD_CATEGORIES"
const val EXTRA_KEY_OUT = "EXTRA_OUT"
class NewsIntentService : JobIntentService() {
    var list = arrayListOf<HelpItem>()
    override fun onHandleWork(intent: Intent) {
        Thread.sleep(5000)
        list = JSONReader(applicationContext, resources.getString(R.string.categories_file), HelpItem::class.java).getList()
        val responseIntent = Intent()
        Log.i("key", "JobIntent")
        responseIntent.action = ACTION_LOAD_CATEGORIES
        responseIntent.addCategory(Intent.CATEGORY_DEFAULT)
        responseIntent.putExtra(EXTRA_KEY_OUT, list)
        sendBroadcast(responseIntent)
    }
    companion object {
        fun enqueueWork(context: Context, intent: Intent) {
            enqueueWork(context, NewsIntentService::class.java, 1, intent)
        }
    }
}
