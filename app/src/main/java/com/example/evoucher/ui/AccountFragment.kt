package com.example.evoucher.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.codebaseandroidapp.base.BaseFragment
import com.example.evoucher.R
import com.example.evoucher.customView.TopBar
import com.example.evoucher.databinding.FragmentAccountBinding
import com.example.evoucher.databinding.FragmentCampaignsBinding
import com.example.evoucher.utils.SharedPreferencesImp
import com.example.evoucher.utils.SharedPreferencesImp.Companion.TOKEN
import com.example.evoucher.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AccountFragment : BaseFragment<FragmentAccountBinding>(FragmentAccountBinding::inflate) {

    @Inject
    lateinit var sharedPreferencesImp : SharedPreferencesImp

    override fun initObserve() {

    }

    override fun initialize() {
        binding.btnLogout.setOnClickListener {
            sharedPreferencesImp.putString(TOKEN, "")
            context?.let {
                Utils.reload(it)
            }
        }

        binding.tb.setTitle("Tài khoản")
        binding.tb.callBack = object : TopBar.CallBack{
            override fun onClick() {
                navController.popBackStack()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = AccountFragment()
    }
}