package com.example.elections_mobile.controller

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.elections_mobile.R
import khttp.responses.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.URLEncoder
import java.security.Key
import java.security.KeyStore
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import javax.net.ssl.*

class RequestController(context: Context): ViewModel()
{
    private val context = context
    private val queue: RequestQueue = Volley.newRequestQueue(context)

    fun register(code: String): LiveData<JSONObject?> {
        val result = MutableLiveData<JSONObject?>()
        val url = "https://192.168.1.50/register"

        val registerRequest = object: StringRequest(Method.POST, url,
                com.android.volley.Response.Listener<String> { response ->
                    result.postValue(JSONObject(response))
                }, com.android.volley.Response.ErrorListener { error ->
            System.err.println("error => $error")
            result.postValue(null)
        }){
                override fun getBody(): ByteArray {
                    return code.toByteArray()
                }
            }
        HttpsURLConnection.setDefaultSSLSocketFactory(getSocketFactory())
        HttpsURLConnection.setDefaultHostnameVerifier(getHostnameVerifier())
        println("VOLLEY")
        queue.add(registerRequest)
        return result
    }

    fun authme(): String {
        val url = "https://192.168.1.50/authme"
        val res: Response = khttp.get(url)

        if (res.statusCode == 200) {
            return res.text
        }
        return ""
    }

    fun auth(pass: String): Int {
        val url = "https://192.168.1.50/auth"
        val res: Response = khttp.post(url = url, data = pass)

        return res.statusCode
    }

    fun data(): Int {
        val url = "https://192.168.1.50/data"
        val res: Response = khttp.post(url = url, data = "sent")

        return res.statusCode
    }

    private fun getSocketFactory(): SSLSocketFactory
    {
        val cf: CertificateFactory = CertificateFactory.getInstance("X.509")
        val caInput: InputStream = BufferedInputStream(context.resources.openRawResource(R.raw.ca))
        val clientInput = BufferedInputStream(context.resources.openRawResource(R.raw.client))
        val ca: Certificate
        val client: Certificate

        try{
            ca = cf.generateCertificate(caInput)
            client = cf.generateCertificate(clientInput)
        } finally {
            caInput.close()
//            clientInput.close()
        }

        val keyStoreType = KeyStore.getDefaultType()
        val keyStore = KeyStore.getInstance(keyStoreType)
        keyStore.load(null, null)

        val ks = KeyStore.getInstance(KeyStore.getDefaultType())
        val pwd = "".toCharArray()
        ks.load(clientInput, pwd)
        val kmf = KeyManagerFactory.getInstance("PKIX")
        kmf.init(ks, pwd)
        clientInput.close()

        keyStore.setCertificateEntry("ca", ca)
        val tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm()
        val tmf = TrustManagerFactory.getInstance(tmfAlgorithm)
        tmf.init(keyStore)

        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(kmf.keyManagers, tmf.trustManagers, null)

        return sslContext.socketFactory
    }

    private fun getHostnameVerifier(): HostnameVerifier
    {
        return HostnameVerifier { hostname, session ->
//            val hv = HttpsURLConnection.getDefaultHostnameVerifier()
//            return@HostnameVerifier hv.verify("192.168.1.50", session)
            true
        }
    }
}
