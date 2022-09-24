package com.practice.foodtrucks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.practice.foodtrucks.Utils.Resource
import com.practice.foodtrucks.model.FoodTruck
import com.practice.foodtrucks.repository.FoodTruckRepo

class FoodTruckViewModel(val repo : FoodTruckRepo) :ViewModel() {

    /* For future use - pagination */
    fun getFoodTrucksNearby() = repo.getFoodTrucksNearby()

    fun getAllFoodTrucksOpen() = liveData {
        try{
            emit(Resource.success(data = repo.getAllFoodTrucksOpen()))
        } catch (e : Exception){
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }

    }

     fun addFoodTrucksToDB(foodTrucks: List<FoodTruck>) = liveData{
         try{
             emit(Resource.success(data = repo.addFoodTrucksToDB(foodTrucks)))
         } catch (e : Exception){
             emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
         }
    }

    fun getAllFoodTrucksFromDB() = liveData {
        try{
            emit(Resource.success(data = repo.getAllFoodTrucksFromDB()))
        } catch (e : Exception){
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

    fun deleteDataFromDB() = liveData<Unit> {
        emit(repo.deleteFoodTrucksFromDB())
    }


}