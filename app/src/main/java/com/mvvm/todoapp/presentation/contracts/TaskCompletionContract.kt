package com.mvvm.todoapp.presentation.contracts

class TaskCompletionContract {
    data class State(
        val isLoading: Boolean = false,
        val message: String = "",
        val error: String = ""
    )
}