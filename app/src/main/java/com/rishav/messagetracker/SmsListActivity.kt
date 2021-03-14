package com.rishav.messagetracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.rishav.messagetracker.adapter.SmsListAdapter
import com.rishav.messagetracker.databinding.ActivitySmsListBinding
import com.rishav.messagetracker.viewmodel.SmsListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SmsListActivity : AppCompatActivity() {
    @Inject
    lateinit var adapter : SmsListAdapter
    lateinit var binding: ActivitySmsListBinding
    private val viewModel : SmsListViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sms_list)
        init()
    }
    private fun init(){
        binding.rvSms.adapter = adapter
        viewModel.smsList.observe(this, Observer { smsList ->
            smsList?.let { adapter.updateData(it) }
        })
    }
}