package com.silentnuke.shifts.addshift

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.silentnuke.shifts.R
import kotlinx.android.synthetic.main.fragment_add_shift.*
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*


class AddShiftFragment : Fragment() {
    private lateinit var viewModel: AddShiftViewModel

    override fun onCreateView(
        inflater: LayoutInflater, parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_shift, parent, false);
        viewModel = ViewModelProviders.of(this)[AddShiftViewModel::class.java]
        setHasOptionsMenu(true)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupUi()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_shift_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_save_shift -> {
                if (viewModel.saveShift())
                    findNavController().navigateUp()
                return true
            }
            else -> return false
        }
    }

    private fun setupUi() {
        setupDateControl(startDateValue, viewModel.selectedStartDate, R.string.add_shift_start_date)
        setupDateControl(endDateValue, viewModel.selectedEndDate, R.string.add_shift_end_date)
        setupListControl(employeeValue, viewModel.employees, viewModel.selectedEmployee)
        setupListControl(roleValue, viewModel.roles, viewModel.selectedRole)
        setupListControl(colorValue, viewModel.colors, viewModel.selectedColor)
        viewModel.snackbarMessage.observe(this, Observer { event ->
            view?.let { view ->
                event.getContentIfNotHandled()?.let {
                    Snackbar.make(view, getString(it), Snackbar.LENGTH_SHORT).show()
                }
            }

        })
    }

    private fun setupListControl(
        view: RecyclerView,
        list: LiveData<List<String>>,
        selected: MutableLiveData<String>
    ) {
        val adapter = ItemsAdapter(selected)
        list.observe(this, Observer<List<String>> { data ->
            adapter.submitList(data)
        })
        view.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        view.adapter = adapter
    }

    private fun setupDateControl(view: TextView, liveData: MutableLiveData<Date>, title: Int) {
        liveData.observe(this, Observer<Date> {
            view.text = SimpleDateFormat("EEE, MMM d h:mm a", Locale.US).format(it)
        })
        view.setOnClickListener {
            SingleDateAndTimePickerDialog.Builder(context)
                .defaultDate(liveData.value)
                .displayDays(true)
                .displayHours(true)
                .displayMinutes(true)
                .displayDaysOfMonth(false)
                .displayMonth(false)
                .displayYears(false)
                .mainColor(resources.getColor(R.color.colorPrimary))
                .title(getString(title))
                .listener {
                    liveData.value = it
                }.display()
        }
    }
}