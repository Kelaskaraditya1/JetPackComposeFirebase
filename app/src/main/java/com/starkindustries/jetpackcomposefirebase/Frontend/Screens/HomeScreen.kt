package com.starkindustries.jetpackcomposefirebase.Frontend.Screens

import android.content.Context
import android.net.http.HttpException
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.starkindustries.jetpackcomposefirebase.Backend.Api.AuthApi.Profile
import com.starkindustries.jetpackcomposefirebase.Backend.Api.NotesApi.Note
import com.starkindustries.jetpackcomposefirebase.Backend.Api.NotesApi.NotesApiInstance
import com.starkindustries.jetpackcomposefirebase.Backend.Data.NotesRow

import com.starkindustries.jetpackcomposefirebase.Frontend.Compose.NoteRowCompose
import com.starkindustries.jetpackcomposefirebase.Keys.Keys
import com.starkindustries.jetpackcomposefirebase.ui.theme.Purple40
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException


@Composable
fun HomeScreen() {
    var title by remember { mutableStateOf("") }
    var timeStamp by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences(Keys.LOGIN_STATUS,Context.MODE_PRIVATE)
    var editor = sharedPreferences.edit()
    val username = sharedPreferences.getString(Keys.USERNAME,"")
    val coroutineScope = rememberCoroutineScope()
    val jwtToken = sharedPreferences.getString(Keys.JWT_TOKEN,"")

    var addDialogVisibleState by remember{
        mutableStateOf(false)
    }

    var notesList by remember{
        mutableStateOf<List<Note>>(emptyList())
    }

    LaunchedEffect(Unit) {
        try {
            if (jwtToken.isNullOrBlank()) {
                Log.e("ERROR", "JWT Token is missing")
                return@LaunchedEffect
            }

            val response = username?.let {
                NotesApiInstance.api.getNotes(it, "Bearer $jwtToken")
            }

            response?.let {
                if (it.isSuccessful) {
                    notesList = it.body() ?: emptyList()
                    Log.d("NOTES", "First note: ${notesList.getOrNull(0)?.title ?: "No Notes"}")
                } else {
                    Log.e("ERROR", "API Response Error: ${it.code()} - ${it.message()}")
                }
            }
        } catch (e: Exception) {
            Log.e("ERROR", "Exception: ${e.localizedMessage}")
            e.printStackTrace()
        }
    }


    if(addDialogVisibleState){
        AlertDialog(onDismissRequest = {
            addDialogVisibleState=false
        }, confirmButton = {
            Button(onClick = {
                var note = username?.let { Note(title = title, content = content, timeStamp = timeStamp, username = it) }

                coroutineScope.launch {
                    try {
                        if(jwtToken!=null){
                            val response = note?.let { NotesApiInstance.api.insertNotes(it,"Bearer $jwtToken") }
                            if (response != null) {
                                if (response.isSuccessful) {
                                    Log.d("API_SUCCESS", "Note inserted successfully!")
                                    addDialogVisibleState=false
                                } else {
                                    Log.e("API_ERROR", "Error: ${response.code()} - ${response.errorBody()?.string()}")
                                }
                            }
                        }
                    } catch (e: Exception) {
                        Log.e("API_EXCEPTION", "Exception: ${e.localizedMessage}")
                    }

                }

            }) {
                Text(text = "Submit"
                , fontWeight = FontWeight.W400
                    , fontSize = 18.sp)
            }
        }
        , dismissButton = {
            Button(onClick = {
                addDialogVisibleState=false
            }
            , colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Purple40
            )) {
                Text(text = "Cancle"
                , fontWeight = FontWeight.W400
                , fontSize = 18.sp)
            }
            }
        , text = {
            Column {
                TextField(value = title
                    , onValueChange ={
                        title=it
                    }
                , label = {
                    Text(text = "Title"
                    , fontWeight = FontWeight.W500
                    , color = Color.Black)
                    })

                Spacer(modifier = Modifier
                    .height(10.dp))

                TextField(value = content
                    , onValueChange ={
                        content=it
                    }
                    , label = {
                        Text(text = "Content"
                            , fontWeight = FontWeight.W500
                            , color = Color.Black)
                    }
                , modifier = Modifier
                        .height(250.dp))

                Spacer(modifier = Modifier
                    .height(10.dp))

                TextField(value = timeStamp
                    , onValueChange ={
                        timeStamp=it
                    }
                    , label = {
                        Text(text = "Time-Stamp"
                            , fontWeight = FontWeight.W500
                            , color = Color.Black)
                    })

            }
            }
        , title = {
                Text(text = "Add Note"
                    , fontSize = 25.sp
                    , fontWeight = FontWeight.W500)
            })
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Expanded state for notes
        val expandedStates = remember { mutableStateMapOf<Int, Boolean>() }
        if(notesList.isEmpty()){
            Box(modifier = Modifier
                .fillMaxSize()
            , contentAlignment = Alignment.Center){
                Text(text = "No note added yet!!"
                , fontSize = 20.sp
                , fontWeight = FontWeight.W500)
            }
        }else{
            // Display notes in a LazyColumn
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(notesList) { note ->
                    NoteRowCompose(
                        notesRow = note,
                        isExpanded = expandedStates[note.noteId ?: -1] ?: false,
                        onExpandToggle = { isExpanded ->
                            expandedStates[note.noteId ?: -1] = isExpanded
                        },
                        context = context
                    )
                }
            }
        }

        // FloatingActionButton
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            FloatingActionButton(onClick = { addDialogVisibleState = true }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
            }
        }


    }
}




@Composable
@Preview(showBackground = true, showSystemUi = true)
fun Preview(){
HomeScreen()
}




