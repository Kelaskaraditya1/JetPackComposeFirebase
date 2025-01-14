package com.starkindustries.jetpackcomposefirebase.Frontend.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.starkindustries.jetpackcomposefirebase.Frontend.Routes.Routes

@Composable
fun DashboardScreen(navController: NavController){
    var auth = FirebaseAuth.getInstance()
    Box(modifier = Modifier
        .fillMaxSize()
    , contentAlignment = Alignment.Center){
        Column(modifier = Modifier
            .fillMaxWidth()
        , verticalArrangement = Arrangement.Center
        , horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "DashBoard Screen"
                , fontSize = 18.sp
                , fontWeight = FontWeight.W500
            )
            Spacer(modifier = Modifier
                .height(10.dp))
            Button(onClick = {
                auth.signOut()
                navController.navigate(Routes.LoginScreen.route){
                    popUpTo(0)
                }
            }
            , shape = RectangleShape) {
                Text(text = "Logout"
                , fontWeight = FontWeight.W500
                , fontSize = 18.sp
                , modifier = Modifier)
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun DashboardscreenPreview(){
    DashboardScreen(rememberNavController())
}