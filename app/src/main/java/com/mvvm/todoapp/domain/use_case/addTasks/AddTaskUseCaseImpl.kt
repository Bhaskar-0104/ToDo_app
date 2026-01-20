package com.mvvm.todoapp.domain.use_case.addTasks

import com.mvvm.todoapp.data.NetworkResult
import com.mvvm.todoapp.data.database.entities.TodoTask
import com.mvvm.todoapp.domain.repositories.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddTaskUseCaseImpl @Inject constructor(val repository: TodoRepository) : AddTaskUseCase {
    override fun invoke(task: TodoTask): Flow<NetworkResult<String>> = flow {
        repository.addTask(task).collect { result ->
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