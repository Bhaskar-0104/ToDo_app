package com.mvvm.todoapp.presentation.ui.features.tasks.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mvvm.todoapp.domain.mappers.toEntity
import com.mvvm.todoapp.presentation.ui.components.EmptyListView
import com.mvvm.todoapp.presentation.ui.components.SectionHeader
import com.mvvm.todoapp.presentation.ui.features.tasks.viewmodel.TodoViewModel
import com.mvvm.todoapp.presentation.ui.theme.AccentBlue

@Composable
fun TasksScreen(
    viewModel: TodoViewModel = hiltViewModel()
) {
    val listState by viewModel.listState.collectAsStateWithLifecycle()
    val completionState by viewModel.taskCompletionState.collectAsStateWithLifecycle()

    val activeTasks = listState.tasks.filter { !it.isCompleted }
    val completedTasks = listState.tasks.filter { it.isCompleted }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (!listState.isLoading && listState.tasks.isEmpty()) {
            EmptyListView()
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 20.dp) // Side margins
            ) {
                // Section: TODAY
                item {
                    SectionHeader("TODAY")
                }
                items(activeTasks, key = { it.id }) { task ->
                    TaskItem(
                        task = task.toEntity(),
                        onToggle = {
                            val updatedTask = task.copy(isCompleted = !task.isCompleted)
                            viewModel.toggleComplete(updatedTask.toEntity())
                        },
                        onDelete = { viewModel.deleteTask(task.id) }
                    )
                }

                // Section: COMPLETED
                if (completedTasks.isNotEmpty()) {
                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                        SectionHeader("COMPLETED")
                    }
                    items(completedTasks, key = { it.id }) { task ->
                        TaskItem(
                            task = task.toEntity(),
                            onToggle = {
                                val updatedTask = task.copy(isCompleted = !task.isCompleted)
                                viewModel.toggleComplete(updatedTask.toEntity())
                            },
                            onDelete = { viewModel.deleteTask(task.id) }
                        )
                    }
                }

                // Spacer for bottom nav visibility
                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }

        // B. Loading Indicator (Center)
        if (listState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = AccentBlue
            )
        }

        // List Error Handing
        if (listState.error.isNotEmpty() && !listState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: ${listState.error}", color = Color.Red)
            }
        }

        // If an add/delete/update op is happening, show a small loader or just let the snackbar handle it
        if (completionState.isLoading) {
            // Optional: Show a linear progress indicator at the top
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter),
                color = AccentBlue
            )
        }
    }
}