package com.example.elections_mobile

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.elections_mobile.controller.LockAppController
import com.example.elections_mobile.controller.RequestController
import org.json.JSONObject

class CodeRegister : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code_register)

        val code = findViewById<EditText>(R.id.code).text.toString()
        val button = findViewById<Button>(R.id.codeOK)

        button.setOnClickListener{
            confirmCode(code)
        }
    }

    fun confirmCode(code: String)
    {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(code)
        builder.setMessage("Είναι σωστός αυτός ο αριθμός;\nΣιγουρευτείτε ότι είναι ο σωστός αλλιώς δεν θα μπορέσετε να προχωρήσετε!")
        builder.setPositiveButton("Ναι") {dialog, id ->
            val data = RequestController.register(code)
            if(data != null)
            {
                confirmInfo(data)
            }
            LockAppController.oblivion(this)
        }
        builder.setNegativeButton("Όχι") { dialog, id ->

        }

        builder.show()
    }

    fun confirmInfo(data: JSONObject)
    {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Είναι αυτές οι πληροφορίες σωστές;")
        builder.setMessage(data.toString())
        builder.setPositiveButton("Ναι") {dialog, id ->
            val pass = RequestController.authme()
            if(pass != "")
            {
                //Next activity
            }
            LockAppController.oblivion(this)
        }
        builder.setNegativeButton("Όχι") { dialog, id ->
            LockAppController.oblivion(this)
        }

        builder.show()
    }
}
