package com.starkindustries.jetpackcomposefirebase.Backend.Api.NotesApi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: NotesApi by lazy {

        Retrofit.Builder()
            .baseUrl("http://192.168.1.100:8080/notes")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NotesApi::class.java)
    }
}