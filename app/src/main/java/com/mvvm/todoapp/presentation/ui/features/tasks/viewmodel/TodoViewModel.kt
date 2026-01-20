package com.mvvm.todoapp.presentation.ui.features.tasks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvvm.todoapp.data.NetworkResult
import com.mvvm.todoapp.data.database.entities.Priority
import com.mvvm.todoapp.data.database.entities.TodoTask
import com.mvvm.todoapp.domain.use_case.addTasks.AddTaskUseCase
import com.mvvm.todoapp.domain.use_case.deleteTasks.DeleteTaskUseCase
import com.mvvm.todoapp.domain.use_case.tasksList.GetTasksListUseCase
import com.mvvm.todoapp.domain.use_case.updateTasks.UpdateTaskUseCase
import com.mvvm.todoapp.presentation.contracts.TaskCompletionContract
import com.mvvm.todoapp.presentation.contracts.TaskListContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val listUseCase: GetTasksListUseCase,
    private val addUseCase: AddTaskUseCase,
    private val updateUseCase: UpdateTaskUseCase,
    private val deleteUseCase: DeleteTaskUseCase,
) : ViewModel() {
    init {
        /*addTask(
            TodoTask(
                title = "Task 1",
                description = "Description 1",
                isCompleted = true,
                dueDate = System.currentTimeMillis(),
                priority = Priority.LOW,
                tags = listOf("Personal")
            )
        )
        for (i in 2..5) {
            addTask(
                TodoTask(
                    title = "Task $i",
                    description = "Description $i",
                    isCompleted = false,
                    dueDate = System.currentTimeMillis(),
                    priority = Priority.HIGH,
                    tags = listOf("Personal"),
                )
            )
        }*/
        getTaskList()
    }

    private val _listState = MutableStateFlow(TaskListContract.State())
    val listState: StateFlow<TaskListContract.State> = _listState

    private val _taskCompletionState = MutableStateFlow(TaskCompletionContract.State())
    val taskCompletionState: StateFlow<TaskCompletionContract.State> = _taskCompletionState

    private fun updateListState(newState: TaskListContract.State) {
        _listState.value = newState
    }

    private fun updateTaskCompletionState(newState: TaskCompletionContract.State) {
        _taskCompletionState.value = newState
    }

    // get All Task List Method
    fun getTaskList() {
        listUseCase().flowOn(Dispatchers.IO).onEach { result ->
            when (result) {
                is NetworkResult.Error<*> -> {
                    updateListState(
                        listState.value.copy(
                            isLoading = false,
                            error = result.message!!
                        )
                    )
                }

                is NetworkResult.Loading<*> -> {
                    updateListState(listState.value.copy(isLoading = true))
                }

                is NetworkResult.Success<*> -> {
                    updateListState(listState.value.copy(isLoading = false, tasks = result.data!!))
                }
            }
        }.launchIn(viewModelScope)
    }

    // Task Add Method
    fun addTask(task: TodoTask) {
        addUseCase(task).flowOn(Dispatchers.IO).onEach { result ->
            when (result) {
                is NetworkResult.Error<*> -> {
                    updateTaskCompletionState(
                        taskCompletionState.value.copy(
                            isLoading = false,
                            error = result.message!!
                        )
                    )
                }

                is NetworkResult.Loading<*> -> {
                    updateTaskCompletionState(taskCompletionState.value.copy(isLoading = true))
                }

                is NetworkResult.Success<*> -> {
                    updateTaskCompletionState(
                        taskCompletionState.value.copy(
                            isLoading = false,
                            message = result.data!!
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    // Task Update Method
    fun toggleComplete(task: TodoTask) {
        updateUseCase(task).onEach { result ->
            when (result) {
                is NetworkResult.Error<*> -> {
                    updateTaskCompletionState(
                        taskCompletionState.value.copy(
                            isLoading = false,
                            error = result.message!!
                        )
                    )
                }

                is NetworkResult.Loading<*> -> {
                    updateTaskCompletionState(taskCompletionState.value.copy(isLoading = true))
                }

                is NetworkResult.Success<*> -> {
                    updateTaskCompletionState(
                        taskCompletionState.value.copy(
                            isLoading = false,
                            message = result.data!!
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    // Task Delete Method
    fun deleteTask(taskId: Int) {
        deleteUseCase(taskId).onEach { result ->
            when (result) {
                is NetworkResult.Error<*> -> {
                    updateTaskCompletionState(
                        taskCompletionState.value.copy(
                            isLoading = false,
                            error = result.message!!
                        )
                    )
                }

                is NetworkResult.Loading<*> -> {
                    updateTaskCompletionState(taskCompletionState.value.copy(isLoading = true))
                }

                is NetworkResult.Success<*> -> {
                    updateTaskCompletionState(
                        taskCompletionState.value.copy(
                            isLoading = false,
                            message = result.data!!
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}