package com.example.evoucher.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.codebaseandroidapp.base.BaseFragment
import com.example.evoucher.R
import com.example.evoucher.databinding.FragmentCampaignDetailBinding
import com.example.evoucher.databinding.FragmentCampaignsBinding

class CampaignDetailFragment : BaseFragment<FragmentCampaignDetailBinding>(FragmentCampaignDetailBinding::inflate) {

    companion object {
        @JvmStatic
        fun newInstance() = CampaignDetailFragment()
    }

    override fun initObserve() {

    }

    override fun initialize() {
        binding.btnGames.setOnClickListener {
            navController.navigate(R.id.action_campaignDetailFragment_to_gamesFragment)
        }
    }
}