package com.atech.tasktracker.database.database

import androidx.room.*
import com.atech.tasktracker.database.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)


    @Update
    suspend fun updateTask(task: Task)


    @Delete
    suspend fun deleteTask(task: Task)


    @Query("SELECT * FROM task_table")
    fun getAllTasks(): Flow<List<Task>>


    @Query("DELETE FROM task_table")
    suspend fun deleteAllTasks()
}