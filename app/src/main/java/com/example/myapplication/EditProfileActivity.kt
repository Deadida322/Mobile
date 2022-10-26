package com.example.myapplication

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.myapplication.databinding.ActivityEditProfileBinding
import java.io.IOException

class EditProfileActivity : AppCompatActivity() {
    lateinit var bindingClass: ActivityEditProfileBinding
    private val REQUEST_TAKE_PHOTO = 1
    private val SELECT_IMAGE_CODE = 2

    private fun showDialog() {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.image_menu, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
        val mAlertDialog = mBuilder.show()
        mDialogView.findViewById<View>(R.id.menuTakePhoto).setOnClickListener {
            takePhoto()
            mAlertDialog.dismiss()
        }
        mDialogView.findViewById<View>(R.id.menuDelte).setOnClickListener {
            deletePhoto()
            mAlertDialog.dismiss()
        }
        mDialogView.findViewById<View>(R.id.menuUpload).setOnClickListener {
            uploadPhoto()
            mAlertDialog.dismiss()
        }
    }

    private fun takePhoto() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }

    private fun uploadPhoto() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select image..."), SELECT_IMAGE_CODE)
    }

    private fun deletePhoto() {
        bindingClass.imageProfile.setImageResource(R.drawable.ic_user_profile_icon)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityEditProfileBinding.inflate(layoutInflater)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        bindingClass.changeImage.setOnClickListener {
            showDialog()
        }
        setContentView(bindingClass.root)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            val thumbnailBitmap = data?.extras?.get("data") as Bitmap
            bindingClass.imageProfile.setImageBitmap(thumbnailBitmap)
        }
        if (requestCode == SELECT_IMAGE_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                try {
                    val bitmap = when {
                        Build.VERSION.SDK_INT < 28 -> MediaStore.Images.Media.getBitmap(
                            this.contentResolver,
                            data.data
                        )
                        else -> {
                            val source = ImageDecoder.createSource(this.contentResolver, data.data as Uri)
                            ImageDecoder.decodeBitmap(source)
                        }
                    }
                    bindingClass.imageProfile.setImageBitmap(bitmap)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
}
