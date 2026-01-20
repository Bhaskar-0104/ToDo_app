package com.mvvm.todoapp.domain.use_case.deleteTasks

import com.mvvm.todoapp.data.NetworkResult
import com.mvvm.todoapp.domain.repositories.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteTaskUseCaseImpl @Inject constructor(val repository: TodoRepository) :
    DeleteTaskUseCase {
    override fun invoke(taskId: Int): Flow<NetworkResult<String>> = flow {
        repository.deleteTask(taskId).collect { result ->
            when (result) {
                is NetworkResult.Error<*> -> {
                    emit(NetworkResult.Error(result.message))
                }

                is NetworkResult.Loading<*> -> {
                    emit(NetworkResult.Loading())
                }

                is NetworkResult.Success<*> -> {
                    emit(NetworkResult.Success(result.data))
                }
            }
        }
    }
}