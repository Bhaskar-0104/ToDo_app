package com.mvvm.todoapp.domain.use_case.tasksList

import com.mvvm.todoapp.data.NetworkResult
import com.mvvm.todoapp.domain.mappers.TodoListModel
import com.mvvm.todoapp.domain.mappers.mapToToDoList
import com.mvvm.todoapp.domain.repositories.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTasksListUseCaseImpl @Inject constructor(val todoRepository: TodoRepository) :
    GetTasksListUseCase {
    override fun invoke(): Flow<NetworkResult<List<TodoListModel>>> = flow {
        todoRepository.getTasks().collect { result ->
            when (result) {
                is NetworkResult.Error<*> -> {
                    emit(NetworkResult.Error(result.message))
                }

                is NetworkResult.Loading<*> -> {
                    emit(NetworkResult.Loading())
                }

                is NetworkResult.Success<*> -> {
                    val list = result.data?.map { it.mapToToDoList() }
                    emit(NetworkResult.Success(list))
                }
            }
        }
    }
}