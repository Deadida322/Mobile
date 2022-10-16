package com.example.myapplication

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {
    lateinit var bindingClass: ActivityEditProfileBinding
    private val REQUEST_TAKE_PHOTO = 1

    private fun showPopup(it: View) {
        val popupMenu = PopupMenu(this, it)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.upload -> {
                    true
                }
                R.id.take_foto -> {
                    takePhoto()
                    true
                }
                R.id.delete -> {
                    deletePhoto()
                    true
                }
                else -> true
            }
        }
        popupMenu.inflate(R.menu.profile_menu)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            popupMenu.setForceShowIcon(true)
        }
        popupMenu.show()
    }

    private fun takePhoto() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }

    private fun deletePhoto() {
        bindingClass.imageProfile.setImageResource(R.drawable.ic_user_profile_icon)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        bindingClass = ActivityEditProfileBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(bindingClass.root)

        bindingClass.changeImage.setOnClickListener {
            showPopup(it)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            val thumbnailBitmap = data?.extras?.get("data") as Bitmap
            bindingClass.imageProfile.setImageBitmap(thumbnailBitmap)
        }
    }
}
