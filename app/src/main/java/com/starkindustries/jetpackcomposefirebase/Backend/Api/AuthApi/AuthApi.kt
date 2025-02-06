package com.starkindustries.jetpackcomposefirebase.Backend.Api.AuthApi

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface AuthApi {


    @POST("auth/signup")
    suspend fun signup(@Body profile:Profile):Response<Profile>

    @POST("auth/login")
    suspend fun login(@Body profile: Profile):Response<String>

    @Multipart
    @PUT("auth/upload-profile-pic/{username}")
    suspend fun uploadProfilePic(@Path("username") username:String, @Part image: MultipartBody.Part) :Response<Profile>

    @GET("auth/get-user-by-username/{username}")
    suspend fun getUserByUsername(@Path("username") username:String):Response<Profile>

}