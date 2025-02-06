package com.starkindustries.jetpackcomposefirebase.Frontend.Screens

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.starkindustries.jetpackcomposefirebase.Backend.Api.AuthApi.AuthApiInstance
import com.starkindustries.jetpackcomposefirebase.Backend.Api.AuthApi.Profile
import com.starkindustries.jetpackcomposefirebase.Backend.Authentication.Authentication
import com.starkindustries.jetpackcomposefirebase.Frontend.Compose.CircularImageCompose
import com.starkindustries.jetpackcomposefirebase.Frontend.Navigation.Navigation
import com.starkindustries.jetpackcomposefirebase.Frontend.Routes.Routes
import com.starkindustries.jetpackcomposefirebase.Keys.Keys
import com.starkindustries.jetpackcomposefirebase.R
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import com.starkindustries.jetpackcomposefirebase.Backend.Utility.getFileFromUri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import java.io.File
import java.io.IOException

fun uploadProfilePicture(context: Context, imageUri: Uri, username: String, onResult: (Profile?) -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val file: File = getFileFromUri(context, imageUri) ?: throw Exception("File conversion failed")

            val requestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)
            val multipartBody = MultipartBody.Part.createFormData("image", file.name, requestBody)

            val response = AuthApiInstance.api.uploadProfilePic(username, multipartBody)

            if (response.isSuccessful) {
                Log.d("PROFILE_PIC_UPLOAD", "Upload Successful: ${response.body()}")
                onResult(response.body()) // Pass success response
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e("PROFILE_PIC_UPLOAD", "Upload Failed: HTTP ${response.code()} - $errorBody")
                onResult(null) // Handle failure
            }
        } catch (e: HttpException) {
            Log.e("PROFILE_PIC_UPLOAD", "HttpException: ${e.message}")
            onResult(null)
        } catch (e: IOException) {
            Log.e("PROFILE_PIC_UPLOAD", "IOException: ${e.message}")
            onResult(null)
        } catch (e: Exception) {
            Log.e("PROFILE_PIC_UPLOAD", "Exception: ${e.message}")
            onResult(null)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(navController: NavController){
    var email by remember{
        mutableStateOf("")
    }

    var password by remember{
        mutableStateOf("")
    }

    var passwoordVisible by remember {
        mutableStateOf(false)
    }

    var name by remember {
        mutableStateOf("")
    }

    var username by remember {
        mutableStateOf("")
    }

    var imageUri by remember{
        mutableStateOf<Uri?>(null)
    }

    var galleryLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) {uri->
        imageUri=uri
    }

    var coRoutineScope = rememberCoroutineScope()

    var scrollState = rememberScrollState()

    var context = LocalContext.current.applicationContext
    var sharedPreferences = context.getSharedPreferences(Keys.LOGIN_STATUS, Context.MODE_PRIVATE)
    var editor = sharedPreferences.edit()

    Text(text = "Signup Screen"
        , modifier = Modifier
            .fillMaxWidth()
            .padding(top = 80.dp)
        , textAlign = TextAlign.Center
        , fontWeight = FontWeight.W500
        , fontSize = 25.sp)

    Spacer(modifier = Modifier
        .height(80.dp))


    Box(modifier = Modifier
        .fillMaxSize()
        .padding(start = 10.dp, end = 10.dp)
        , contentAlignment = Alignment.Center){

        Column(modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)) {


            Box(modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
            , contentAlignment = Alignment.Center){

                if(imageUri!=null){
                    Box(modifier = Modifier
                        .padding(20.dp)
                        .clip(CircleShape)){
                        Image(painter = rememberAsyncImagePainter(imageUri)
                            , contentDescription =""
                            , modifier = Modifier
                                .clip(CircleShape)
                                .size(160.dp)
                                .border(width = 1.dp, shape = CircleShape, color = Color.Black)
                            , contentScale = ContentScale.Crop)
                    }

                }else{
                    Image(painter = painterResource(id = R.drawable.profile)
                        , contentDescription =""
                        , modifier = Modifier
                            .fillMaxSize())
                }
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 40.dp, end = 110.dp)
                    , contentAlignment = Alignment.BottomEnd){
                    Image(painter = painterResource(id = R.drawable.plus)
                        , contentDescription = ""
                        , modifier = Modifier
                            .size(40.dp)
                            .clickable {
                                galleryLauncher.launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                            })

                }

            }

            Spacer(modifier = Modifier
                .height(20.dp))
            TextField(value = name, onValueChange ={
                name = it
            }
                , label = {
                    Text(text = "Name"
                        , fontSize = 18.sp
                        , fontWeight = FontWeight.W500
                        , color = Color.Black)
                }
                , modifier = Modifier
                    .fillMaxWidth()
                , shape = CircleShape
                , colors = TextFieldDefaults.textFieldColors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
                , textStyle = TextStyle(
                    fontSize = 18.sp
                    , color = Color.Black
                ))

            Spacer(modifier = Modifier
                .height(20.dp))

            TextField(value = username, onValueChange ={
                username = it
            }
                , label = {
                    Text(text = "Username"
                        , fontSize = 18.sp
                        , fontWeight = FontWeight.W500
                        , color = Color.Black)
                }
                , modifier = Modifier
                    .fillMaxWidth()
                , shape = CircleShape
                , colors = TextFieldDefaults.textFieldColors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
                , textStyle = TextStyle(
                    fontSize = 18.sp
                    , color = Color.Black
                ))

            Spacer(modifier = Modifier
                .height(20.dp))

            TextField(value = email, onValueChange ={
                email = it
            }
                , label = {
                    Text(text = "Email"
                        , fontSize = 18.sp
                        , fontWeight = FontWeight.W500
                        , color = Color.Black)
                }
                , modifier = Modifier
                    .fillMaxWidth()
                , shape = CircleShape
                , colors = TextFieldDefaults.textFieldColors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
                , textStyle = TextStyle(
                    fontSize = 18.sp
                    , color = Color.Black
                ))

            Spacer(modifier = Modifier
                .height(20.dp))

            TextField(value = password,
                onValueChange ={
                    password = it
                }
                , label = {
                    Text(text = "Password"
                        , fontSize = 18.sp
                        , color = Color.Black
                        , fontWeight = FontWeight.W500)
                } , modifier = Modifier
                    .fillMaxWidth()
                , shape = CircleShape
                , colors = TextFieldDefaults.textFieldColors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
                , textStyle = TextStyle(
                    fontSize = 18.sp
                    , color = Color.Black
                )
                , trailingIcon = {
                    IconButton(onClick = {
                        passwoordVisible=!passwoordVisible
                    }
                        , modifier = Modifier
                            .padding(end = 10.dp)) {
                        Icon(painter = if(passwoordVisible)
                            painterResource(id = R.drawable.visibility_on)
                        else
                            painterResource(id = R.drawable.visibility_off)
                            , contentDescription = "")
                    }
                })
            Box(modifier = Modifier
                .fillMaxWidth()
                , contentAlignment = Alignment.Center){
                Button(onClick = {

                    coRoutineScope.launch {

                        var profile = Profile(name=name, email = email, username = username, password = password, profilePicUrl = "")

                        try{

                            var response = AuthApiInstance.api.signup(profile)
                            if(response.isSuccessful){

                                if(imageUri==null)
                                    Toast.makeText(context, "Pick an image from your gallery first!!", Toast.LENGTH_SHORT).show()
                                else{
                                    Log.d("IMAGE_URI",imageUri.toString())
                                    uploadProfilePicture(context=context,imageUri=imageUri!!, username = username){ profile ->
                                        editor.putString(Keys.USERNAME,username)
                                        editor.apply()
                                        Log.d("PROFILE_PIC_UPLOAD",profile.toString())
                                    }
                                }
                                navController.navigate(Routes.DashboardScreen.route){
                                    popUpTo(0)
                                }
                                editor.putBoolean(Keys.LOGIN_STATUS, true)
                                editor.commit()
                            }else{
                                Log.d("SIGNUP_ERROR",response.body().toString())
                            }
                        }catch (e:Exception){
                            e.printStackTrace()
                        }
                    }


                }
                    , shape = RectangleShape
                    , modifier = Modifier
                        .padding(top = 20.dp)) {
                    Text(text = "Signup"
                        , fontSize = 18.sp
                        , fontWeight = FontWeight.W500)
                }
            }

            Spacer(modifier = Modifier
                .height(100.dp))

            Row(modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate(Routes.LoginScreen.route) {
                        popUpTo(0)
                    }
                }
                , horizontalArrangement = Arrangement.Center) {
                Text(text = "Already have an account, "
                    , fontWeight = FontWeight.W400
                    , fontSize = 18.sp
                )
                Text(text = "Login"
                    , textDecoration = TextDecoration.Underline
                    , fontSize = 18.sp
                    , fontWeight = FontWeight.W500)
            }

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true, showSystemUi = true)
fun SignupScreenPreview(){
SignupScreen(navController = rememberNavController())
}