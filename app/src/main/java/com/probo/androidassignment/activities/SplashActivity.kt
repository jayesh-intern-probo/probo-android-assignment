package com.probo.androidassignment.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.probo.androidassignment.R

class SplashActivity: AppCompatActivity() {

    companion object{
        const val SHARED_PREF_NAME = "myPreferences"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val mySharedPreference = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE)
            val email = mySharedPreference.getString("Email", null)
            val dob = mySharedPreference.getString("DOB", null)
            val password = mySharedPreference.getString("Password", null)
            val imageURI = mySharedPreference.getString("imageURI", null)

            if(email==null || dob==null || password==null)
                startActivity(Intent(this, SignUpActivity::class.java))
            else {
                val intent = Intent(this@SplashActivity, MainActivity::class.java)

                val bundle = Bundle()
                bundle.putString("email", email)
                bundle.putString("dob", dob)
                bundle.putString("password", password)
                bundle.putString("imageURI", imageURI)

                intent.putExtras(bundle)
                startActivity(intent)
            }
            finish()
        }, 2000)
    }
}