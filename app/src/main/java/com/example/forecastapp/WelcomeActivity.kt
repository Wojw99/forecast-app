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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WelcomeActivity : AppCompatActivity() {
    private val locationCode = 100
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        findUserLocation()
    }

    /**
     * Checks if user gave permissions
     * */
    private fun findUserLocation(){
        val accessFineLocation = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)

        // Check if the application granted permissions
        if (accessFineLocation != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationCode)
        } else {
            // Wait for user geocode
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if(location != null){
                    gotToMain(location.latitude, location.longitude)
                } else {
                    gotToMain()
                }
            }.addOnFailureListener {
                showAlertBox()
            }
        }
    }

    /**
     * Starts the MainActivity with setting default lat and lon as 0,0
     * */
    private fun gotToMain(lat: Double = 0.0, lon: Double = 0.0){
        ForecastViewModel.defaultLat = lat
        ForecastViewModel.defaultLon = lon
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * Gets permissions from user and shows alert to them
     * */
    private fun requestLocationPermissions(permission: String){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, permission)){
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.perm_needed))
                .setMessage(getString(R.string.perm_needed_desc))
                .setPositiveButton(getString(R.string.ok)) { _ : DialogInterface, _ : Int ->
                    ActivityCompat.requestPermissions(this@WelcomeActivity,
                        arrayOf(permission), locationCode)
                }
                .setNegativeButton(getString(R.string.cancel)) { _ : DialogInterface, _ : Int -> }
                .create().show()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(permission), locationCode)
        }
    }

    /**
     * Shows alert with information about canceled granting location permissions.
     * */
    private fun showAlertBox(){
        AlertDialog.Builder(this)
                .setTitle(getString(R.string.error))
                .setMessage(getString(R.string.gps_check))
                .setPositiveButton(getString(R.string.ok)) { _ : DialogInterface, _ : Int ->
                    gotToMain()
                }
                .create().show()
    }

    /**
     * Part of requestLocationPermissions() scope. Shows alert to the user.
     * */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == locationCode){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                findUserLocation()
            }
            else{
                showAlertBox()
            }
        }
    }
}