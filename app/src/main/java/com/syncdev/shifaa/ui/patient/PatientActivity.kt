package com.syncdev.shifaa.ui.patient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.syncdev.shifaa.R
import com.syncdev.shifaa.databinding.ActivityPatientBinding
import com.syncdev.shifaa.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

@AndroidEntryPoint
class PatientActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private lateinit var binding: ActivityPatientBinding
    private val patientActivityViewModel by viewModels<PatientActivityViewModel>()
    private val TAG = "PatientActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPatientBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_patient) as NavHostFragment
        val navController = navHostFragment.navController

        //Request n notification permission if SDK = 33 or higher
        requestPermission()

        //Get the firebase user from shared preferences and assign it to UserData.user
        //So we can use it later
        val patientId = patientActivityViewModel.getPatientId()
        Log.i(TAG, "onCreate: $patientId")
        if (patientId != null) {
            patientActivityViewModel.searchPatientById(patientId)
        }

        binding.bottomNavPatient.setupWithNavController(navController)

        binding.bottomNavPatient.setOnItemSelectedListener { item ->
            // When an item in the bottom navigation view is selected, this listener is triggered

            // Navigate to the associated destination based on the selected item
            NavigationUI.onNavDestinationSelected(item, navController)

            // Return true to indicate that the selection event has been handled
            // This ensures that the selected item remains visually highlighted
            return@setOnItemSelectedListener true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this)
    }

    private fun requestPermission(){
        if (Constants.RUNNING_TIRAMISU_OR_LATER){
            if (!hasNotificationPermission()){
                requestNotificationPermission()
            }
        }
    }

    private fun hasNotificationPermission() =
        EasyPermissions.hasPermissions(
            this,
            Constants.NOTIFICATION_PERMISSION,
        )

    private fun requestNotificationPermission(){
        EasyPermissions.requestPermissions(
            this,
            "You need to grant notification permission in order to get notified when you reach your destination.",
            Constants.NOTIFICATION_REQUEST,
            Constants.NOTIFICATION_PERMISSION
        )
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Log.i("Notification", "onPermissionsGranted: Notification ")
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        when(requestCode){
            Constants.NOTIFICATION_REQUEST-> {
                if (EasyPermissions.somePermissionPermanentlyDenied(this,perms)){
                    AppSettingsDialog.Builder(this).build().show()
                }else{
                    requestNotificationPermission()
                }
            }
        }
    }
}