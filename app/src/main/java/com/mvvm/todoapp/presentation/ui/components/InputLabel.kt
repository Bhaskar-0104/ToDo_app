package com.mvvm.todoapp.presentation.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InputLabel(text: String) {
    Text(
        text = text,
        color = Color.White,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}