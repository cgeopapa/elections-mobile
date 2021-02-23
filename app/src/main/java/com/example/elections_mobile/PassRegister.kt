package com.example.elections_mobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import com.example.elections_mobile.controller.LockAppController
import com.example.elections_mobile.controller.RequestController

class PassRegister : AppCompatActivity()
{
    private lateinit var requestController: RequestController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pass_register)
        requestController = RequestController(this)

        val button = findViewById<Button>(R.id.passOK)
        button.setOnClickListener {
            val pass = findViewById<EditText>(R.id.pass).text.toString()
            println(pass)
            requestController.auth(pass).observe(this, Observer { data ->
                if(data != null)
                {
                    requestController.data().observe(this, Observer { data ->
                        if(data != null)
                        {
                            LockAppController.success(this)
                        }
                        else
                        {
                            LockAppController.oblivion(this)
                        }
                    })
                }
                else
                {
                    LockAppController.oblivion(this)
                }
            })
        }
    }

    override fun onBackPressed() {
        //NO!!!
    }
}
