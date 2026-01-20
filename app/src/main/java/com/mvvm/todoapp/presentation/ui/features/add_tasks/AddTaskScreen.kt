package com.mvvm.todoapp.presentation.ui.features.add_tasks

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mvvm.todoapp.data.database.entities.Priority
import com.mvvm.todoapp.data.database.entities.TodoTask
import com.mvvm.todoapp.presentation.ui.components.CustomTextField
import com.mvvm.todoapp.presentation.ui.components.InputLabel
import com.mvvm.todoapp.presentation.ui.components.OptionRow
import com.mvvm.todoapp.presentation.ui.theme.AccentBlue
import com.mvvm.todoapp.presentation.ui.theme.AppBackground
import com.mvvm.todoapp.presentation.ui.theme.BorderColor
import com.mvvm.todoapp.presentation.ui.theme.CardBackground
import com.mvvm.todoapp.presentation.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    onDismiss: () -> Unit,
    onSave: (TodoTask) -> Unit
) {
    // 1. UI State for Inputs
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf(Priority.MEDIUM) }
    var dueDate by remember { mutableLongStateOf(System.currentTimeMillis()) }

    // Manage Tags
    val availableTags = listOf("Personal", "Work", "Shopping")
    val selectedTags = remember { mutableStateListOf<String>() }

    Scaffold(
        containerColor = AppBackground,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Add New Task",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel", color = AccentBlue, fontSize = 16.sp)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = AppBackground)
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        val newTask = TodoTask(
                            title = title,
                            description = description,
                            priority = priority,
                            dueDate = dueDate,
                            tags = selectedTags.toList(),
                            isCompleted = false
                        )
                        onSave(newTask)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AccentBlue)
            ) {
                Text("Save Task", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            // --- Task Name ---
            InputLabel("Task Name")
            CustomTextField(
                value = title,
                onValueChange = { title = it },
                placeholder = "e.g., Buy groceries",
                minLines = 1
            )

            Spacer(modifier = Modifier.height(20.dp))

            // --- Description ---
            InputLabel("Description")
            CustomTextField(
                value = description,
                onValueChange = { description = it },
                placeholder = "Add more details...",
                minLines = 4
            )

            Spacer(modifier = Modifier.height(20.dp))

            // --- Options Card (Date & Priority) ---
            Card(
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, BorderColor)
            ) {
                Column {
                    // Due Date
                    OptionRow(
                        icon = Icons.Default.DateRange,
                        label = "Due Date",
                        value = "Today", // In real app, format 'dueDate' to string
                        onClick = { /* Open DatePicker Dialog */ }
                    )

                    Divider(color = BorderColor, thickness = 1.dp)

                    // Priority
                    OptionRow(
                        icon = Icons.Default.Flag,
                        label = "Priority",
                        value = priority.name.lowercase().replaceFirstChar { it.uppercase() },
                        onClick = {
                            // Cycle priority for demo
                            priority = when (priority) {
                                Priority.LOW -> Priority.MEDIUM
                                Priority.MEDIUM -> Priority.HIGH
                                Priority.HIGH -> Priority.LOW
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // --- Tags ---
            InputLabel("Tags")
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                availableTags.forEach { tag ->
                    val isSelected = selectedTags.contains(tag)
                    FilterChip(
                        selected = isSelected,
                        onClick = {
                            if (isSelected) selectedTags.remove(tag)
                            else selectedTags.add(tag)
                        },
                        label = { Text(tag) },
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = CardBackground,
                            labelColor = TextSecondary,
                            selectedContainerColor = AccentBlue.copy(alpha = 0.2f),
                            selectedLabelColor = AccentBlue
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            borderColor = if (isSelected) AccentBlue else BorderColor,
                            selectedBorderColor = AccentBlue,
                            enabled = isSelected,
                            selected = isSelected
                        )
                    )
                }

                // Add Tag Button (Visual only)
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .background(CardBackground, CircleShape)
                        .border(1.dp, BorderColor, CircleShape)
                        .size(32.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add", tint = TextSecondary)
                }
            }

            // Extra spacing for scroll
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}