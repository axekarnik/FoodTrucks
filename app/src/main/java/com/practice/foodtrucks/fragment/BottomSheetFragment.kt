package com.practice.foodtrucks.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.practice.foodtrucks.Utils.Constants.KEY_FOODTRUCK_ADDR
import com.practice.foodtrucks.Utils.Constants.KEY_FOODTRUCK_CATEGORIES
import com.practice.foodtrucks.Utils.Constants.KEY_FOODTRUCK_END_TIME
import com.practice.foodtrucks.Utils.Constants.KEY_FOODTRUCK_NAME
import com.practice.foodtrucks.Utils.Constants.KEY_FOODTRUCK_START_TIME
import com.practice.foodtrucks.databinding.FragmentBottomSheetBinding

class BottomSheetFragment : BottomSheetDialogFragment() {
    lateinit var binding: FragmentBottomSheetBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomSheetBinding.inflate(LayoutInflater.from(context))
        val bundle = arguments
        if(bundle != null){
            binding.cellBottomSheet.txtName.text =  bundle.getString(KEY_FOODTRUCK_NAME)
            binding.cellBottomSheet.txtAddr.text = bundle.getString(KEY_FOODTRUCK_ADDR)
            binding.cellBottomSheet.txtCategories.text = bundle.getString(KEY_FOODTRUCK_CATEGORIES)
            binding.cellBottomSheet.txtTimings.text = bundle.getString(KEY_FOODTRUCK_START_TIME) + "-" + bundle.getString(
                KEY_FOODTRUCK_END_TIME)
        }

        return binding.root


    }



}