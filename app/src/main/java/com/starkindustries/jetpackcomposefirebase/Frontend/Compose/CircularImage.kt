package com.starkindustries.jetpackcomposefirebase.Frontend.Compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.starkindustries.jetpackcomposefirebase.R

@Composable
fun CircularImage(){
    Image(painter = painterResource(id = R.drawable.sandesh)
        , contentDescription = ""
    , modifier = Modifier
            .clip(CircleShape)
            .size(200.dp)
            .border(width = 1.dp, shape = CircleShape, color = Color.Gray)
    , contentScale = ContentScale.Crop)
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun Preview(){
CircularImage()
}