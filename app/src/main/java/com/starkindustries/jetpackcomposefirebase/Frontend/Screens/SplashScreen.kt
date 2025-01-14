package com.starkindustries.jetpackcomposefirebase.Frontend.Screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.starkindustries.jetpackcomposefirebase.Frontend.Routes.Routes
import com.starkindustries.jetpackcomposefirebase.Keys.Keys
import com.starkindustries.jetpackcomposefirebase.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController){

    var context = LocalContext.current.applicationContext
    var sharedPreferences = context.getSharedPreferences(Keys.LOGIN_STATUS,Context.MODE_PRIVATE)
    var editor = sharedPreferences.edit()
    var auth = FirebaseAuth.getInstance()
    var user = FirebaseAuth.getInstance().currentUser

    LaunchedEffect(Unit) {
        delay(1000)
        if(sharedPreferences.getBoolean(Keys.LOGIN_STATUS,false)&&user!=null){
            navController.navigate(Routes.DashboardScreen.route){
                popUpTo(0)
            }
        }
        else{
            navController.navigate(Routes.LoginScreen.route){
                popUpTo(0)
            }
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
    , contentAlignment = Alignment.Center){
        Image(painter = painterResource(id = R.drawable.notes)
            , contentDescription = ""
        , modifier = Modifier
                .size(150.dp))
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun SplashScreenPreview(){
    SplashScreen(rememberNavController())
}