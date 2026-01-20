package com.mvvm.todoapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mvvm.todoapp.data.database.entities.TodoTask
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo_table")
    fun getAllTasks(): Flow<List<TodoTask>> // Return Flow for real-time updates

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TodoTask)

    @Update
    suspend fun updateTask(task: TodoTask)

    @Query("DELETE FROM todo_table WHERE id = :taskId")
    suspend fun deleteTask(taskId: Int)
}