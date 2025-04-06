package com.plko.bls.app.ui.screens.profile

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun ProfileScreen() {
    Text(
        text = "Profile Screen",
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight(),
        textAlign = TextAlign.Center,
        fontSize = 20.sp
    )
}