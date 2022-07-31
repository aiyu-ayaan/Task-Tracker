package com.atech.tasktracker.ui.fragments.home

import android.animation.LayoutTransition
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.atech.tasktracker.R
import com.atech.tasktracker.database.model.DiffUtilTask
import com.atech.tasktracker.database.model.Task
import com.atech.tasktracker.databinding.RowTaskBinding

class HomeAdapter(
    private val increaseListener: (Task) -> Unit,
    private val decreaseListener: (Task) -> Unit,
    private val deleteListener: (Task) -> Unit
) : ListAdapter<Task, HomeAdapter.HomeViewHolder>(DiffUtilTask()) {

    inner class HomeViewHolder(
        private val binding: RowTaskBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.constraintLayout.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
            binding.constraintLayout.setOnClickListener {
                val visible =
                    if (binding.linearLayout.visibility == View.GONE) View.VISIBLE else View.GONE
                TransitionManager.beginDelayedTransition(binding.constraintLayout, AutoTransition())
                binding.apply {
                    linearLayout.visibility = visible
                    dividerTask.visibility = visible
                }
            }
            binding.apply {
                buttonIncrease.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION)
                        increaseListener(getItem(position))

                }
                buttonDecrease.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION)
                        decreaseListener(getItem(position))
                }
                buttonDelete.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION)
                        deleteListener(getItem(position))
                }
            }
        }

        fun bind(task: Task) {
            binding.apply {
                textViewTaskName.text = task.name
                textViewTaskProgress.text = binding.root.context.getString(
                    R.string.task_text_completed,
                    task.init_progress.toString(),
                    task.final_progress.toString()
                )
                progressIndicatorTask.progress = task.process.toInt()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder =
        HomeViewHolder(
            RowTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}