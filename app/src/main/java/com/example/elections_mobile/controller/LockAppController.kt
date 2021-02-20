package com.example.elections_mobile.controller

import android.content.Context
import android.content.Intent
import com.example.elections_mobile.Oblivion
import com.example.elections_mobile.Success

class LockAppController
{
    companion object{
        fun oblivion(context: Context){
            val intent = Intent(context, Oblivion::class.java)
            context.startActivity(intent)
        }

        fun success(context: Context){
            val intent = Intent(context, Success::class.java)
            context.startActivity(intent)
        }
    }
}
