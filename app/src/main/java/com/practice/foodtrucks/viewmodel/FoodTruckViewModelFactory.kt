package com.practice.foodtrucks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.practice.foodtrucks.repository.FoodTruckRepo
import java.lang.IllegalArgumentException

class FoodTruckViewModelFactory (val repo: FoodTruckRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FoodTruckViewModel::class.java))
            return FoodTruckViewModel(repo) as T
        throw  IllegalArgumentException("Unknown View model class")
    }
}