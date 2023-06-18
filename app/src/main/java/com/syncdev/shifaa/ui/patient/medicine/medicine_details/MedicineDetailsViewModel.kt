package com.syncdev.shifaa.ui.patient.medicine.medicine_details

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.syncdev.domain.model.ScheduledMedication
import com.syncdev.domain.usecase.patient.GetScheduledMedicationByIdUseCase
import com.syncdev.shifaa.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicineDetailsViewModel
@Inject
constructor(
    private val getScheduledMedicationByIdUseCase: GetScheduledMedicationByIdUseCase
): ViewModel(){

    private var _medicine = MutableLiveData<ScheduledMedication>()
    val medicine: LiveData<ScheduledMedication> get() = _medicine

    fun getMedicineById(medicineId: Int){
        viewModelScope.launch {
            _medicine.postValue(
                getScheduledMedicationByIdUseCase.invoke(medicineId)
            )
        }
    }

    fun createChip(context: Context, time: String): Chip {
        val chip = Chip(context)
        chip.text = time
        chip.isClickable = false
        chip.isCheckable = false

        // Create a custom ChipDrawable with your desired style
        val chipDrawable = ChipDrawable.createFromAttributes(
            context,
            null,
            0,
            R.style.customChipStyle
        )

        // Set the custom ChipDrawable as the background for the chip
        chip.setChipDrawable(chipDrawable)

        chip.setOnCheckedChangeListener { _, isChecked ->
            // Set the text color of the chip to white when selected
            chip.setTextColor(
                if (!isChecked) ContextCompat.getColor(context, android.R.color.white)
                else ContextCompat.getColor(context, R.color.chip_text_color)
            )

        }

        return chip
    }
}