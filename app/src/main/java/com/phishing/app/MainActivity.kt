package com.phishing.app

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.webkit.URLUtil
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

import androidx.databinding.DataBindingUtil
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.phishing.app.databinding.ActivityMainBinding
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        init()
    }

    fun init() {

        onClick()

        val intent = intent
        val data: Uri? = intent.data

        if (data != null) {

            fetch(data.toString())
        }

        Log.d("init", "data" + data)
    }

    fun onClick() {

        binding.btSubmit.setOnClickListener {

            if (Patterns.WEB_URL.matcher(binding.etUrl.editableText)
                    .matches() && URLUtil.isValidUrl(binding.etUrl.editableText.toString())
            ) {
                fetch(binding.etUrl.editableText.toString())
            } else {
                Toast.makeText(applicationContext, "Enter a valid url", Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {

        const val serverURL = "https://checkurl.phishtank.com/"
        const val checkurl = "checkurl/"

    }

    fun fetch(url: String) {

        val queue = Volley.newRequestQueue(applicationContext)
        val sr: StringRequest = object : StringRequest(
            Request.Method.POST,
            "https://checkurl.phishtank.com/checkurl/",
            object : com.android.volley.Response.Listener<String?> {
                override fun onResponse(response: String?) {

                    Log.d("MainVolley", "onResponse: " + response)

                    val obj = JSONObject(response)
                    var inDatabase = ""
                    var valid = ""
                    if (obj.has("results")) {

                        val results: JSONObject = obj.getJSONObject("results")
                        if (results.has("in_database")) {
                            var inData = results.getBoolean("in_database")
                            if (inData) {
                                inDatabase = "Url is in database. "
                            } else {
                                inDatabase = "Url isn't in database. "
                            }
                        }
                        if (results.has("valid")) {
                            var v = results.getBoolean("valid")
                            if (v) {
                                valid = "It is a Phishing Link"
                                inDatabase = ""
                                dialog(inDatabase + valid)
                            } else {

                                valid = "It is not a Phishing Link"
                                inDatabase = ""
                                dialog(inDatabase + valid)
                            }
                        } else {
                            dialog(inDatabase + valid)
                        }


                    }
                }
            },
            object : com.android.volley.Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError?) {
                    Log.d("MainVolley", "onResponse: " + error)
                }
            }) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["url"] = url.toLowerCase()
                params["format"] = "json"
                return params
            }

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["Content-Type"] = "application/x-www-form-urlencoded"
                return params
            }
        }
        queue.add(sr)

    }

    fun dialog(message: String) {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Phishing Status")
        builder.setMessage(message)
//builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            dialog.dismiss()
        }
        val alertDialog = builder.create()
        alertDialog.show()

    }

}