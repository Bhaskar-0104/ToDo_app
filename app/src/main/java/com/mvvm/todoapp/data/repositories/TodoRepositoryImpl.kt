package com.mvvm.todoapp.data.repositories

import com.mvvm.todoapp.data.NetworkResult
import com.mvvm.todoapp.data.database.TodoDatabase
import com.mvvm.todoapp.data.database.entities.TodoTask
import com.mvvm.todoapp.domain.repositories.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val db: TodoDatabase
) : TodoRepository {
    override suspend fun getTasks(): Flow<NetworkResult<List<TodoTask>>> = flow {
        emit(NetworkResult.Loading())
        db.todoDao().getAllTasks().collect { tasks ->
            emit(NetworkResult.Success(tasks))
        }
    }.catch { e ->
        emit(NetworkResult.Error(e.message))
    }

    override suspend fun addTask(task: TodoTask): Flow<NetworkResult<String>> = flow {
        emit(NetworkResult.Loading())
        with(db.todoDao().insertTask(task = task)) {
            emit(NetworkResult.Success("Task added successfully"))
        }
    }.catch { e ->
        emit(NetworkResult.Error(e.message))
    }

    override suspend fun updateTask(task: TodoTask): Flow<NetworkResult<String>> = flow {
        emit(NetworkResult.Loading())
        with(db.todoDao().updateTask(task = task)) {
            emit(NetworkResult.Success("Task updated successfully"))
        }
    }.catch { e ->
        emit(NetworkResult.Error(e.message))
    }

    override suspend fun deleteTask(taskId: Int): Flow<NetworkResult<String>> = flow {
        emit(NetworkResult.Loading())
        with(db.todoDao().deleteTask(taskId = taskId)) {
            emit(NetworkResult.Success("Task deleted successfully"))
        }
    }.catch { e ->
        emit(NetworkResult.Error(e.message))
    }
}