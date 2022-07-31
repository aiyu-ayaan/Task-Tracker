package com.atech.tasktracker.ui.fragments.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.atech.tasktracker.R
import com.atech.tasktracker.database.model.Task
import com.atech.tasktracker.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding: FragmentHomeBinding by viewBinding()
    private val viewModel by viewModels<HomeViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val homeAdapter = HomeAdapter({ task ->
            increaseProgress(task)
        }, { task ->
            decreaseProgress(task)
        }, { task ->
            removeTask(task)
        }
        )
        binding.apply {
            recyclerView.apply {
                adapter = homeAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }
        lifecycleScope.launch {
            viewModel.tasks.collect {
                if (it.isEmpty())
                    binding.apply {
                        recyclerView.visibility = View.GONE
                        layoutEmpty.visibility = View.VISIBLE
                    }
                else
                    binding.apply {
                        recyclerView.visibility = View.VISIBLE
                        layoutEmpty.visibility = View.GONE
                    }
                homeAdapter.submitList(it)
            }
        }

        addMenuHost()
    }

    private fun addMenuHost() {
        val menuHost = requireActivity()
        val menuProvider = object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_home, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.remove_all -> {
                        viewModel.deleteAllTask()
                        return true
                    }
                }
                return false
            }

        }
        menuHost.addMenuProvider(menuProvider, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }

    private fun removeTask(task: Task) {
        viewModel.deleteTask(task).also {
            Toast.makeText(requireContext(), "Deleted !!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkDone(task: Task): Boolean = task.init_progress == task.final_progress

    private fun lessThatZero(task: Task): Boolean = task.init_progress <= 0

    private fun decreaseProgress(task: Task) {
        if (lessThatZero(task)) {
            Toast.makeText(requireContext(), "Progress can't be less that 0 !!", Toast.LENGTH_SHORT)
                .show()
            return
        }
        viewModel.updateTask(
            task.copy(
                init_progress = task.init_progress - 1
            )
        )
    }

    private fun increaseProgress(task: Task) {
        if (checkDone(task)) {
            Toast.makeText(requireContext(), "Task is completed !!", Toast.LENGTH_SHORT).show()
            return
        }
        viewModel.updateTask(
            task.copy(
                init_progress = task.init_progress + 1
            )
        )
    }

}
