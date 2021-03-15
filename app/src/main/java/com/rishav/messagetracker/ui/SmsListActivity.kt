package com.rishav.messagetracker.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.rishav.messagetracker.R
import com.rishav.messagetracker.adapter.SmsListAdapter
import com.rishav.messagetracker.databinding.ActivitySmsListBinding
import com.rishav.messagetracker.viewmodel.SmsListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

const val TAG= "SmsListActivity"
@AndroidEntryPoint
class SmsListActivity : AppCompatActivity() {
    @Inject
    lateinit var adapter : SmsListAdapter
    lateinit var binding: ActivitySmsListBinding
    private val viewModel : SmsListViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sms_list)
        init()
    }
    private fun init(){
        registerForPermissionCallBack()
        binding.rvSms.adapter = adapter
        viewModel.smsList.observe(this, Observer {
            viewModel.getTimedSmsList()
        })
        viewModel.timedSmsList.observe(this, Observer { smsList ->
            smsList?.let { adapter.updateData(it) }
        })
    }
    private fun registerForPermissionCallBack(){
        val requestPermissionLauncher = registerForActivityResult(RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Log.d(TAG, "registerForPermissionCallBack: permission is granted")
            } else {
                Toast.makeText(this, "SMS receive permission denied", Toast.LENGTH_SHORT).show()
            }
        }

        when {
            ContextCompat.checkSelfPermission(
                    this.applicationContext,
                    Manifest.permission.RECEIVE_SMS
            ) == PackageManager.PERMISSION_GRANTED -> {
                Log.d(TAG, "registerForPermissionCallBack: permission granted")
            }
            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS) -> {
                // In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected. In this UI,
                // include a "cancel" or "no thanks" button that allows the user to
                // continue using your app without granting the permission.
                Log.d(TAG, "registerForPermissionCallBack: request permission rationale")
            }
            else -> {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                requestPermissionLauncher.launch(
                        Manifest.permission.RECEIVE_SMS)
            }
        }
    }

}