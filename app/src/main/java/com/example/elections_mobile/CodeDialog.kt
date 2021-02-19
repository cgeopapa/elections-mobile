package com.example.elections_mobile

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.lang.IllegalStateException

class CodeDialog : DialogFragment(){
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val arg = arguments
            val builder = AlertDialog.Builder(it)
            builder.setTitle(arg?.get("title")?.toString())
                .setMessage(arg?.get("msg")?.toString())
                .setPositiveButton("Ναι", DialogInterface.OnClickListener{dialog, id ->
                    //OK
                })
                .setNegativeButton("Οχι", DialogInterface.OnClickListener{dialog, id ->
                    //NO
                })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
