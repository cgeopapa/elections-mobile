package com.example.elections_mobile.controller

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.elections_mobile.R
import khttp.responses.Response
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.InputStream
import java.io.UnsupportedEncodingException
import java.security.KeyStore
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import javax.net.ssl.*

class RequestController(context: Context): ViewModel()
{
    private val context = context
    private val queue: RequestQueue = Volley.newRequestQueue(context)

    fun register(code: String): LiveData<JSONObject?>
    {
        val url = "https://192.168.1.50/register"

        return send(url, Request.Method.POST, code)
    }

    fun authme(): MutableLiveData<JSONObject?> {
        val url = "https://192.168.1.50/authme"

        return send(url, Request.Method.GET)
    }

    fun auth(pass: String): MutableLiveData<JSONObject?> {
        val url = "https://192.168.1.50/auth"

        return send(url, Request.Method.POST, pass)
    }

    fun data(): MutableLiveData<JSONObject?> {
        val url = "https://192.168.1.50/data"

        return send(url, Request.Method.POST, "sent")
    }

    private fun send(url: String, method: Int, body: String): MutableLiveData<JSONObject?>
    {
        val result = MutableLiveData<JSONObject?>()

        val request = object: StringRequest(method, url,
                com.android.volley.Response.Listener { response ->
                    result.postValue(JSONObject(response))
                }, com.android.volley.Response.ErrorListener { _ ->
            result.postValue(null)
        }){
            override fun parseNetworkResponse(response: NetworkResponse): com.android.volley.Response<String> {
                val enc = charset(HttpHeaderParser.parseCharset(response.headers))
                try{
                    var parsed = String(response.data, enc)
                    val bytes = parsed.toByteArray(enc)
                    parsed = String(bytes, charset("UTF-8"))
                    return com.android.volley.Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response))
                } catch (e: UnsupportedEncodingException) {
                    return com.android.volley.Response.error(ParseError(e))
                }
            }
            override fun getBody(): ByteArray {
                return body.toByteArray()
            }
        }
        HttpsURLConnection.setDefaultSSLSocketFactory(getSocketFactory())
        HttpsURLConnection.setDefaultHostnameVerifier(getHostnameVerifier())
        queue.add(request)
        return result
    }

    private fun send(url: String, method: Int): MutableLiveData<JSONObject?>
    {
        val result = MutableLiveData<JSONObject?>()

        val request = object: StringRequest(method, url,
                com.android.volley.Response.Listener { response ->
                    result.postValue(JSONObject(response))
                }, com.android.volley.Response.ErrorListener { _ ->
            result.postValue(null)
        }){
            override fun parseNetworkResponse(response: NetworkResponse): com.android.volley.Response<String> {
                val enc = charset(HttpHeaderParser.parseCharset(response.headers))
                try{
                    var parsed = String(response.data, enc)
                    val bytes = parsed.toByteArray(enc)
                    parsed = String(bytes, charset("UTF-8"))
                    return com.android.volley.Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response))
                } catch (e: UnsupportedEncodingException) {
                    return com.android.volley.Response.error(ParseError(e))
                }
            }
        }
        HttpsURLConnection.setDefaultSSLSocketFactory(getSocketFactory())
        HttpsURLConnection.setDefaultHostnameVerifier(getHostnameVerifier())
        queue.add(request)
        return result
    }

    private fun getSocketFactory(): SSLSocketFactory
    {
        val cf: CertificateFactory = CertificateFactory.getInstance("X.509")
        val caInput: InputStream = BufferedInputStream(context.resources.openRawResource(R.raw.ca))
        val clientInput = BufferedInputStream(context.resources.openRawResource(R.raw.client))
        val ca: Certificate

        try{
            ca = cf.generateCertificate(caInput)
        } finally {
            caInput.close()
        }

        val keyStoreType = KeyStore.getDefaultType()
        val keyStore = KeyStore.getInstance(keyStoreType)
        keyStore.load(null, null)

        val ks = KeyStore.getInstance("PKCS12")
        val pwd = "ele".toCharArray()
        ks.load(clientInput, pwd)
        val kmf = KeyManagerFactory.getInstance("X509")
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
        return HostnameVerifier { _, _ ->
//            val hv = HttpsURLConnection.getDefaultHostnameVerifier()
//            return@HostnameVerifier hv.verify("192.168.1.50", session)
            true
        }
    }
}
