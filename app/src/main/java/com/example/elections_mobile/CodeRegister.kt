package com.example.elections_mobile

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class CodeRegister : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code_register)

        val inputText = findViewById<EditText>(R.id.code)
        val button = findViewById<Button>(R.id.codeOK)

        button.setOnClickListener{
            val d = CodeDialog()
            val b = Bundle()
            b.putString("title", inputText.text.toString())
            b.putString("msg", "Είναι σωστός αυτός ο αριθμός;\nΣιγουρευτείτε ότι είναι ο σωστός αλλιώς δεν θα μπορέσετε να προχωρήσετε!")
            d.arguments = b
            d.show(supportFragmentManager, "code")
        }
    }
}
