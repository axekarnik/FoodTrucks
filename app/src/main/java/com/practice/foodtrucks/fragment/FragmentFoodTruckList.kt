package com.practice.foodtrucks.fragment

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.practice.foodtrucks.R
import com.practice.foodtrucks.Utils.Status
import com.practice.foodtrucks.Utils.Utils
import com.practice.foodtrucks.Utils.Utils.Companion.IS_DATA_REFRESHED
import com.practice.foodtrucks.adapter.FoodTruckAdapter
import com.practice.foodtrucks.databinding.FragmentListFoodTruckBinding
import com.practice.foodtrucks.model.FoodTruck
import com.practice.foodtrucks.repository.FoodTruckRepo
import com.practice.foodtrucks.viewmodel.FoodTruckViewModel
import com.practice.foodtrucks.viewmodel.FoodTruckViewModelFactory

class FragmentFoodTruckList : Fragment(R.layout.fragment_list_food_truck) {
    lateinit var binding: FragmentListFoodTruckBinding
    lateinit var viewModel: FoodTruckViewModel
    lateinit var foodTruckList : List<FoodTruck>
    var mAdapter = FoodTruckAdapter()
    companion object {
        val LOG_TAG = FragmentFoodTruckList.javaClass.simpleName
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListFoodTruckBinding.bind(view)

        /* Set up menu */
        setHasOptionsMenu(true)

        val repo = FoodTruckRepo(requireContext())

        val factory = FoodTruckViewModelFactory(repo)
        viewModel = ViewModelProvider(this, factory).get(FoodTruckViewModel::class.java)

        binding.listFt.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
            setHasFixedSize(true)
        }

        Log.v(LOG_TAG, "Is data refreshed $IS_DATA_REFRESHED" )
        if(IS_DATA_REFRESHED)
            getFoodTruckDataFromDB()
        else{
            binding.icLoading.visibility = View.VISIBLE
            getFoodTruckDataFromApi()
        }


    }

    fun getFoodTruckDataFromApi() {
        /* Api to get all the food trucks */
        viewModel?.getAllFoodTrucksOpen().observe(viewLifecycleOwner, Observer {
            binding.icLoading.visibility = View.GONE
            it?.let{
                Log.v(LOG_TAG, "Status is " + it.status)
                when(it.status) {
                    Status.SUCCESS -> {
                        foodTruckList = it?.data!!

                        if(foodTruckList.size == 0)
                            binding.txtNoData.visibility = View.VISIBLE
                        else
                            binding.txtNoData.visibility = View.GONE

                        mAdapter.setDataList(foodTruckList)
                        IS_DATA_REFRESHED = true
                        /* Check if there is data in the DB, clear it */
                        deleteDataFromDB(foodTruckList)
                    }
                    Status.ERROR -> {
                        Utils.showErrorInSnackBar(binding.parentLayout, it.message.toString())
                    }
                }
            }



        })
    }

    fun deleteDataFromDB(foodTruckList: List<FoodTruck>) {
        viewModel.deleteDataFromDB().observe(viewLifecycleOwner, Observer {
            Log.v(LOG_TAG, "Delete data from db ")

            /* Insert new list into db */
            insertTrucksIntoDB(foodTruckList)

        })
    }

    fun insertTrucksIntoDB(foodTruckList : List<FoodTruck>) {
        if(foodTruckList != null) {
            viewModel.addFoodTrucksToDB(foodTruckList).observe(viewLifecycleOwner, Observer {
                Log.v(LOG_TAG, "Food trucks added to db " )
            })
        }else
            Log.v(LOG_TAG ,"Cannot add foodtrucks to list")

    }
    fun getFoodTruckDataFromDB() {
        viewModel.getAllFoodTrucksFromDB().observe(viewLifecycleOwner, Observer {data->

            if(data.data?.size!! > 0) {
                /* Indicates we had data in the cache, display that data */
                Log.v(LOG_TAG, "Data in the cache display from cache")
                mAdapter.setDataList(data.data!!)
            }else {
                 /* Indicates cache is empty */
                Log.v(LOG_TAG, "Fetching data from db")
                getFoodTruckDataFromApi()
            }
        })
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        menu.findItem(R.id.menu_trucklist).isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId ) {
            R.id.menu_maps -> {
                findNavController().navigate(R.id.action_foodTruckList_to_foodTruckMapFragment)
            }
        }
        return true
    }

}