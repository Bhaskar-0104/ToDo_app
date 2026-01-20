package com.mvvm.todoapp.domain.use_case.updateTasks

import com.mvvm.todoapp.data.NetworkResult
import com.mvvm.todoapp.data.database.entities.TodoTask
import kotlinx.coroutines.flow.Flow

interface UpdateTaskUseCase {
    operator fun invoke(task: TodoTask): Flow<NetworkResult<String>>
}