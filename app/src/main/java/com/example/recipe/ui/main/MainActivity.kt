package com.example.recipe.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    var navigationPositiontack = Stack<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        //creates link between DrawerLayout and ActionBar
        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.drawerOpen, R.string.drawerClose)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()


        initView()

    }

    private fun initView(){

        navigationPositiontack.push(R.id.nav_home)
        toolbar.title = getString(R.string.menu_home)
        setFragment(HomeFragment())
        nav_view.setCheckedItem(R.id.nav_home)

        nav_view.setNavigationItemSelectedListener { item->
            when(item.itemId){
                R.id.nav_home-> {
                    toolbar.title = getString(R.string.menu_home)
                    navigationPositiontack.push(R.id.nav_home)
                    setFragment(HomeFragment())
                }
                R.id.nav_favourites -> {
                    toolbar.title = getString(R.string.menu_favourites)
                    navigationPositiontack.push(R.id.nav_favourites)
                    setFragment(FavouritesFragment())
                }
                R.id.nav_search -> {
                    toolbar.title = getString(R.string.menu_search)
                    navigationPositiontack.push(R.id.nav_search)
                    setFragment(SearchFragment())
                }
                R.id.nav_settings -> {
                    toolbar.title = getString(R.string.menu_settings)
                    navigationPositiontack.push(R.id.nav_settings)
                    setFragment(SettingsFragment())
                }
            }

            //set item as selected to persist highlight
            item.isChecked = true
            //close drawer when item is tapped
            drawer_layout.closeDrawers()

            true
        }
    }

    override fun onBackPressed() {

        if(drawer_layout.isDrawerOpen(GravityCompat.START)){
            drawer_layout.closeDrawer(GravityCompat.START)
        }

        val fragmentManager = supportFragmentManager.findFragmentById(R.id.flContainer)?.childFragmentManager

        if(fragmentManager != null && supportFragmentManager.backStackEntryCount > 0){
            fragmentManager.popBackStack()
            val navPosition = navigationPositiontack.pop()
            nav_view.setCheckedItem(navPosition)
            toolbar.title = getCorrectTitle(navPosition)
            getCorrectFragment(navPosition)

        }else{
//            super.onBackPressed()
            finish()
        }
    }

    private fun setFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.flContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun getCorrectTitle(navPosition: Int): String{
        return when(navPosition){
            R.id.nav_home -> getString(R.string.menu_home)
            R.id.nav_favourites -> getString(R.string.menu_favourites)
            R.id.nav_search -> getString(R.string.menu_search)
            R.id.nav_settings -> getString(R.string.menu_settings)
            else -> ""
        }
    }

    private fun getCorrectFragment(navPosition: Int){
        when(navPosition){
            R.id.nav_home -> setFragment(HomeFragment())
            R.id.nav_favourites -> setFragment(FavouritesFragment())
            R.id.nav_search -> setFragment(SearchFragment())
            R.id.nav_settings -> setFragment(SettingsFragment())
        }
    }
}
