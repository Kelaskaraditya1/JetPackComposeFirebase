package com.starkindustries.jetpackcomposefirebase.Backend.Api.NotesApi

import com.starkindustries.jetpackcomposefirebase.Backend.Data.NotesRow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HEAD
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface NotesApi {

    @POST("notes/insert-note")
    suspend fun insertNotes(@Body note:Note,@Header("Authorization") jwtToken:String):Response<Note>

    @GET("notes/get-notes/{username}")
    suspend fun getNotes(@Path(value = "username") username:String,@Header("Authorization") jwtToken:String):Response<List<Note>>

    @PUT("notes/update-note/{noteId}/{username}")
    suspend fun updateNote(@Body note:Note,@Path("noteId") noteId:Int,@Path("username") username:String,@Header("Authorization") jwtToken:String):Response<Note>

    @DELETE("notes/delete-note/{noteId}")
    suspend fun deleteNote(@Path("noteId") noteId:Int,@Header("Authorization") jwtToken: String):Response<String>

}