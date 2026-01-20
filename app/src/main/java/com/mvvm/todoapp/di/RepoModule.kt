package com.mvvm.todoapp.di

import com.mvvm.todoapp.data.database.TodoDatabase
import com.mvvm.todoapp.data.repositories.TodoRepositoryImpl
import com.mvvm.todoapp.domain.repositories.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepoModule {
    @Provides
    fun provideTodoRepository(database: TodoDatabase): TodoRepository {
        return TodoRepositoryImpl(database)
    }
}