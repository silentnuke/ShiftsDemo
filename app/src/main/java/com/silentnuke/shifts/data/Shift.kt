package com.silentnuke.shifts.data

import com.google.gson.annotations.SerializedName

data class Shift constructor(
    @SerializedName("name") val name: String,
    @SerializedName("role") val role: String,
    @SerializedName("start_date") val startDate: String,
    @SerializedName("end_date") val endDate: String,
    @SerializedName("color") val color: String
)
