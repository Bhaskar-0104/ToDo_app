package com.mvvm.todoapp.presentation.contracts

import com.mvvm.todoapp.domain.mappers.TodoListModel

class TaskListContract {
    data class State(
        val isLoading: Boolean = false,
        val tasks: List<TodoListModel> = emptyList(),
        val error: String = ""
    )
}