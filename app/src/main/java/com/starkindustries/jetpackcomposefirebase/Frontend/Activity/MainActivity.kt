package com.starkindustries.jetpackcomposefirebase.Frontend.Activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import com.starkindustries.jetpackcomposefirebase.Backend.Data.NotesRow
import com.starkindustries.jetpackcomposefirebase.Frontend.Navigation.Navigation
import com.starkindustries.jetpackcomposefirebase.ui.theme.JetPackComposeFirebaseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var listState = mutableStateOf<List<NotesRow>>(emptyList())
        enableEdgeToEdge()
        setContent {
            JetPackComposeFirebaseTheme {
//                var noteList by listState
//                lifecycleScope.launchWhenCreated {
//                    var response = try{
//                        RetrofitInstance.api.getNotes()
//                    }catch (e:Exception){
//                        e.printStackTrace()
//                        null
//                    }
//                    if(response!=null && response.isSuccessful)
//                        listState.value = response.body()?: emptyList()
//                }
                Application()
            }
        }
    }
}

@Composable
fun Application(){
    Navigation()
}

