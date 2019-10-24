package com.silentnuke.shifts.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.google.gson.Gson

/**
 * Quick and dirty implementation.
 */
object ShiftsManager {
    private val _colors = MutableLiveData(listOf("Red", "Green", "Blue"))
    val colors: LiveData<List<String>> = _colors

    private val _roles: MutableLiveData<List<String>> =
        MutableLiveData(listOf("Waiter", "Prep", "Cook", "Front of House"))
    val roles: LiveData<List<String>> = _roles

    private val _remoteShifts = MutableLiveData<List<Shift>>().apply { value = emptyList() }
    private val _localShifts =
        MutableLiveData<MutableList<Shift>>().apply { value = mutableListOf() }
    val shifts: LiveData<List<Shift>> = MediatorLiveData<List<Shift>>().apply {
        this.addSource(_remoteShifts) {
            this.value =
                (it + (_localShifts.value ?: emptyList())).sortedByDescending { it.startDate }
        }
        this.addSource(_localShifts) {
            this.value =
                (it + (_remoteShifts.value ?: emptyList())).sortedByDescending { it.startDate }
        }
    }

    val employess: LiveData<List<String>> = Transformations.map(_remoteShifts) {
        val employees = mutableListOf<String>()
        for (shift in it)
            if (!employees.contains(shift.name))
                employees.add(shift.name)
        employees
    }

    fun addShift(item: Shift) {
        _localShifts.value!!.add(item)
        _localShifts.value = _localShifts.value
    }

    fun loadShifts(context: Context) {
        val json = context.assets.open("shifts.json").bufferedReader()
        val gson = Gson()
        val shiftsResponse = gson.fromJson(json, ShiftsResponse::class.java);
        _remoteShifts.value = shiftsResponse.shifts
    }

}
