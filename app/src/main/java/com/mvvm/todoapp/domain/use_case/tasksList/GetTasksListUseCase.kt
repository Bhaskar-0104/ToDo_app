package com.mvvm.todoapp.domain.use_case.tasksList

import com.mvvm.todoapp.data.NetworkResult
import com.mvvm.todoapp.domain.mappers.TodoListModel
import kotlinx.coroutines.flow.Flow

interface GetTasksListUseCase {
    operator fun invoke(): Flow<NetworkResult<List<TodoListModel>>>
}