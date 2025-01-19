package com.starkindustries.jetpackcomposefirebase.Backend.Data

data class NotesRow(
    var id: Int? = null,       // Make id nullable and give it a default value of null
    var title: String? = "",   // Make title nullable and provide a default value
    var content: String? = "", // Same for content
    var timeStamp: String? = "", // Same for timeStamp
    var noteId: String? = null // Same for noteId
)

