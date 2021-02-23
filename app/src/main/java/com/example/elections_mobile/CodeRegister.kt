package com.example.elections_mobile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.elections_mobile.controller.LockAppController
import com.example.elections_mobile.controller.RequestController
import org.json.JSONObject

class CodeRegister : AppCompatActivity()
{
    private lateinit var requestController: RequestController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code_register)
        requestController = RequestController(this)

        val button = findViewById<Button>(R.id.codeOK)

        button.setOnClickListener{
            confirmCode(findViewById<EditText>(R.id.code).text.toString())
        }
    }

    private fun confirmCode(code: String)
    {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(code)
        builder.setMessage("Είναι σωστός αυτός ο αριθμός;\nΣιγουρευτείτε ότι είναι ο σωστός αλλιώς δεν θα μπορέσετε να προχωρήσετε!")
        builder.setPositiveButton("Ναι") { _, _ ->
            requestController.register(code).observe(this, Observer { data ->
                if(data != null)
                {
                    confirmInfo(data)
                }
                else {
                    LockAppController.oblivion(this)
                }
            })
        }
        builder.setNegativeButton("Όχι") { _, _ ->

        }

        builder.show()
    }

    private fun confirmInfo(data: JSONObject)
    {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Είναι αυτές οι πληροφορίες σωστές;")
        builder.setMessage(
                "Όνομα: ${data["fname"]}\n" +
                "Επώνυμο: ${data["lname"]}\n" +
                "Τηλέφωνο: ${data["phone"]}"
        )
        builder.setPositiveButton("Ναι") {_, _ ->
            requestController.authme().observe(this, Observer { _ ->
                val intent = Intent(this, PassRegister::class.java)
                startActivity(intent)
            })
        }
        builder.setNegativeButton("Όχι") { _, _ ->
            LockAppController.oblivion(this)
        }

        builder.show()
    }
}
