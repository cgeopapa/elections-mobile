package com.example.elections_mobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.elections_mobile.controller.LockAppController
import com.example.elections_mobile.controller.RequestController

class PassRegister : AppCompatActivity()
{
    private val requestController = RequestController(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pass_register)

        val pass = findViewById<EditText>(R.id.pass).text.toString()
        val button = findViewById<Button>(R.id.passOK)
        button.setOnClickListener {
            val status = requestController.auth(pass)
            if(status == 200)
            {
                val dataStatus = requestController.data()
                if(dataStatus == 200)
                {
                    LockAppController.success(this)
                }
                LockAppController.oblivion(this)
            }
            LockAppController.oblivion(this)
        }
    }
}
