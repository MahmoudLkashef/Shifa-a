package com.syncdev.shifaa.ui.auth.login.patient

import androidx.lifecycle.ViewModel
import com.syncdev.domain.usecase.auth.patient.LoginPatientUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PatientSignInViewModel
@Inject
constructor(
    private val loginPatientUseCase: LoginPatientUseCase
) : ViewModel() {


}