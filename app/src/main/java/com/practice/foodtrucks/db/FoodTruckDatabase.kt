package com.practice.foodtrucks.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.practice.foodtrucks.model.FoodTruck

@Database(entities = [FoodTruck::class], version = 1)
abstract class FoodTruckDatabase : RoomDatabase(){
     abstract val  dao : FoodTruckDao

    companion object{
        @Volatile
        private var INSTANCE : FoodTruckDatabase? = null
        fun getInstance(context: Context):FoodTruckDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance==null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FoodTruckDatabase::class.java,
                        "food_truck_database"
                    ).build()
                }
                return instance
            }
        }

    }
}