package com.practice.foodtrucks.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.practice.foodtrucks.model.FoodTruck

@Dao
interface FoodTruckDao {

    @Insert
    suspend fun insertAllFoodTrucks(foodtrucks : List<FoodTruck>)

    @Query("SELECT * FROM food_truck_table")
    suspend fun getAllFoodTrucks() : List<FoodTruck>

    @Query("DELETE FROM food_truck_table")
    suspend fun deleteAllFoodTrucks()

}