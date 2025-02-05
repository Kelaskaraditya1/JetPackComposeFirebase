package com.starkindustries.jetpackcomposefirebase.Backend.Api.NotesApi

import com.starkindustries.jetpackcomposefirebase.Backend.Data.NotesRow
import retrofit2.Response
import retrofit2.http.GET

interface NotesApi {

    @GET("get-notes")
    suspend fun getNotes(): Response<List<NotesRow>>
}