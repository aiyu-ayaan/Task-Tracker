package com.atech.tasktracker.database.model

import androidx.annotation.Keep
import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = false)
    val name: String,
    val init_progress: Int,
    val final_progress: Int,
    val created: Long = System.currentTimeMillis(),
) {
    @Ignore
    val process = (init_progress.toDouble() / final_progress.toDouble()) * 100.0
}


class DiffUtilTask : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean =
        oldItem.name == newItem.name


    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean =
        oldItem == newItem

}