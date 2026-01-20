package com.mvvm.todoapp.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardDoubleArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mvvm.todoapp.presentation.ui.theme.AccentBlue

@Composable
fun OptionRow(
    icon: ImageVector,
    label: String,
    value: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = AccentBlue,
            modifier = Modifier.padding(end = 12.dp)
        )
        Text(
            text = label,
            color = Color.White,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            color = AccentBlue,
            fontSize = 14.sp
        )
        Icon(
            imageVector = Icons.Default.KeyboardDoubleArrowRight,
            contentDescription = null,
            tint = Color(0xFF556171),
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}