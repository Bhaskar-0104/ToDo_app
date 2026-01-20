package com.mvvm.todoapp.domain.mappers

import com.mvvm.todoapp.data.database.entities.TodoTask

fun TodoTask.mapToToDoList(): TodoListModel {
    return TodoListModel(
        id = this.id,
        title = this.title,
        description = this.description,
        isCompleted = this.isCompleted,
        priority = this.priority,
        dueDate = this.dueDate,
        tags = this.tags
    )
}

fun TodoListModel.toEntity(): TodoTask {
    return TodoTask(
        id = this.id, // Passing the existing ID tells Room to UPDATE, not INSERT
        title = this.title,
        description = this.description,
        isCompleted = this.isCompleted,
        priority = this.priority,
        dueDate = this.dueDate,
        tags = this.tags
    )
}