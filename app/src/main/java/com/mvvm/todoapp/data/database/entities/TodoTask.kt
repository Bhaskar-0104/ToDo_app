package com.mvvm.todoapp.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class TodoTask(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val isCompleted: Boolean = false,
    val dueDate: Long? = null,
    val priority: Priority = Priority.MEDIUM,
    val tags: List<String> = emptyList()
)

enum class Priority { LOW, MEDIUM, HIGH }