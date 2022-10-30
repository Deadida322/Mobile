package com.example.myapplication.profile

import android.app.Activity
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditProfileBinding
    private lateinit var getCameraResult: ActivityResultLauncher<Intent>
    private lateinit var getMediaResult: ActivityResultLauncher<Intent>

    private fun showDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.image_menu, null)
        val builder = AlertDialog.Builder(this)
            .setView(dialogView)
        val alertDialog = builder.show()
        dialogView.findViewById<View>(R.id.menuTakePhoto).setOnClickListener {
            takePhoto()
            alertDialog.dismiss()
        }
        dialogView.findViewById<View>(R.id.menuDelte).setOnClickListener {
            deletePhoto()
            alertDialog.dismiss()
        }
        dialogView.findViewById<View>(R.id.menuUpload).setOnClickListener {
            uploadPhoto()
            alertDialog.dismiss()
        }
    }

    private fun takePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        getCameraResult.launch(intent)
    }

    private fun uploadPhoto() {
        val intent = Intent(ACTION_GET_CONTENT)
        intent.type = "image/*"
        getMediaResult.launch(intent)
    }

    private fun deletePhoto() {
        binding.imageProfile.setImageResource(R.drawable.ic_user_profile_icon)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)

        getCameraResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val newImage = it.data?.extras?.get("data") as Bitmap
                binding.imageProfile.setImageBitmap(newImage)
            }
        }
        getMediaResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                if (it.data != null) {
                    runCatching {
                        val bitmap = when {
                            Build.VERSION.SDK_INT < 28 -> MediaStore.Images.Media.getBitmap(
                                this.contentResolver,
                                it.data?.data
                            )
                            else -> {
                                val source = ImageDecoder.createSource(this.contentResolver, it.data?.data as Uri)
                                ImageDecoder.decodeBitmap(source)
                            }
                        }
                        binding.imageProfile.setImageBitmap(bitmap)
                    }
                }
            }
        }
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding.changeImage.setOnClickListener {
            showDialog()
        }
        setContentView(binding.root)
    }
}
