package com.atech.tasktracker.ui.fragments.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atech.tasktracker.database.database.TaskRepository
import com.atech.tasktracker.database.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {
    fun addTask(task: Task) = viewModelScope.launch {
        taskRepository.insertTask(task)
    }
}