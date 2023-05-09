package com.example.evoucher.ui

import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.evoucher.R
import com.example.evoucher.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Delay
import java.util.*
import kotlin.concurrent.timerTask

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar!!.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.home_nav_host_fragment)
    }

    override fun onResume() {
        super.onResume()

        Log.d("MainActivity", "onResume")
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (navController.currentDestination?.id == R.id.homeFragment
            || navController.currentDestination?.id == R.id.loginFragment) {
            finish()
        } else {
            super.onBackPressed()
        }
    }
}