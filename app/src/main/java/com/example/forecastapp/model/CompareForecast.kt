package com.example.forecastapp.model

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class CompareForecast(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {
        val tag = this.tags.first()
        Log.d("Compare: ", tag)
        return Result.success()
    }

}