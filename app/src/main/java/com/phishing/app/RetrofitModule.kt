package com.phishing.app

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface RetrofitModule {

    companion object {

        var okHttpClient = OkHttpClient.Builder()
            .build()

        var gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl(MainActivity.serverURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @POST(MainActivity.checkurl)
    fun phishUrl(
        @Body json: JsonObject
    ): Call<PhishResPojo>?

}