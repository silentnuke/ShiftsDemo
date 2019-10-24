package com.silentnuke.shifts.data

import com.google.gson.annotations.SerializedName

data class ShiftsResponse constructor(
    @SerializedName("shifts") val shifts: List<Shift>
)