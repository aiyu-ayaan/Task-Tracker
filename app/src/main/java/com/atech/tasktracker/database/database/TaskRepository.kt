package com.atech.tasktracker.database.database

import com.atech.tasktracker.database.model.Task
import javax.inject.Inject


class TaskRepository @Inject constructor(
    private val taskDao: TaskDao
) {
    fun getTasks() = taskDao.getAllTasks()
    suspend fun insertTask(task: Task) = taskDao.insertTask(task)
    suspend fun updateTask(task: Task) = taskDao.updateTask(task)
    suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)
    suspend fun deleteAll() = taskDao.deleteAllTasks()
}