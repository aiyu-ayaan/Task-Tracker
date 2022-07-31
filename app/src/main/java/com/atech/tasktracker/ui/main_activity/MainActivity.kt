package com.atech.tasktracker.ui.main_activity

import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import android.viewbinding.library.activity.viewBinding
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.atech.tasktracker.NavGraphDirections
import com.atech.tasktracker.R
import com.atech.tasktracker.databinding.ActivityMainBinding
import com.atech.tasktracker.util.currentNavigationFragment
import com.google.android.material.color.MaterialColors
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by viewBinding()

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            setSupportActionBar(toolbar)
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

            navController = navHostFragment.findNavController()

            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.homeFragment,
                )
            )
            collapsingToolbar.setupWithNavController(toolbar, navController, appBarConfiguration)
            setupActionBarWithNavController(navController, appBarConfiguration)

            fabAddPassword.setOnClickListener {
                navigateToAdd()
            }
            appbarLayout.addOnOffsetChangedListener { _, verticalOffset ->
                if (abs(verticalOffset) - appbarLayout.totalScrollRange == 0) // collapsed
                    changeStatusBarToolbarColor(R.attr.scrimColor)
                else
                    changeStatusBarToolbarColor(android.viewbinding.library.R.attr.colorSurface)

            }
        }
        onDestinationChange()

    }

    private fun onDestinationChange() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> {
                    binding.fabAddPassword.show()
                }
                else -> {
                    binding.fabAddPassword.hide()
                }
            }

        }
    }

    private fun navigateToAdd() {
        getCurrentFragment()?.apply {
            exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
            reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)

            val action = NavGraphDirections.actionGlobalAddTaskFragment()
            navController.navigate(action)
        }
    }

    private fun changeStatusBarToolbarColor(@AttrRes colorCode: Int) =
        this.apply {
            try {
                window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window?.statusBarColor = MaterialColors.getColor(
                    this,
                    colorCode,
                    Color.WHITE
                )
                binding.toolbar.setBackgroundColor(
                    MaterialColors.getColor(
                        this,
                        colorCode,
                        Color.WHITE
                    )
                )
            } catch (e: Exception) {
                Toast.makeText(this, "${e.message}", Toast.LENGTH_SHORT).show()
            }
        }


    private fun getCurrentFragment(): Fragment? =
        supportFragmentManager.currentNavigationFragment

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}