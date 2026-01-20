package com.mvvm.todoapp.di

import com.mvvm.todoapp.domain.repositories.TodoRepository
import com.mvvm.todoapp.domain.use_case.addTasks.AddTaskUseCase
import com.mvvm.todoapp.domain.use_case.addTasks.AddTaskUseCaseImpl
import com.mvvm.todoapp.domain.use_case.deleteTasks.DeleteTaskUseCase
import com.mvvm.todoapp.domain.use_case.deleteTasks.DeleteTaskUseCaseImpl
import com.mvvm.todoapp.domain.use_case.tasksList.GetTasksListUseCase
import com.mvvm.todoapp.domain.use_case.tasksList.GetTasksListUseCaseImpl
import com.mvvm.todoapp.domain.use_case.updateTasks.UpdateTaskUseCase
import com.mvvm.todoapp.domain.use_case.updateTasks.UpdateTaskUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    fun provideTaskListUseCase(repository: TodoRepository): GetTasksListUseCase {
        return GetTasksListUseCaseImpl(repository)
    }

    @Provides
    fun provideAddTaskUseCase(repository: TodoRepository): AddTaskUseCase {
        return AddTaskUseCaseImpl(repository)
    }

    @Provides
    fun provideUpdateTaskUseCase(repository: TodoRepository): UpdateTaskUseCase {
        return UpdateTaskUseCaseImpl(repository)
    }

    @Provides
    fun provideDeleteTaskUseCase(repository: TodoRepository): DeleteTaskUseCase {
        return DeleteTaskUseCaseImpl(repository)
    }
}