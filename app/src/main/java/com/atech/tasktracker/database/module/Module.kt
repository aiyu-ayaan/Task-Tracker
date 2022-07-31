package com.atech.tasktracker.database.module

import android.content.Context
import androidx.room.Room
import com.atech.tasktracker.database.database.TaskDao
import com.atech.tasktracker.database.database.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object Module {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext
        content: Context,
        callback: TaskDatabase.TaskCallback
    ): TaskDatabase =
        Room.databaseBuilder(
            content,
            TaskDatabase::class.java,
            TaskDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()


    @Singleton
    @Provides
    fun provideTaskDao(
        database: TaskDatabase
    ): TaskDao = database.taskDao()
}