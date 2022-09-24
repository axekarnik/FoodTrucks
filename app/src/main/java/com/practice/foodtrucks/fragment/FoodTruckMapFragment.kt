package com.practice.foodtrucks.fragment

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.practice.foodtrucks.R
import com.practice.foodtrucks.Utils.Constants.KEY_FOODTRUCK_ADDR
import com.practice.foodtrucks.Utils.Constants.KEY_FOODTRUCK_CATEGORIES
import com.practice.foodtrucks.Utils.Constants.KEY_FOODTRUCK_END_TIME
import com.practice.foodtrucks.Utils.Constants.KEY_FOODTRUCK_NAME
import com.practice.foodtrucks.Utils.Constants.KEY_FOODTRUCK_START_TIME
import com.practice.foodtrucks.databinding.FragmentFoodTruckMapBinding
import com.practice.foodtrucks.model.FoodTruck
import com.practice.foodtrucks.repository.FoodTruckRepo
import com.practice.foodtrucks.viewmodel.FoodTruckViewModel
import com.practice.foodtrucks.viewmodel.FoodTruckViewModelFactory


class FoodTruckMapFragment : Fragment(R.layout.fragment_food_truck_map),
    OnMapReadyCallback, GoogleMap.OnMarkerClickListener{

    lateinit var binding: FragmentFoodTruckMapBinding
    lateinit  var googleMap: GoogleMap
    lateinit var viewModel : FoodTruckViewModel
    lateinit var foodTruckList : List<FoodTruck>
    lateinit var bottomSheetFragment : BottomSheetFragment


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentFoodTruckMapBinding.bind(view)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment

        bottomSheetFragment = BottomSheetFragment()

        /* Initializing repo */
        val repo = FoodTruckRepo(requireContext())

        /* Initializing view model */
        var viewModelFactory = FoodTruckViewModelFactory(repo)
        viewModel = ViewModelProvider(this, viewModelFactory).get(FoodTruckViewModel::class.java)


        /* Fetch food trucks from db */
        viewModel.getAllFoodTrucksFromDB().observe(viewLifecycleOwner, Observer {
            foodTruckList = it.data!!

            /* Setup map */
            mapFragment.getMapAsync(this)
        })

    }


    /* Styling the marker */
    private fun createMarker() : MarkerOptions{
        return MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
    }

    override fun onMapReady(map: GoogleMap?) {
        map?.let {  googleMap = map}

        /*Set click listener for marker on the map */
        googleMap.setOnMarkerClickListener(this)

        for(foodTruck in foodTruckList){
            var marker = googleMap.addMarker(createMarker().
            position(LatLng(foodTruck.latitude.toDouble(), foodTruck.longitude.toDouble()))
                .title(foodTruck.name)
            )
            marker.tag = foodTruck
            updateMapZoomLevel()
        }
        setHasOptionsMenu(true)
    }

    fun updateMapZoomLevel() {
        var latLng = LatLng(foodTruckList[0].latitude.toDouble(), foodTruckList[0].longitude.toDouble())
        val center = CameraUpdateFactory.newLatLng(latLng)
        val zoom = CameraUpdateFactory.zoomTo(11f)
        googleMap.moveCamera(center)
        googleMap.animateCamera(zoom)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        menu.findItem(R.id.menu_maps).isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId ) {
            R.id.menu_trucklist -> {
                findNavController().navigate(R.id.action_foodTruckMapFragment_to_foodTruckList)
            }
        }
        return true
    }

    override fun onMarkerClick(marker: Marker?) :Boolean {
        var truck: FoodTruck = marker?.tag as FoodTruck

        /* Constructing bundle for bottom sheet */
        var bundle = bundleOf(KEY_FOODTRUCK_NAME to truck.name,
            KEY_FOODTRUCK_ADDR to truck.addr,
            KEY_FOODTRUCK_CATEGORIES to truck.categories,
            KEY_FOODTRUCK_END_TIME to truck.endTime,
            KEY_FOODTRUCK_START_TIME to truck.startTime
            )

        findNavController().navigate(R.id.action_foodTruckMapFragment_to_bottomSheetFragment, bundle)
        return false
    }
}