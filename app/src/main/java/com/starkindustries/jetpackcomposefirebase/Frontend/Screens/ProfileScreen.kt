package com.starkindustries.jetpackcomposefirebase.Frontend.Screens

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.rpc.context.AttributeContext.Auth
import com.starkindustries.jetpackcomposefirebase.Backend.Api.AuthApi.AuthApiInstance
import com.starkindustries.jetpackcomposefirebase.Backend.Api.AuthApi.Profile
import com.starkindustries.jetpackcomposefirebase.Backend.Authentication.Authentication
import com.starkindustries.jetpackcomposefirebase.Backend.RealTime.RealTimeDatabase
import com.starkindustries.jetpackcomposefirebase.Frontend.Routes.Routes
import com.starkindustries.jetpackcomposefirebase.Keys.Keys
import com.starkindustries.jetpackcomposefirebase.R

@Composable
fun ProfileScreen(navController: NavController) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences(Keys.LOGIN_STATUS, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    val username = sharedPreferences.getString(Keys.USERNAME,"")

    var coroutineScope = rememberCoroutineScope()

    var profile by remember{
        mutableStateOf<Profile?>(null)
    }

    var imageUrl by remember {
        mutableStateOf("")
    }


    LaunchedEffect(Unit) {
        try{
            username?.let {
                var profile1 =AuthApiInstance.api.getUserByUsername(it)
                if(profile1.isSuccessful){
                    profile=profile1.body()
                    imageUrl=profile?.profilePicUrl!!
                    Log.d("PROFILE",profile1.body().toString())
                }
                else
                    Log.d("PROFILE_ERROR",profile1.message().toString())
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }


    // State variables

    // Fetch user data
//    val firebaseUser = FirebaseAuth.getInstance().currentUser
//    firebaseUser?.uid?.let { uid ->
//        LaunchedEffect(uid) {
//            RealTimeDatabase.fetchUserByUid(uid) { user ->
//                if (user != null) {
//                    username = user.userName.orEmpty()
//                    email = user.email.orEmpty()
//                    imageUri = user.profileImageUri.orEmpty()
//                }
//            }
//        }
//    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        // Profile Image
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp),
            contentAlignment = Alignment.Center
        ) {
            if (!imageUrl.isEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(model = imageUrl),
                    contentDescription = "",
                    modifier = Modifier
                        .size(180.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                // Placeholder image while loading
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Placeholder",
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Spacer(modifier = Modifier.height(10.dp))

        if (username != null) {
            Text(text = username,
                fontSize = 22.sp, fontWeight = FontWeight.W500, modifier = Modifier
                    .fillMaxWidth(), textAlign = TextAlign.Center)
        }

        Spacer(modifier = Modifier.height(10.dp))

        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            , contentAlignment = Alignment.Center){
            Button(onClick = {
                var auth = FirebaseAuth.getInstance()
                auth.signOut()
                editor.putBoolean(Keys.LOGIN_STATUS,false)
                editor.apply()
                navController.navigate(route = Routes.LoginScreen.route){
                    popUpTo(0)
                }
            }) {
                Text(text = "Logout"
                    , fontSize = 18.sp)
            }
        }

    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ProfileScreenPreview() {
    ProfileScreen(rememberNavController())
}