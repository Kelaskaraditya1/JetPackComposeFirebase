package com.starkindustries.jetpackcomposefirebase.Backend.Api.AuthApi

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {


    @POST("auth/signup")
    suspend fun signup(@Body profile:Profile):Response<Profile>

    @POST("auth/login")
    suspend fun login(@Body profile: Profile):Response<String>


}