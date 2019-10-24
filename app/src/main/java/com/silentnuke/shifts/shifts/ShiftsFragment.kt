package com.silentnuke.shifts.shifts

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.silentnuke.shifts.R
import com.silentnuke.shifts.data.Shift
import kotlinx.android.synthetic.main.fragment_shifts.*

class ShiftsFragment : Fragment() {
    private lateinit var viewModel: ShiftsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_shifts, parent, false)
        viewModel = ViewModelProviders.of(this)[ShiftsViewModel::class.java]
        setHasOptionsMenu(true)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupShiftsAdapter()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.shifts_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add_shift -> {
                findNavController().navigate(R.id.addShiftFragment)
                return true
            }
            else -> return false
        }
    }

    private fun setupShiftsAdapter() {
        val listAdapter = ShiftsAdapter()
        viewModel.shifts.observe(this, Observer<List<Shift>> { shifts ->
            listAdapter.submitList(shifts)
        })
        viewModel.dataLoading.observe(this, Observer<Boolean> { isLoading ->
            progress.visibility = if (isLoading) View.VISIBLE else View.GONE
        })
        viewModel.empty.observe(this, Observer<Boolean> { isEmpty ->
            empty.visibility = if (isEmpty) View.VISIBLE else View.GONE
        })
        shiftsRV.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        shiftsRV.adapter = listAdapter
    }
}