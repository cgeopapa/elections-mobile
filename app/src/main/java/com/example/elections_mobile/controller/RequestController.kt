package com.example.elections_mobile.controller

import khttp.responses.Response
import org.json.JSONObject
import java.net.URLEncoder

class RequestController
{
    companion object {
        fun register(code: String): JSONObject? {
            val url = "https://10.53.251.10/register"

            val res: Response = khttp.post(url = url, data = code)

            if(res.statusCode == 200)
            {
                return res.jsonObject
            }
            return null
        }

        fun authme(): String {
            val url = "https://10.53.251.10/authme"
            val res: Response = khttp.get(url)

            if(res.statusCode == 200)
            {
                return res.text
            }
            return ""
        }

        fun auth(pass: String): Int {
            val url = "https://10.53.251.10/auth"
            val res: Response = khttp.post(url = url, data = pass)

            return res.statusCode
        }

        fun data(): Int {
            val url = "https://10.53.251.10/data"
            val res: Response = khttp.post(url = url, data = "sent")

            return res.statusCode
        }
    }
}
