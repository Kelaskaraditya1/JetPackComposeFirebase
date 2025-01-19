package com.starkindustries.jetpackcomposefirebase.Frontend.Screens

import android.content.Context
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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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
import com.starkindustries.jetpackcomposefirebase.Backend.Authentication.Signup.Signup
import com.starkindustries.jetpackcomposefirebase.Frontend.Routes.Routes
import com.starkindustries.jetpackcomposefirebase.Keys.Keys
import com.starkindustries.jetpackcomposefirebase.R

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

    var context = LocalContext.current.applicationContext
    var sharedPreferences = context.getSharedPreferences(Keys.LOGIN_STATUS, Context.MODE_PRIVATE)
    var editor = sharedPreferences.edit()

    Text(text = "Signup Screen"
        , modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp)
        , textAlign = TextAlign.Center
        , fontWeight = FontWeight.W500
        , fontSize = 25.sp)

    Spacer(modifier = Modifier
        .height(100.dp))

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(start = 10.dp, end = 10.dp)
        , contentAlignment = Alignment.Center){

        Column {

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
                    Signup(context,email,password,navController)
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
                    navController.navigate(Routes.LoginScreen.route){
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