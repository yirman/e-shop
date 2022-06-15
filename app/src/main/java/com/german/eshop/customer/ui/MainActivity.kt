package com.german.eshop.customer.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.german.eshop.customer.R
import com.german.eshop.customer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setupUI()
        setupNavigationController()
    }

    private fun setupUI(){
        binding.navView
            .getHeaderView(0)
            .findViewById<TextView>(R.id.title).text =
            "${getString(R.string.app_name).toCharArray()[0]}!"
        binding.navView
            .getHeaderView(0)
            .findViewById<TextView>(R.id.message).text = getString(R.string.welcome)
    }

    private fun setupNavigationController() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)
        binding.navView.menu.findItem(R.id.sign_in).setOnMenuItemClickListener {
            binding.drawerLayout.close()
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean = NavigationUI.navigateUp(navController, binding.drawerLayout)
}