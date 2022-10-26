package com.utils // ktlint-disable filename

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import java.util.*

fun Long.toTime(): String {
    val newdate = Date(this)
    val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
    return format.format(newdate)
}

@SuppressLint("UseCompatLoadingForDrawables")
fun LoadImg(context: Context, path: String, view: ImageView) {
    val id: Int = context.getResources().getIdentifier(path, "drawable", context.packageName)
    val img = context.getResources().getDrawable(id, context.getApplicationContext().getTheme())
    view.background = img
}

fun loadFragment(frManager: FragmentManager?, fragment: Fragment, container: Int) {
    val fragmentTransaction = frManager?.beginTransaction()
    fragmentTransaction?.replace(container, fragment)
    fragmentTransaction?.commit()
}
