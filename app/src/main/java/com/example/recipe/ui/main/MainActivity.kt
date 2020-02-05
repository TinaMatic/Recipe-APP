package com.example.recipe.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.recipe.R
import com.example.recipe.settings.SettingsFragment
import com.example.recipe.ui.favourites.FavouritesFragment
import com.example.recipe.ui.home.HomeFragment
import com.example.recipe.ui.search.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
//        setSupportActionBar(toolbar)

        val navController = findNavController(R.id.nav_host_fragment)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_favourites,
                R.id.nav_search,
                R.id.nav_settings
            ), drawer_layout)

        setupActionBarWithNavController(navController, appBarConfiguration)
        nav_view.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.nav_home){
            setFragment(HomeFragment())
        } else if(item.itemId == R.id.nav_favourites){
            setFragment(FavouritesFragment())
        } else if(item.itemId == R.id.nav_search){
            setFragment(SearchFragment())
        } else if(item.itemId == R.id.nav_settings){
            setFragment(SettingsFragment())
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {

        val fragmentManager = supportFragmentManager.findFragmentById(R.id.flContainer)?.childFragmentManager

        if(fragmentManager != null && fragmentManager.backStackEntryCount > 0){
            fragmentManager.popBackStack()
        }else{
            super.onBackPressed()
        }
    }

    private fun setFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.flContainer, fragment)
            .commit()
    }
}
