package com.example.forecastapp.view

import android.app.AlertDialog
import android.content.Context
import android.view.View
import androidx.core.content.res.TypedArrayUtils.getText
import com.example.forecastapp.R
import kotlinx.coroutines.delay

/**
 * Class stores user animation for ForecastFragment and CurrentFragment
 * */
class AnimationHelper {
    companion object{
        /**
         * Animate the view when user clicks
         * */
        suspend fun clickAnimate(view: View, milis: Long = 250L, scaleMin: Float = 0.7f){
            view.animate().scaleX(scaleMin).duration = milis
            view.animate().scaleY(scaleMin).duration = milis
            delay(milis)
            view.animate().scaleX(1f).duration = milis
            view.animate().scaleY(1f).duration = milis
        }

        /**
         * Simple fade out animation
         * */
        suspend fun fadeOutAnimate(view: View, milis: Long = 300L){
            view.animate().alpha(1f).duration = milis
            view.animate().alpha(1f).duration = milis
        }
    }
}