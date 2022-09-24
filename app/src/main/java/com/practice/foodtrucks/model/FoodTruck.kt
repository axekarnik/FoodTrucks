package com.practice.foodtrucks.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "food_truck_table")
data class FoodTruck(

    @PrimaryKey(autoGenerate = true)
    var id : Int,

    @SerializedName("dayofweekstr")
    var day: String,

    @SerializedName("starttime")
    var startTime: String,

    @SerializedName("endtime")
    var endTime: String,

    @SerializedName("start24")
    var startTime24: String,

    @SerializedName("end24")
    var endTime24: String,

    @SerializedName("latitude")
    var latitude: String,

    @SerializedName("longitude")
    var longitude: String,

    @SerializedName("optionaltext")
    var categories: String?,

    @SerializedName("applicant")
    var name: String,

    @SerializedName("location")
    var addr: String

)