package com.practice.foodtrucks.data

import android.util.Log
import androidx.paging.PagingSource
import com.practice.foodtrucks.Utils.Utils
import com.practice.foodtrucks.model.FoodTruck
import com.practice.foodtrucks.network.RetrofitApi
import java.lang.Exception

private const val STARTING_PAGE_INDEX = 1

/* Class for future use - pagination */
class FoodTruckPagingSource(val retrofitApi: RetrofitApi) :

    PagingSource<Int, FoodTruck>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FoodTruck > {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {

            val response = retrofitApi.getFoodTrucks(params.loadSize, position,1, "applicant",
                            Utils.constructQuery())
            LoadResult.Page(data = response,
            prevKey = if(position == STARTING_PAGE_INDEX) null else position-1,
            nextKey = if(response.isEmpty()) null else position+1
            )
        } catch (e : Exception){
            return LoadResult.Error(e)
        }
    }
}