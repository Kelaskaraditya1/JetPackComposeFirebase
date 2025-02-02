package com.starkindustries.jetpackcomposefirebase.Frontend.Screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.starkindustries.jetpackcomposefirebase.Backend.Data.NotesRow
import com.starkindustries.jetpackcomposefirebase.Backend.RealTime.RealTimeDatabase

import com.starkindustries.jetpackcomposefirebase.Frontend.Compose.NoteRowCompose


@Composable
fun HomeScreen() {

    var dialogState by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf("") }
    var timeStamp by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    val context = LocalContext.current
    val user = FirebaseAuth.getInstance().currentUser

    // Notes state
    val notes = remember { mutableStateOf(emptyList<NotesRow>()) }

    // Fetch notes once
    if (user != null) {
        RealTimeDatabase.getNotes(user) { fetchedNotes ->
            notes.value = fetchedNotes
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Expanded state for notes
        val expandedStates = remember { mutableStateMapOf<Int, Boolean>() }

        // Display notes
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(notes.value) { note ->
                if (user != null) {
                    NoteRowCompose(
                        notesRow = note,
                        isExpanded = expandedStates[note.id ?: -1] ?: false, // Handle nullable IDs
                        onExpandToggle = { isExpanded ->
                            expandedStates[note.id ?: -1] = isExpanded
                        }, onDelete = {
                            if (user != null) {
                                RealTimeDatabase.deleteNote(it,user)
                            }
                        }, user = user,
                        context
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
            FloatingActionButton(onClick = { dialogState = true }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
            }
        }

        // AlertDialog
        if (dialogState) {
            AlertDialog(
                onDismissRequest = { dialogState = false },
                title = { Text(text = "Add Note") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = title,
                            onValueChange = { title = it },
                            label = { Text(text = "Title") }
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedTextField(
                            value = timeStamp,
                            onValueChange = { timeStamp = it },
                            label = { Text(text = "Timestamp") }
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedTextField(
                            value = content,
                            onValueChange = { content = it },
                            label = { Text(text = "Content") }
                        )
                    }
                },
                confirmButton = {
                    Button(onClick = {
                        if (title.isNotBlank() && content.isNotBlank()) {
                            val note = NotesRow(
                                title = title,
                                timeStamp = timeStamp,
                                content = content
                            )
                            if (user != null) {
                                RealTimeDatabase.insertNote(user, note)
                                // Reset fields
                                title = ""
                                timeStamp = ""
                                content = ""
                            }
                            dialogState = false
                        } else {
                            Toast.makeText(
                                context,
                                "Title and Content cannot be empty!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }) {
                        Text(text = "Submit")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { dialogState = false }) {
                        Text(text = "Cancel")
                    }
                }
            )
        }
    }
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun Preview(){
HomeScreen()
}




