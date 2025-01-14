package com.starkindustries.jetpackcomposefirebase.Backend.Signup

import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.starkindustries.jetpackcomposefirebase.Frontend.Routes.Routes
import com.starkindustries.jetpackcomposefirebase.Keys.Keys

fun Signup(context:Context,email:String,password:String,navController: NavController){
    var auth = FirebaseAuth.getInstance()
    var sharedPreferences = context.getSharedPreferences(Keys.LOGIN_STATUS, Context.MODE_PRIVATE)
    var editor = sharedPreferences.edit()
    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(){it->
        if(it.isSuccessful){
            editor.putBoolean(Keys.LOGIN_STATUS,true)
            editor.apply()
            navController.navigate(Routes.DashboardScreen.route){
                popUpTo(0)
            }
        }
    }.addOnFailureListener(){
        Log.d("errorMessage",it.message.toString())
    }

}