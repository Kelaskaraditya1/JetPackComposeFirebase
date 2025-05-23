package com.starkindustries.jetpackcomposefirebase.Frontend.Routes

import com.starkindustries.jetpackcomposefirebase.Keys.Keys

sealed class Routes(var route:String) {
    object SplashScreen:Routes(Keys.SPLASH_SCREEN_ROUTE)
    object LoginScreen:Routes(Keys.LOGIN_SCREEN_ROUTE)
    object SignupScreen:Routes(Keys.SIGNUP_SCREEN_ROUTE)
    object DashboardScreen:Routes(Keys.DASHBOARD_SCREEN_ROUTE)
    object HomeScreen:Routes(Keys.HOME_SCREEN_ROUTE)
    object ProfileScreen:Routes(Keys.PROFILE_SCREEN_ROUTE)
    object VideoCallScreen:Routes(Keys.VIDEO_CALL_ROUTE)
}