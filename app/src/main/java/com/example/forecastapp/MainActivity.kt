package com.example.forecastapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.forecastapp.model.MyDatabase
import com.example.forecastapp.model.repository.HistoricalDailyWeatherRepository
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.forecastapp.model.HistoricalDailyModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         var historicalweather:HistoricalDailyWeatherRepository

        val historicalDao = MyDatabase.getDatabase(
                application
        ).historicalDailyDao()
        val historicalrepository = HistoricalDailyWeatherRepository(historicalDao)

        //var history=HistoricalDailyModel(0,1564,1234,23.54,45.87)
        //historicalrepository.add()

        //historicalweather.add()
        setupBottomNavigation()

    }

    /**
     * Setup moving between fragments and changing action bar title
     * */
    private fun setupBottomNavigation(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        val appBarConfiguration =
            AppBarConfiguration(setOf(R.id.forecastFragment, R.id.currentFragment, R.id.listFragment, R.id.listOpenFragment))

        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)
    }
}