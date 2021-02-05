package com.example.forecastapp.model.data

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.LocalDate

class Converters {
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun timestampToLocalDate(value: Long?): LocalDate?{
        return value?.let { LocalDate.ofEpochDay(it) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun localDateToTimestamp(date: LocalDate?): Long?{
        return date?.toEpochDay()
    }
}