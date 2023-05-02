package com.example.evoucher.ui

import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.example.codebaseandroidapp.base.BaseFragment
import com.example.evoucher.R
import com.example.evoucher.adapter.CampaignAdapter
import com.example.evoucher.adapter.IndustriesAdapter
import com.example.evoucher.adapter.PartnersAdapter
import com.example.evoucher.customView.CustomToast
import com.example.evoucher.databinding.FragmentHomeBinding
import com.example.evoucher.utils.SharedPreferencesImp
import com.example.evoucher.utils.Utils.Companion.observer
import com.example.evoucher.utils.Utils.Companion.reload
import com.example.evoucher.viewModel.HomeVM
import com.example.evoucher.viewModel.LoginVM
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeVM : HomeVM by viewModels()
    @Inject
    lateinit var sPregerences: SharedPreferencesImp
    lateinit var industriesAdapter: IndustriesAdapter
    lateinit var campaignAdapter: CampaignAdapter
    lateinit var partnersAdapter: PartnersAdapter

    override fun initObserve() {
        homeVM.industries.observer(
            viewLifecycleOwner,
            onSuccess = {
                industriesAdapter.list = it
                industriesAdapter.notifyDataSetChanged()
            }, onError = {

            }, onLoading = {
            }
        )
        homeVM.partners.observer(
            viewLifecycleOwner,
            onSuccess = {
                partnersAdapter.list = it
                partnersAdapter.notifyDataSetChanged()
            }, onError = {

            }, onLoading = {
            }
        )
        homeVM.canpaigns.observer(
            viewLifecycleOwner,
            onSuccess = {
                binding.pbLoading.visibility = View.GONE
                campaignAdapter.list = it
                campaignAdapter.notifyDataSetChanged()
            }, onError = {

            }, onLoading = {
                binding.pbLoading.visibility = View.VISIBLE
            }
        )
    }

    override fun initialize() {
        homeVM.getCampaigns()
        homeVM.getIndustries()
        homeVM.getPartners()

        industriesAdapter = IndustriesAdapter(arrayListOf())
        binding.rvIndustries.adapter = industriesAdapter

        campaignAdapter = CampaignAdapter(arrayListOf())
        binding.rvCampaign.adapter = campaignAdapter

        partnersAdapter = PartnersAdapter(arrayListOf())
        binding.rvPartner.adapter = partnersAdapter
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

}