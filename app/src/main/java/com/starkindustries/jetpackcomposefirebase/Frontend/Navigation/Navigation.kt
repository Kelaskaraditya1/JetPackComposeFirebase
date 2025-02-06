package com.starkindustries.jetpackcomposefirebase.Frontend.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.starkindustries.jetpackcomposefirebase.Frontend.Routes.Routes
import com.starkindustries.jetpackcomposefirebase.Frontend.Screens.DashboardScreen
import com.starkindustries.jetpackcomposefirebase.Frontend.Screens.HomeScreen
import com.starkindustries.jetpackcomposefirebase.Frontend.Screens.LoginScreen
import com.starkindustries.jetpackcomposefirebase.Frontend.Screens.ProfileScreen
import com.starkindustries.jetpackcomposefirebase.Frontend.Screens.SignupScreen
import com.starkindustries.jetpackcomposefirebase.Frontend.Screens.SplashScreen

@Composable
fun Navigation(){

    var context = LocalContext.current.applicationContext

    var navController = rememberNavController()
    NavHost(navController = navController, startDestination =Routes.SplashScreen.route ){
        composable(Routes.SplashScreen.route){
            SplashScreen(navController = navController)
        }
        composable(Routes.LoginScreen.route){
            LoginScreen(navController = navController)
        }
        composable(Routes.DashboardScreen.route){
            DashboardScreen(navController)
        }
        composable(Routes.SignupScreen.route){
            SignupScreen(navController = navController)
        }
        composable(Routes.HomeScreen.route){
            HomeScreen()
        }
        composable(Routes.ProfileScreen.route){
            ProfileScreen(navController = navController)
        }
    }
}