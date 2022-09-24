package com.practice.foodtrucks.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.*
import com.practice.foodtrucks.Utils.Constants.APPLICANT
import com.practice.foodtrucks.Utils.Utils
import com.practice.foodtrucks.data.FoodTruckPagingSource
import com.practice.foodtrucks.db.FoodTruckDatabase
import com.practice.foodtrucks.model.FoodTruck
import com.practice.foodtrucks.network.RetrofitApi
import com.practice.foodtrucks.network.RetrofitClient

class FoodTruckRepo(context: Context) {
    private val dao = FoodTruckDatabase.getInstance(context).dao
    private val retrofitApi: RetrofitApi = RetrofitClient.getInstance().create(RetrofitApi::class.java)

    /* For future use - Pagination */
    fun getFoodTrucksNearby(): LiveData<PagingData<FoodTruck>> {
        val pager = Pager(
            config = PagingConfig(
                pageSize = 5,
                maxSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                FoodTruckPagingSource(retrofitApi)
            }
        );

        return  pager.liveData
    }

    suspend fun getAllFoodTrucksOpen() = retrofitApi.getAllFoodTrucksOpen(Utils.getCurrentDay(),
        APPLICANT, Utils.constructQuery())

    suspend fun addFoodTrucksToDB(foodTrucks: List<FoodTruck>)  = dao.insertAllFoodTrucks(foodTrucks)

    suspend fun getAllFoodTrucksFromDB() = dao.getAllFoodTrucks()

    suspend fun deleteFoodTrucksFromDB() = dao.deleteAllFoodTrucks()
}