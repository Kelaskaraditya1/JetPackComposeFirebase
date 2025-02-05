package com.starkindustries.jetpackcomposefirebase.Backend.Api.NotesApi

data class Note(var noteId:Int?=null,
    var title:String,
    var content:String,
    var timeStamp:String,
    var username:String)
