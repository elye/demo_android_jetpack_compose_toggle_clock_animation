package com.example.jcsimpleclock

import android.util.Log
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun Toggle(modifier: Modifier = Modifier,
           toggleState: MutableState<Boolean>,
           onToggle: (Boolean) -> Unit) {
    TextButton(modifier = modifier, onClick = {
        onToggle(toggleState.value)
        toggleState.value = !toggleState.value
    }) {
        Text(fontSize = 32.sp, text = if (toggleState.value) "Stop" else "Start")
    }
}
