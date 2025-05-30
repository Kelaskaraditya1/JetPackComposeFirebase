package com.starkindustries.jetpackcomposefirebase.Backend.Api.AuthApi

import com.google.gson.GsonBuilder
import com.starkindustries.jetpackcomposefirebase.Keys.Keys
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object AuthApiInstance {

    val api:AuthApi by lazy{

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(60, TimeUnit.SECONDS)  // Increase connection timeout
            .readTimeout(60, TimeUnit.SECONDS)     // Increase read timeout
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        val gson = GsonBuilder().setLenient().create()

        Retrofit.Builder()
            .baseUrl(Keys.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(AuthApi::class.java)
    }
}