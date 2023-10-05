package com.syncdev.shifaa.ui.patient.home.find_doctor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.FragmentFindDoctorBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FindDoctorFragment : Fragment() {

    private lateinit var binding: FragmentFindDoctorBinding
    private val TAG = "FindDoctorFragment"
    private lateinit var doctorsListAdapter: DoctorsListAdapter
    private val findDoctorViewModel by viewModels<FindDoctorViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_find_doctor,
            container,
            false
        )

        findDoctorViewModel.getAllDoctors()

        doctorsListAdapter = DoctorsListAdapter()

        val specialty:MutableList<String> = resources.getStringArray(R.array.specialty).toMutableList()
        specialty.sort()
        specialty.add(0, "All Specialities")
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, specialty)

        binding.apply {
            dropdownMenuFilterSpecialty.setAdapter(arrayAdapter)
            dropdownMenuFilterSpecialty.setText(specialty[0], false)
            dropdownMenuFilterSpecialty.onItemClickListener =
                AdapterView.OnItemClickListener { _, _, position, _ ->
                    //Getting the selected specialty from the filter
                    val selectedSpecialty =
                        dropdownMenuFilterSpecialty.adapter.getItem(position) as String

                    //Updating the recycler view with the filtered list
                    findDoctorViewModel.filterDoctorsList(selectedSpecialty)
                    doctorsListAdapter.submitList(findDoctorViewModel.filteredList.value)
                }


            btnBackFindDoctor.setOnClickListener {
                findNavController().popBackStack()
            }
            rvDoctors.adapter = doctorsListAdapter
            rvDoctors.addItemDecoration(DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL))

            svFindDoctor.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText.isNullOrEmpty()) {
                        doctorsListAdapter.submitList(findDoctorViewModel.filteredList.value)
                    } else {
                        doctorsListAdapter.filter.filter(newText)
                    }
                    return true
                }
            })
        }

        findDoctorViewModel.doctorsList.observe(viewLifecycleOwner, Observer { doctorsList->
            doctorsListAdapter.submitList(doctorsList)
        })

        doctorsListAdapter.onItemClicked = { doctor ->
            findNavController().navigate(
                FindDoctorFragmentDirections.actionFindDoctorFragmentToBookAppointmentDetailsFragment(
                    doctorId = doctor.id.toString(),
                    time = "",
                    date = ""
                )
            )
        }


        return binding.root
    }

}