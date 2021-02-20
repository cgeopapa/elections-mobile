package com.example.elections_mobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Success : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)
    }

    override fun onBackPressed() {
        //NO!!!
    }
}
