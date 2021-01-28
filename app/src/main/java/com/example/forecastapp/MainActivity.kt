package com.example.forecastapp

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.forecastapp.model.api.RetrofitInstance
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class MainActivity : AppCompatActivity() {
    private val ACCESS_LOCATION_CODE = 1
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupBottomNavigation()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        findUserLocation()
    }

    private fun findUserLocation(){
        val accessFineLocation = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val accessCoarseLocation = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        if (accessFineLocation != PackageManager.PERMISSION_GRANTED && accessCoarseLocation != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermissions()
        }
        else{
            Log.d("MainActivity", "Permission granted")
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if(location != null){
                Constants.currentLat = location.latitude
                Constants.currentLon = location.longitude
                Log.d("MainActivity", location.latitude.toString())
                Log.d("MainActivity", location.longitude.toString())
            }
        }

        fusedLocationClient.lastLocation.addOnCompleteListener {
            val location = it.result
            if(location != null){
                Constants.currentLat = location.latitude
                Constants.currentLon = location.longitude
                Log.d("MainActivity", location.latitude.toString())
                Log.d("MainActivity", location.longitude.toString())
            }
            else{
                Log.d("MainActivity", "Location is null")
            }
        }
    }

    // Todo: Test canceling permissions and application working without them. For now it work.
    private fun requestLocationPermissions(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
            AlertDialog.Builder(this)
                .setTitle("Permission needed")
                .setMessage("This permission is needed to check user geolocation.")
                .setPositiveButton("OK") { _ : DialogInterface, _ : Int ->
                    ActivityCompat.requestPermissions(this@MainActivity,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                        ACCESS_LOCATION_CODE)
                }
                .setNegativeButton("Cancel") { _ : DialogInterface, _ : Int -> }
                .create().show()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), ACCESS_LOCATION_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == ACCESS_LOCATION_CODE){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // testing API connection
//    private fun getTestResponse(){
//        GlobalScope.launch {
//            Log.d("MainActivity", "Wait for the response from ${RetrofitInstance.api
//                .getOneCallForecast(33.441792,-94.037689,Constants.DEFAULT_PARAMS,Constants.API_KEY).request().url}")
//            val response = RetrofitInstance.api
//                .getOneCallForecast(33.441792,-94.037689,Constants.DEFAULT_PARAMS,Constants.API_KEY).awaitResponse()
//            Log.d("MainActivity", "We have the response")
//
//            if(response.isSuccessful){
//                val data = response.body()!!
//
//                for(d in data.daily)
//                {
//                    Log.d("MainActivity", "${d.weather}")
//                }
//            }
//            else {
//                Log.d("MainActivity", "The response is not successful.")
//            }
//        }
//    }

    // Set up moving between fragments and changing action bar title
    private fun setupBottomNavigation(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        val appBarConfiguration =
            AppBarConfiguration(setOf(R.id.forecastFragment, R.id.currentFragment, R.id.listFragment))

        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)
    }
}