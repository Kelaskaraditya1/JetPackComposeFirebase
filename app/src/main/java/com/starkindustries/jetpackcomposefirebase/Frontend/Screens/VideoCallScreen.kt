package com.starkindustries.jetpackcomposefirebase.Frontend.Screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoSendCallInvitationButton


@Composable
fun VideoCallStream(navController: NavController){
    Box(modifier = Modifier
        .fillMaxSize()
    , contentAlignment = Alignment.Center){
        Text(text = "Video Call Stream"
        , fontSize = 18.sp
        , fontWeight = FontWeight.W500)
    }


}

fun CallButton(isVideoCall:Boolean,obClick:(ZegoSendCallInvitationButton)){

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun VideoCallPreview() {
    VideoCallStream(rememberNavController())
}