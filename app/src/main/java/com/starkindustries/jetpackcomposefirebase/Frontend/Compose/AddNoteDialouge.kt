package com.starkindustries.jetpackcomposefirebase.Frontend.Compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteDialouge(noteTitle:String){

    var dialougeState by remember{
        mutableStateOf(false)
    }

    var title by remember{
        mutableStateOf("")
    }

    var timeStamp by remember{
        mutableStateOf("")
    }

    var content by remember {
        mutableStateOf("")
    }

    AlertDialog(
        text = {
            Column(modifier = Modifier
                .padding(top = 10.dp)) {

                Text(text = noteTitle
                    , fontWeight = FontWeight.W500
                , fontSize = 18.sp
                , modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                , textAlign = TextAlign.Center)

                TextField(value = title
                    , onValueChange = {
                        title=it
                    }
                , label = {
                    Text(text = "Title"
                    , fontSize = 16.sp
                    , fontWeight = FontWeight.W500
                    , color = Color.Black)
                    })

                Spacer(modifier = Modifier
                    .height(10.dp))

                TextField(value = timeStamp
                    , onValueChange = {
                        timeStamp=it
                    }
                    , label = {
                        Text(text = "TimeStamp"
                            , fontSize = 16.sp
                            , fontWeight = FontWeight.W500
                            , color = Color.Black)
                    })

                Spacer(modifier = Modifier
                    .height(10.dp))

                TextField(value = content
                    , onValueChange = {
                        content=it
                    }
                    , label = {
                        Text(text = "Content"
                            , fontSize = 16.sp
                            , fontWeight = FontWeight.W500
                            , color = Color.Black)
                    })
            }
        }
        ,onDismissRequest = {
            dialougeState=false
    }, confirmButton = {
        Button(onClick = {
            dialougeState=false
        }) {
            Text(text = "Submit"
            , fontSize = 16.sp
            , fontWeight = FontWeight.W500)
        }
    }
    , dismissButton = {
        TextButton(onClick = {
            dialougeState=false
        }) {
            Text(text = "Cancel"
            , fontSize = 16.sp
            , fontWeight = FontWeight.W500)
        }
        })



}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun DialougePreview(){
    AddNoteDialouge("Update Note")
}