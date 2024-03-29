package org.hf.basemvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.SavedStateViewModelFactory
import hf.org.basemvvm.R
import hf.org.basemvvm.databinding.ActivityMainBinding
import org.mod.common.base.BaseAppBVMActivity

class MainActivity : BaseAppBVMActivity<ActivityMainBinding, MainViewModel>() {

    override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java


    override fun getLayoutId(): Int  = R.layout.activity_main

    override fun initialize(savedInstanceState: Bundle?) {
//        val s = SavedStateViewModelFactory
        binding.viewModel = viewModel
        Log.e("test","MainActivity initialize")

    }

}