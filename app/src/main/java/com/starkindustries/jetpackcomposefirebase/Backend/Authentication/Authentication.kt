package com.starkindustries.jetpackcomposefirebase.Backend.Authentication

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.starkindustries.jetpackcomposefirebase.Frontend.Routes.Routes
import com.starkindustries.jetpackcomposefirebase.Keys.Keys
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore

class Authentication {
    companion object {

        fun LoginFunction(
            context: Context,
            email: String,
            password: String,
            navController: NavController
        ) {
            var auth = FirebaseAuth.getInstance()
            var sharedPreferences =
                context.getSharedPreferences(Keys.LOGIN_STATUS, Context.MODE_PRIVATE)
            var editor = sharedPreferences.edit()

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener() {
                if (it.isSuccessful) {
                    editor.putBoolean(Keys.LOGIN_STATUS, true)
                    editor.apply()
                    navController.navigate(Routes.VideoCallScreen.route) {
                        popUpTo(0)
                    }

                }
            }.addOnFailureListener() {
                Log.d("errorMessage", it.message.toString())
            }
        }

        fun signout(navController: NavController,context: Context) {
            var auth = FirebaseAuth.getInstance()
            var sharedPreferences = context.getSharedPreferences(Keys.LOGIN_STATUS, Context.MODE_PRIVATE)
            var editor = sharedPreferences.edit()
            auth.signOut()
            editor.putBoolean(Keys.LOGIN_STATUS,false)
            editor.apply()
            navController.navigate(Routes.LoginScreen.route){
                popUpTo(0)
            }
        }



        fun Signup(
            context: Context,
            email: String,
            password: String,
            userName: String,
            profileImageUri: String,
            navController: NavController
        ) {
            val auth = FirebaseAuth.getInstance()
            val firestore = FirebaseFirestore.getInstance()
            val sharedPreferences = context.getSharedPreferences(Keys.LOGIN_STATUS, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseUser = task.result?.user
                        firebaseUser?.let { user ->
                            // Update display name and profile picture in Firebase Authentication
                            val profileUpdates = UserProfileChangeRequest.Builder()
                                .setDisplayName(userName)
                                .setPhotoUri(Uri.parse(profileImageUri))
                                .build()

                            user.updateProfile(profileUpdates)
                                .addOnCompleteListener { updateTask ->
                                    if (updateTask.isSuccessful) {
                                        // Store additional user data in Firestore
                                        val userData = mapOf(
                                            "uid" to user.uid,
                                            "username" to userName,
                                            "profilePicUri" to profileImageUri,
                                            "email" to email
                                        )

                                        firestore.collection("users").document(user.uid)
                                            .set(userData)
                                            .addOnSuccessListener {
                                                Log.d("Signup", "User profile created successfully!")
                                                editor.putBoolean(Keys.LOGIN_STATUS, true)
                                                editor.apply()
                                                navController.navigate(Routes.VideoCallScreen.route) {
                                                    popUpTo(0)
                                                }
                                            }
                                            .addOnFailureListener { e ->
                                                Log.d("Signup", "Failed to store user data in Firestore: ${e.message}")
                                            }
                                    } else {
                                        Log.d("Signup", "Failed to update profile in Firebase Auth: ${updateTask.exception?.message}")
                                    }
                                }
                        }
                    } else {
                        Log.d("Signup", "Failed to create user: ${task.exception?.message}")
                    }
                }
                .addOnFailureListener { e ->
                    Log.d("Signup", "Error: ${e.message}")
                }
        }

    }


}