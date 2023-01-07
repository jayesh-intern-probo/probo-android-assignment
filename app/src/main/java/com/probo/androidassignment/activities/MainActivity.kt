package com.probo.androidassignment.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.probo.androidassignment.R
import de.hdodenhof.circleimageview.CircleImageView
import java.io.IOException


class MainActivity : AppCompatActivity() {

    companion object {
        const val READ_STORAGE_PERMISSION_CODE = 1
        const val PICK_IMAGE_REQUEST_CODE = 2
    }

    private var mSelectedImageFileUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpDetails()

        val btnLogOut = findViewById<AppCompatButton>(R.id.btn_log_out)
        btnLogOut.setOnClickListener {
            startActivity(Intent(this@MainActivity, SignUpActivity::class.java))
        }

        val profileImage = findViewById<CircleImageView>(R.id.civ_profile_image)
        profileImage.setOnClickListener {
            if(ContextCompat.checkSelfPermission(this@MainActivity,
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                imageChooser(this@MainActivity)
            }
            else {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    READ_STORAGE_PERMISSION_CODE
                )
            }
        }
    }

    private fun setUpDetails() {
        val tvEmail = findViewById<TextView>(R.id.tv_email)
        val tvDOB = findViewById<TextView>(R.id.tv_dob)
        val tvPassword= findViewById<TextView>(R.id.tv_password)

        val bundle = intent.extras

        tvEmail.text = tvEmail.text.toString() + bundle?.getString("email")
        tvDOB.text = tvDOB.text.toString() + bundle?.getString("dob")
        tvPassword.text = tvPassword.text.toString() + bundle?.getString("password")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == READ_STORAGE_PERMISSION_CODE) {
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                imageChooser(this@MainActivity)
            }
            else {
                Toast.makeText(this@MainActivity,
                    "Permissions Denied. Please Allow It From Settings", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode==Activity.RESULT_OK &&
            requestCode== PICK_IMAGE_REQUEST_CODE &&
            data!!.data != null) {
            mSelectedImageFileUri = data.data!!

            try {
                val profileImage = findViewById<CircleImageView>(R.id.civ_profile_image)
                Glide
                    .with(this@MainActivity)
                    .load(Uri.parse(mSelectedImageFileUri.toString()))
                    .centerCrop()
                    .placeholder(R.drawable.ic_baseline_person_24)
                    .into(profileImage)
            }
            catch (exception: IOException) {
                exception.printStackTrace()
            }
        }
    }

    private fun imageChooser(activity: MainActivity) {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activity.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
    }
}