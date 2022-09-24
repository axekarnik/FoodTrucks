package com.practice.foodtrucks.network

import com.practice.foodtrucks.model.FoodTruck
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitApi {
    companion object {
        const val APP_TOKEN = "v9PuzKT4WIusTjjuDBdVPrx8V";
    }

    /* For future use - pagination */
    @Headers("X-App-Token: $APP_TOKEN")
    @GET("jjew-r69b.json")
    suspend fun getFoodTrucks(@Query("\$limit") limit: Int,
                              @Query("\$offset") offset: Int,
                              @Query("dayorder") dayOrder: Int,
                              @Query("\$order") order: String,
                              @Query("\$where") where : String
    ) :List<FoodTruck>

    @Headers("X-App-Token: $APP_TOKEN")
    @GET("jjew-r69b.json")
    suspend fun getAllFoodTrucksOpen(@Query("dayorder") dayOrder: Int,
                              @Query("\$order") order: String,
                              @Query("\$where") where : String
    ) :List<FoodTruck>

}