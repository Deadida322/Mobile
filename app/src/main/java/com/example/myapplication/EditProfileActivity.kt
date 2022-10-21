package com.example.myapplication

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {
    lateinit var bindingClass: ActivityEditProfileBinding
    private val REQUEST_TAKE_PHOTO = 1

    private fun showDialog(it: View) {
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
            showDialog(it)
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
