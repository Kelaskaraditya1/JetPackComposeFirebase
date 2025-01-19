package com.starkindustries.jetpackcomposefirebase.Frontend.Compose

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.starkindustries.jetpackcomposefirebase.R


@Composable
fun CircularImageCompose(callBack:(Uri)->Unit){

    var imageUri by remember{
        mutableStateOf<Uri?>(null)
    }

    var galleryLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) {uri->
        imageUri=uri
    }

    Box(modifier = Modifier
        .size(200.dp)){

        if(imageUri!=null){
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)){
                Image(painter = rememberAsyncImagePainter(imageUri)
                    , contentDescription =""
                    , modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .size(180.dp)
                        .border(width = 1.dp, shape = CircleShape, color = Color.Black)
                    , contentScale = ContentScale.Crop)
            }

        }else{
            Image(painter = painterResource(id = R.drawable.profile)
                , contentDescription =""
                , modifier = Modifier
                    .fillMaxSize())
        }
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 40.dp, end = 10.dp)
        , contentAlignment = Alignment.BottomEnd){
                Image(painter = painterResource(id = R.drawable.plus)
                    , contentDescription = ""
                    , modifier = Modifier
                        .size(40.dp)
                        .clickable {
                            galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                        })

        }

    }
}



@Composable
@Preview(showBackground = true, showSystemUi = true)
fun CircularImagePreview(){
    CircularImageCompose(){

    }
}