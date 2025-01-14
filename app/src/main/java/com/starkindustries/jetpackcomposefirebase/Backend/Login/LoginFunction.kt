package com.starkindustries.jetpackcomposefirebase.Backend.Login

import android.content.Context
import android.util.Log
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.starkindustries.jetpackcomposefirebase.Frontend.Routes.Routes
import com.starkindustries.jetpackcomposefirebase.Keys.Keys

fun LoginFunction(context: Context,email:String,password:String,navController: NavController){
    var auth = FirebaseAuth.getInstance()
    var sharedPreferences = context.getSharedPreferences(Keys.LOGIN_STATUS,Context.MODE_PRIVATE)
    var editor = sharedPreferences.edit()

    auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(){
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