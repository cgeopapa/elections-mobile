package com.example.elections_mobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import java.net.CookieHandler
import java.net.CookieManager
import java.net.CookiePolicy

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CookieHandler.setDefault(CookieManager(null, CookiePolicy.ACCEPT_ALL))

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener{
            val intent = Intent(this, CodeRegister::class.java)
            startActivity(intent)
        }
    }
}
