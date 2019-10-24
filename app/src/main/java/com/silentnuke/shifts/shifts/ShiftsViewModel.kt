package com.silentnuke.shifts.shifts

import android.app.Application
import androidx.lifecycle.*
import com.silentnuke.shifts.data.Shift
import com.silentnuke.shifts.data.ShiftsManager
import kotlinx.coroutines.launch

class ShiftsViewModel(application: Application) : AndroidViewModel(application) {
    val shifts: LiveData<List<Shift>> = ShiftsManager.shifts

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    val empty: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        this.addSource(shifts) {
            this.value = it.isEmpty() && dataLoading.value == false
        }
        this.addSource(_dataLoading) {
            this.value = !it && (shifts.value?.isEmpty() ?: true)
        }
    }

    init {
        loadShifts()
    }

    private fun loadShifts() {
        _dataLoading.value = true
        viewModelScope.launch {
            ShiftsManager.loadShifts(getApplication())
            _dataLoading.value = false
        }
    }
}