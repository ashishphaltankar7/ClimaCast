package com.example.climacast

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.os.postDelayed

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        // to hide above bar
        supportActionBar?.hide()

        // Code for Splash Screen
//        Handler(Looper.getMainLooper()).postDelayed({
//            val intent= Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        },2500)


        Handler(Looper.getMainLooper()).postDelayed(3000) {
            startActivity(Intent(this,MainActivity::class.java))
            //Toast.makeText(this,"Welcome To Weather App",Toast.LENGTH_LONG).show()
            finish()
        }
    }
}