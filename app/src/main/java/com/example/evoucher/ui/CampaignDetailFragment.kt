package com.example.evoucher.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.codebaseandroidapp.base.BaseFragment
import com.example.evoucher.R
import com.example.evoucher.customView.TopBar
import com.example.evoucher.databinding.FragmentCampaignDetailBinding
import com.example.evoucher.databinding.FragmentCampaignsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CampaignDetailFragment : BaseFragment<FragmentCampaignDetailBinding>(FragmentCampaignDetailBinding::inflate) {

    override fun initObserve() {

    }

    override fun initialize() {
        binding.tb.setTitle("Chi tiết chiến dịch")
        binding.tb.callBack = object : TopBar.CallBack {
            override fun onClick() {
                navController.popBackStack()
            }

        }

        binding.btnGames.setOnClickListener {
            navController.navigate(R.id.action_campaignDetailFragment_to_gamesFragment)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = CampaignDetailFragment()
    }
}