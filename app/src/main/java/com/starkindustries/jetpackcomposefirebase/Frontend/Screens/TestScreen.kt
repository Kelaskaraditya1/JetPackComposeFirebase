package com.starkindustries.jetpackcomposefirebase.Frontend.Screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.starkindustries.jetpackcomposefirebase.Backend.Api.AuthApi.AuthApi
import com.starkindustries.jetpackcomposefirebase.Backend.Api.AuthApi.AuthApiInstance
import com.starkindustries.jetpackcomposefirebase.Backend.Api.AuthApi.Profile
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

@Composable
fun TestScreen(){
    var coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier
        .fillMaxSize()
    , contentAlignment = Alignment.Center){
        Column {
            Button(onClick = {
                var profile = Profile(name="Sandesh Jadhav", email = "sandy1@gmail.com", password = "sandy@1234", username = "sandy1", profileImageUri = "")

                coroutineScope.launch {
                    try {
                        val response = AuthApiInstance.api.signup(profile)
                        if (response.isSuccessful) {
                            Log.d("SIGNUP", "Success: ${response.body()}")
                        } else {
                            val errorBody = response.errorBody()?.string()
                            Log.e("SIGNUP_ERROR", "Error: $errorBody")
                        }
                    } catch (e: IOException) {  // Network failures
                        Log.e("SIGNUP_ERROR", "Network Error: ${e.message}")
                    } catch (e: HttpException) { // API response errors
                        Log.e("SIGNUP_ERROR", "HTTP Exception: ${e.message}")
                    } catch (e: Exception) { // Other unknown errors
                        Log.e("SIGNUP_ERROR", "Unexpected Error", e)
                    }

                }

            }) {
                Text(text = "Signup"
                , fontSize = 18.sp
                , fontWeight = FontWeight.W500)
            }

            Spacer(modifier = Modifier
                .height(10.dp))

            Button(onClick = {
                coroutineScope.launch {

                    var profile = Profile(username = "kelaskaraditya1", password = "Aditya@1234")

                    coroutineScope.launch {
                        try {
                            var response = AuthApiInstance.api.login(profile)
                            if(response.isSuccessful)
                                Log.d("LOGIN",response.body().toString())
                            else
                                Log.d("LOGIN_ERROR",response.errorBody().toString())

                        }catch (e:Exception){
                            e.printStackTrace()
                        }
                    }
                }

            }) {
                Text(text = "Login"
                , fontSize = 18.sp
                , fontWeight = FontWeight.W500)
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TestPreview() {
    TestScreen()
}