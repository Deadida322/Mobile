package com.example.myapplication

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.lang.reflect.Type
import java.util.ArrayList

class JSONReader<T>(val context: Context, val fileName: String, val clazz: Class<T>?) {

    private fun getStringFromAsset(): String {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return ""
        }
        return jsonString
    }

    fun getList(): ArrayList<T> {
        val jsonArray = getStringFromAsset()
        val typeOfT: Type? = TypeToken.getParameterized(MutableList::class.java, clazz).type
        return Gson().fromJson(jsonArray, typeOfT)
    }
}
