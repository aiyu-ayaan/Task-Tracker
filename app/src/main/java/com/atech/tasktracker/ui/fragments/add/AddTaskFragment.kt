package com.atech.tasktracker.ui.fragments.add

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.atech.tasktracker.R
import com.atech.tasktracker.database.model.Task
import com.atech.tasktracker.databinding.LayoutAddFragmentBinding
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTaskFragment : Fragment(R.layout.layout_add_fragment) {

    private val binding: LayoutAddFragmentBinding by viewBinding()
    private val viewModel: AddViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            buttonAdd.setOnClickListener {
                validateTask()
            }
        }
    }

    private fun validateTask() = binding.apply {
        if (outlinedTextFieldTaskName.editText?.text!!.isBlank()) {
            outlinedTextFieldTaskName.error = "Task name is required"
            return@apply
        } else {
            outlinedTextFieldTaskName.error = null
        }

        if (outlinedTextInitialProgress.editText?.text!!.isBlank()) {
            outlinedTextInitialProgress.error = "Initial progress is required"
            return@apply
        } else {
            outlinedTextInitialProgress.error = null
        }
        if (outlinedTextFieldFinalProgress.editText?.text!!.isBlank()) {
            outlinedTextFieldFinalProgress.error = "Final progress is required"
            return@apply
        } else {
            outlinedTextFieldFinalProgress.error = null
        }

        if (outlinedTextInitialProgress.editText?.text.toString()
                .toInt() > outlinedTextFieldFinalProgress.editText?.text.toString().toInt()
        ) {
            outlinedTextFieldFinalProgress.error =
                "Final progress must be greater than initial progress"
            return@apply
        } else {
            outlinedTextFieldFinalProgress.error = null
        }
        addTask(
            outlinedTextFieldTaskName.editText!!.text.toString(),
            outlinedTextInitialProgress.editText!!.text.toString().toInt(),
            outlinedTextFieldFinalProgress.editText!!.text.toString().toInt()
        )
    }

    private fun addTask(taskName: String, initialProgress: Int, finalProgress: Int) {
        viewModel.addTask(Task(taskName, initialProgress, finalProgress))
        findNavController().navigateUp()
    }

}