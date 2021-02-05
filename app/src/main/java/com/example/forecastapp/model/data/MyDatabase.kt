package com.example.forecastapp.model.data
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.forecastapp.model.HistDaily

@Database(entities = [HistDaily::class ], version = 8, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MyDatabase:RoomDatabase()
{
    abstract fun historicalDailyDao(): HistoricalDailyDao

    companion object {
        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getDatabase(context: Context): MyDatabase {
            val tempInstance = INSTANCE

            if(tempInstance != null){
                return tempInstance
            }
            else
                synchronized(this){
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        MyDatabase::class.java,
                        "my_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                    return instance
                }
        }
    }
}