package com.mvvm.todoapp.domain.mappers

import com.mvvm.todoapp.data.database.entities.Priority

data class TodoListModel (
    val id: Int = 0,
    val title: String,
    val description: String,
    val isCompleted: Boolean = false,
    val priority: Priority = Priority.MEDIUM,
    val dueDate: Long? = null,
    val tags: List<String> = emptyList()
)