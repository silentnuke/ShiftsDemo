package com.silentnuke.shifts.addshift

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.silentnuke.shifts.Event
import com.silentnuke.shifts.R
import com.silentnuke.shifts.data.Shift
import com.silentnuke.shifts.data.ShiftsManager
import java.text.SimpleDateFormat
import java.util.*

class AddShiftViewModel : ViewModel() {
    val employees: LiveData<List<String>> = ShiftsManager.employess
    val roles: LiveData<List<String>> = ShiftsManager.roles
    val colors: LiveData<List<String>> = ShiftsManager.colors
    val selectedStartDate: MutableLiveData<Date> = MutableLiveData()
    val selectedEndDate: MutableLiveData<Date> = MutableLiveData()
    val selectedEmployee: MutableLiveData<String> = MutableLiveData()
    val selectedRole: MutableLiveData<String> = MutableLiveData()
    val selectedColor: MutableLiveData<String> = MutableLiveData()

    private val _snackbarMessage = MutableLiveData<Event<Int>>()
    val snackbarMessage: LiveData<Event<Int>> = _snackbarMessage

    fun saveShift(): Boolean {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z", Locale.US)
        val startDate = selectedStartDate.value
        if (startDate == null) {
            _snackbarMessage.value = Event(R.string.add_shift_error_start_date)
            return false
        }
        val endDate = selectedEndDate.value
        if (endDate == null) {
            _snackbarMessage.value = Event(R.string.add_shift_error_end_date)
            return false
        }
        if (startDate > endDate) {
            _snackbarMessage.value = Event(R.string.add_shift_error_wrong_end_date)
            return false
        }
        val employee = selectedEmployee.value
        if (employee == null) {
            _snackbarMessage.value = Event(R.string.add_shift_error_employee_not_selected)
            return false
        }
        val role = selectedRole.value
        if (role == null) {
            _snackbarMessage.value = Event(R.string.add_shift_error_role_not_selected)
            return false
        }
        val color = selectedColor.value
        if (color == null) {
            _snackbarMessage.value = Event(R.string.add_shift_error_color_not_selected)
            return false
        }
        ShiftsManager.addShift(
            Shift(
                employee,
                role,
                dateFormat.format(startDate),
                dateFormat.format(endDate),
                color
            )
        )
        return true
    }
}