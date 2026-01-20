package com.mvvm.todoapp.presentation.ui.features.tasks.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PriorityHigh
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mvvm.todoapp.data.database.entities.Priority
import com.mvvm.todoapp.data.database.entities.TodoTask
import com.mvvm.todoapp.presentation.ui.theme.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskItem(
    task: TodoTask,
    onToggle: () -> Unit,
    onDelete: () -> Unit
) {
    // 1. Visual Style Helpers
    val isCompleted = task.isCompleted
    val containerColor = if (isCompleted) Color.Transparent else CardBackground // 0xFF161F30
    val contentAlpha = if (isCompleted) 0.5f else 1f
    val textColor = if (isCompleted) TextSecondary else TextPrimary
    val textDecoration = if (isCompleted) TextDecoration.LineThrough else null

    // 2. Dynamic Subtitle Logic (Matches: "High priority • Due 2:00 PM")
    val subtitle = remember(task) {
        buildSubtitle(task)
    }

    // 3. Dynamic Icon Logic (Matches screenshot icons)
    val rightIcon = remember(task) {
        getIconForTask(task)
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = containerColor),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp) // Tighter spacing like the image
            .alpha(contentAlpha),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isCompleted) 0.dp else 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 18.dp) // Matches image padding
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // --- CHECKBOX ---
            Checkbox(
                checked = isCompleted,
                onCheckedChange = { onToggle() },
                colors = CheckboxDefaults.colors(
                    checkedColor = AccentBlue,        // Blue when checked
                    uncheckedColor = Color(0xFF475569), // Slate grey when unchecked (custom border look)
                    checkmarkColor = Color.White
                ),
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // --- TEXT CONTENT ---
            Column(modifier = Modifier.weight(1f)) {
                // Title
                Text(
                    text = task.title,
                    color = textColor,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = if (isCompleted) FontWeight.Normal else FontWeight.SemiBold,
                        fontSize = 16.sp
                    ),
                    textDecoration = textDecoration
                )

                // Subtitle (Priority • Date)
                if (subtitle.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = subtitle,
                        color = TextSecondary, // Muted Grey
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1
                    )
                }
            }

            // --- RIGHT ACTION ICON ---
            // In the image, these are status icons (Exclamation, Bell),
            // but we keep the onDelete capability functionality-wise.
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = rightIcon,
                    contentDescription = "Action",
                    tint = if (task.priority == Priority.HIGH && !isCompleted) AccentBlue else TextSecondary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

// --- HELPER FUNCTIONS ---

private fun buildSubtitle(task: TodoTask): String {
    val parts = mutableListOf<String>()

    // 1. First Part: Priority OR Tag
    // If High Priority, show that. Otherwise, show the first Tag (e.g. "Work").
    if (task.priority == Priority.HIGH) {
        parts.add("High priority")
    } else if (task.tags.isNotEmpty()) {
        parts.add(task.tags.first())
    }

    // 2. Second Part: Due Date OR Description
    // If there is a Due Date, show it.
    // If NOT, show the Description (e.g. "Attachment included").
    if (task.dueDate != null && task.dueDate > 0L) {
        val sdf = SimpleDateFormat("'Due' h:mm a", Locale.getDefault())
        parts.add(sdf.format(Date(task.dueDate)))
    } else if (task.description.isNotBlank()) {
        // Only show description if we didn't show a date (avoids clutter)
        // This matches the "Work • Attachment included" example
        parts.add(task.description)
    }

    // 3. Join them with the bullet point
    return parts.joinToString(" • ")
}

/**
 * Chooses the icon based on task attributes (Priority > Date > Default)
 */
private fun getIconForTask(task: TodoTask): ImageVector {
    return when {
        task.isCompleted -> Icons.Default.DateRange // Or a Check icon
        task.priority == Priority.HIGH -> Icons.Default.PriorityHigh // (!) Icon
        task.dueDate != null && task.dueDate > 0L -> Icons.Default.Notifications // Bell Icon
        else -> Icons.Outlined.Delete // Fallback
    }
}