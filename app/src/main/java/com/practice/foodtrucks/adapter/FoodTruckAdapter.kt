package com.practice.foodtrucks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practice.foodtrucks.databinding.CellFoodTruckBinding
import com.practice.foodtrucks.model.FoodTruck
import java.util.*

class FoodTruckAdapter : RecyclerView.Adapter<FoodTruckAdapter.FoodTruckViewHolder>() {
    var foodTruckList : List<FoodTruck> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodTruckViewHolder {
        val binding = CellFoodTruckBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodTruckViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return foodTruckList.size
    }

    override fun onBindViewHolder(holder: FoodTruckViewHolder, position: Int) {
        val item = foodTruckList[position]
        holder.setData(item)
    }

    fun setDataList(data : List<FoodTruck>) {
        foodTruckList = data
        notifyDataSetChanged()
    }
    class FoodTruckViewHolder(val binding: CellFoodTruckBinding) : RecyclerView.ViewHolder(binding.root){
        var name = binding.txtName
        var addr = binding.txtAddr
        var category = binding.txtCategories
        var time = binding.txtTimings

        fun setData(data : FoodTruck){
            name.text = data.name
            addr.text = data.addr
            category.text = data.categories
            time.text = data.startTime + "-" + data.endTime
        }
    }


}