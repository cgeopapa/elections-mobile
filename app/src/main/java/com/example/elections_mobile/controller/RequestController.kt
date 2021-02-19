package com.example.elections_mobile.controller

import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class RequestController
{
    fun register(code: String)
    {
        val url = URL("https://10.53.251.10/register")
        val param = URLEncoder.encode(code, "UTF-8")

        with(url.openConnection() as HttpURLConnection)
        {
            requestMethod = "POST"
            setRequestProperty("Content-Type", "application/json; utf-8")
            setRequestProperty("Accept", "application/json")
            val os = OutputStreamWriter(outputStream)
            os.write(param)
            os.flush()


        }
    }
}