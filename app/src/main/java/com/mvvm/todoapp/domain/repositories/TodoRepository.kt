package com.mvvm.todoapp.domain.repositories

import com.mvvm.todoapp.data.NetworkResult
import com.mvvm.todoapp.data.database.entities.TodoTask
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun getTasks(): Flow<NetworkResult<List<TodoTask>>>
    suspend fun addTask(task: TodoTask): Flow<NetworkResult<String>>
    suspend fun updateTask(task: TodoTask): Flow<NetworkResult<String>>
    suspend fun deleteTask(taskId: Int): Flow<NetworkResult<String>>
}