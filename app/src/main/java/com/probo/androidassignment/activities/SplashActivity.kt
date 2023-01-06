package com.probo.androidassignment.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.probo.androidassignment.R

class SplashActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            //TODO Check Whether User Has Logged In
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }, 2000)
    }
}