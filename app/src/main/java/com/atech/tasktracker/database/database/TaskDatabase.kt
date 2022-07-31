package com.atech.tasktracker.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.atech.tasktracker.database.model.Task
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {


    abstract fun taskDao(): TaskDao

    companion object {
        const val DATABASE_NAME = "task_database"
    }

    class TaskCallback @Inject constructor(
        private val database: Provider<TaskDatabase>,
    ) : RoomDatabase.Callback() {

        @OptIn(DelicateCoroutinesApi::class)
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            GlobalScope.launch {
                database.get().taskDao().insertTask(Task("Learn Python", 2, 25))
                database.get().taskDao().insertTask(Task("Learn Java", 10, 30))
            }
        }
    }
}