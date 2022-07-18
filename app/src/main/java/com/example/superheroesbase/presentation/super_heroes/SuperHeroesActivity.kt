package com.example.superheroesbase.presentation.super_heroes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.superheroesbase.R
import com.example.superheroesbase.databinding.ActivityMainBinding

class SuperHeroesActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    var toolbarTitle: TextView? = null
    var toolbarBackButton: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(initViewBinding())
        initActionBar()
        initNavHost()
    }

    override fun onResume() {
        super.onResume()
        toolbarTitle?.text = getString(R.string.app_name)
        toolbarBackButton?.visibility = View.GONE
    }

    private fun initActionBar() {
        binding.layoutActionBar.apply {
            setSupportActionBar(toolbar)
            toolbar.visibility = View.VISIBLE
            toolbarTitle = titleText
            toolbarBackButton = backButton
            backButton.setOnClickListener {
                onBackPressed()
            }
        }
    }
    private fun initViewBinding(): View {
        binding = ActivityMainBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun initNavHost() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navSuperHeroes) as NavHostFragment
        navController = navHostFragment.navController
    }

}