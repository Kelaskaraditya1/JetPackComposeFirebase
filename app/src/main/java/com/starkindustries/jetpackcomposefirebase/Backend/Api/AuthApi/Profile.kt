package com.starkindustries.jetpackcomposefirebase.Backend.Api.AuthApi

data class Profile(
    var profileId: Int = 0,  // Default value, backend will assign it
    var email: String = "",
    var name: String = "",
    var password: String,
    var profileImageUri: String = "",
    var username: String
)

