package com.german.eshop.customer.ui.activity

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.german.eshop.customer.R
import com.german.eshop.customer.databinding.ActivityMainBinding
import com.german.eshop.customer.ui.adapter.SearchAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController : NavController
    private lateinit var autoComplete: SearchView.SearchAutoComplete

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setupUI()
        setupNavigationController()
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_main_search, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView: SearchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setIconifiedByDefault(true)
        searchView.isIconified = false
        autoComplete = searchView.findViewById<SearchView.SearchAutoComplete>(androidx.appcompat.R.id.search_src_text)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                autoComplete.dismissDropDown()
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean = true
        })
        val list = mutableListOf<String>()
        list.add("Puta")
        list.add("Puta 1")
        list.add("German")
        list.add("German Puta :v")
        autoComplete.setDropDownBackgroundResource(R.color.white);
        autoComplete.dropDownAnchor = R.id.toolbar
        autoComplete.threshold = 1
        autoComplete.setAdapter(SearchAdapter(this, list))
        autoComplete.onItemClickListener = this
        return super.onCreateOptionsMenu(menu)
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
            startActivity(Intent(this, LoginActivity::class.java))
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean = NavigationUI.navigateUp(navController, binding.drawerLayout)

    override fun onItemClick(adapterView: AdapterView<*>, p1: View?, position: Int, p3: Long) {
        val item = adapterView.getItemAtPosition(position).toString()
        autoComplete.setText(item)
        Log.e("MainActivity", item)
    }
}