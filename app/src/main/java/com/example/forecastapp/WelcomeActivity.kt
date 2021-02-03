package com.example.forecastapp

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.example.forecastapp.viewmodel.ForecastViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class WelcomeActivity : AppCompatActivity() {
    private val ACCESS_LOCATION_CODE = 1
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        findUserLocation()
    }

    private fun findUserLocation(){
        val accessFineLocation = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val accessCoarseLocation = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        if (accessFineLocation != PackageManager.PERMISSION_GRANTED || accessCoarseLocation != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
            requestLocationPermissions(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        else{
            Log.d("MainActivity", "Permission granted")
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if(location != null){
                ForecastViewModel.defaultLat = location.latitude
                ForecastViewModel.defaultLon = location.longitude
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    // Todo: Test canceling permissions and application working without them. For now it work.
    private fun requestLocationPermissions(permission: String){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, permission)){
            AlertDialog.Builder(this)
                .setTitle("Permission needed")
                .setMessage("This permission is needed to check user geolocation.")
                .setPositiveButton("OK") { _ : DialogInterface, _ : Int ->
                    ActivityCompat.requestPermissions(this@WelcomeActivity,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                        ACCESS_LOCATION_CODE)
                }
                .setNegativeButton("Cancel") { _ : DialogInterface, _ : Int -> }
                .create().show()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(permission), ACCESS_LOCATION_CODE)
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
}