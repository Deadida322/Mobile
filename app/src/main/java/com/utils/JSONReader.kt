package com.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import kotlin.collections.ArrayList

class JSONReader<T>(private val context: Context, private val fileName: String, private val clazz: Class<T>?) {

    private fun getStringFromAsset(): String {
        var jsonString: String = ""
        runCatching {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        }
        return jsonString
    }

    fun getList(): ArrayList<T> {
        val jsonArray = getStringFromAsset()
        val typeOfT: Type? = TypeToken.getParameterized(MutableList::class.java, clazz).type
        return Gson().fromJson(jsonArray, typeOfT)
    }
}
