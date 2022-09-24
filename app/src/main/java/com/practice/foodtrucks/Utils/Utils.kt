package com.practice.foodtrucks.Utils

import android.graphics.Color
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.practice.foodtrucks.R
import java.text.SimpleDateFormat
import java.util.*

class Utils {

    companion object {
        var IS_DATA_REFRESHED = false
        fun getCurrentDay() : Int {
            var date = Date()
            return date.day
        }

        fun getCurrentTime() : String {
            var date = Date()
            var time = SimpleDateFormat("HH:mm")
            return time.format(date)
        }

        fun constructQuery() : String {
            var currentTime = getCurrentTime()
            return "start24 < '$currentTime' AND end24 > '$currentTime'"
        }

        fun showErrorInSnackBar(contextView: View, errorMessage: String){
           Snackbar.make(contextView, errorMessage, Snackbar.LENGTH_LONG)
                .setBackgroundTint(Color.BLACK)
                .setTextColor(Color.WHITE)
                .show()

        }

    }
}