package com.example.evoucher.ui

import android.util.Log
import androidx.activity.OnBackPressedCallback
import com.example.codebaseandroidapp.base.BaseFragment
import com.example.evoucher.R
import com.example.evoucher.databinding.FragmentHomeBinding
import com.example.evoucher.utils.SharedPreferencesImp
import com.example.evoucher.utils.Utils.Companion.reload
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    @Inject
    lateinit var sPregerences: SharedPreferencesImp

    override fun initObserve() {

    }

    override fun initialize() {
        binding.btnDX.setOnClickListener {
            sPregerences.putString(SharedPreferencesImp.TOKEN, "")
            context?.let { it -> reload(it) }
        }
        binding.btnChiTiet.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_campaignDetailFragment)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

}