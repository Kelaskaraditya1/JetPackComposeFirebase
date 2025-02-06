package com.starkindustries.jetpackcomposefirebase.Frontend.Compose

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.starkindustries.jetpackcomposefirebase.Backend.Api.NotesApi.Note
import com.starkindustries.jetpackcomposefirebase.Backend.Data.NotesRow
import com.starkindustries.jetpackcomposefirebase.Backend.RealTime.RealTimeDatabase

@Composable
fun NoteRowCompose(
    notesRow: Note,
    isExpanded: Boolean,
    onExpandToggle: (Boolean) -> Unit,
    onDelete:(noteId:String)->Unit,
    context:Context) {

    var updateDialouge by remember{
        mutableStateOf(false)
    }

    var updatedTitle by remember{
        mutableStateOf("")
    }

    var updatedContent by remember{
        mutableStateOf("")
    }

    var updatedTimeStamp by remember{
        mutableStateOf("")
    }

    Box(
        modifier = Modifier
            .padding(15.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .size(200.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.LightGray,
                contentColor = Color.Black
            ),
            elevation = CardDefaults.cardElevation(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        notesRow.title?.let {
                            Text(
                                text = it,
                                fontWeight = FontWeight.Bold,
                                fontSize = 28.sp, modifier = Modifier
                                    .weight(3f)
                            )
                        }
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                        , contentAlignment = Alignment.TopEnd){
                            Row(modifier = Modifier) {
                                IconButton(onClick = {
                                    notesRow.noteId?.let { onDelete(it.toString()) }
                                }) {
                                    Icon(imageVector = Icons.Default.Delete, contentDescription = "")
                                }
                                Spacer(modifier = Modifier.width(5.dp))
                                IconButton(onClick = {
                                    updateDialouge=true
                                }) {
                                    Icon(imageVector = Icons.Default.Edit, contentDescription = "")
                                }
                            }
                        }
                    }

                    notesRow.timeStamp?.let {
                        Text(
                            text = it,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W400,
                            modifier = Modifier
                                .padding(top = 8.dp)
                        )
                    }

                    Spacer(
                        modifier = Modifier
                            .height(20.dp)
                    )

                    notesRow.content?.let {
                        Text(
                            text = it,
                            fontWeight = FontWeight.W500,
                            fontSize = 20.sp,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = if (isExpanded) Int.MAX_VALUE else 2,
                            modifier = Modifier
                                .clickable {
                                    onExpandToggle(!isExpanded)
                                }
                        )
                    }

                    if (!isExpanded) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(3.dp)
                                .clickable { onExpandToggle(true) },
                            contentAlignment = Alignment.BottomEnd
                        ) {
                            Text(
                                text = "show more...",
                                fontWeight = FontWeight.W500,
                                modifier = Modifier
                                    .clickable {
                                        onExpandToggle(true)
                                    }
                            )
                        }
                    }
                }
            }
        }
    }
    if(updateDialouge){
        AlertDialog(
            onDismissRequest = { updateDialouge = false },
            title = { Text(text = "Update Note") },
            text = {
                Column {
                    notesRow.title?.let {
                        OutlinedTextField(
                            value = updatedTitle,
                            onValueChange = { updatedTitle = it },
                            label = { Text(text = "Title") }
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    notesRow.timeStamp?.let {
                        OutlinedTextField(
                            value = updatedTimeStamp,
                            onValueChange = { updatedTimeStamp = it },
                            label = { Text(text = "Timestamp") }
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    notesRow.content?.let {
                        OutlinedTextField(
                            value = updatedContent,
                            onValueChange = { updatedContent = it },
                            label = { Text(text = "Content") }
                        )
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    if (updatedTitle.isNotBlank() && updatedContent.isNotBlank()) {
                        val note = NotesRow(
                            title = updatedTitle,
                            timeStamp = updatedTimeStamp,
                            content = updatedContent,
                            noteId = notesRow.noteId.toString() // Ensure the noteId is preserved
                        )
                        // Reset fields
                        updatedTitle = ""
                        updatedContent = ""
                        updatedTimeStamp = ""

                        updateDialouge = false
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
            }
,
            dismissButton = {
                TextButton(onClick = { updateDialouge = false }) {
                    Text(text = "Cancel")
                }
            }
        )
    }
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun NoteRowPreview(){

}

