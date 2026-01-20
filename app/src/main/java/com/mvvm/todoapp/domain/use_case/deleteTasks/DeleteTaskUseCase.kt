package com.mvvm.todoapp.domain.use_case.deleteTasks

import com.mvvm.todoapp.data.NetworkResult
import kotlinx.coroutines.flow.Flow

interface DeleteTaskUseCase {
    operator fun invoke(taskId: Int): Flow<NetworkResult<String>>
}