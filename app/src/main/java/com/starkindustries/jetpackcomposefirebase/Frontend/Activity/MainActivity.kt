package com.starkindustries.jetpackcomposefirebase.Frontend.Activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.starkindustries.jetpackcomposefirebase.Frontend.Navigation.Navigation
import com.starkindustries.jetpackcomposefirebase.ui.theme.JetPackComposeFirebaseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetPackComposeFirebaseTheme {
                Application()
            }
        }
    }
}

@Composable
fun Application(){
    Navigation()
}

