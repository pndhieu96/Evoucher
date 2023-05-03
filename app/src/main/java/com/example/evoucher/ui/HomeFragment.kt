package com.example.evoucher.ui

import android.view.View
import androidx.fragment.app.viewModels
import com.example.codebaseandroidapp.base.BaseFragment
import com.example.evoucher.R
import com.example.evoucher.adapter.CampaignAdapter
import com.example.evoucher.adapter.IndustriesAdapter
import com.example.evoucher.adapter.PartnersAdapter
import com.example.evoucher.customView.CustomToast
import com.example.evoucher.databinding.FragmentHomeBinding
import com.example.evoucher.model.Campaign
import com.example.evoucher.model.Industry
import com.example.evoucher.model.Partner
import com.example.evoucher.utils.SharedPreferencesImp
import com.example.evoucher.utils.Utils.Companion.observer
import com.example.evoucher.viewModel.HomeVM
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeVM : HomeVM by viewModels()
    private var refreshCount = 0;
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
                showError(it.statusMessage[0])
            }, onLoading = {
            }
        )
        homeVM.partners.observer(
            viewLifecycleOwner,
            onSuccess = {
                partnersAdapter.list = it
                partnersAdapter.notifyDataSetChanged()
            }, onError = {
                showError(it.statusMessage[0])
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
                showError(it.statusMessage[0])
            }, onLoading = {
                binding.pbLoading.visibility = View.VISIBLE
            }
        )
    }

    override fun initialize() {
        homeVM.getCampaigns()
        homeVM.getIndustries()
        homeVM.getPartners()

        binding.srLayout.setOnRefreshListener {
            refreshCount++;
            if(refreshCount == 2) {
                homeVM.getCampaigns()
                homeVM.getIndustries()
                homeVM.getPartners()
                refreshCount = 0;
            }
            binding.srLayout.setRefreshing(false);
        }

        industriesAdapter = IndustriesAdapter(arrayListOf())
        binding.rvIndustries.adapter = industriesAdapter
        industriesAdapter.callBack = object : IndustriesAdapter.CallBack {
            override fun onClick(item: Industry) {
                navController.navigate(R.id.action_homeFragment_to_campaignsFragment)
            }

        }

        campaignAdapter = CampaignAdapter(arrayListOf())
        binding.rvCampaign.adapter = campaignAdapter
        campaignAdapter.callBack = object : CampaignAdapter.CallBack {
            override fun onClick(item: Campaign) {
                navController.navigate(R.id.action_homeFragment_to_campaignDetailFragment)
            }

        }

        partnersAdapter = PartnersAdapter(arrayListOf())
        binding.rvPartner.adapter = partnersAdapter
        partnersAdapter.callBack = object :PartnersAdapter.CallBack {
            override fun onClick(item: Partner) {
                navController.navigate(R.id.action_homeFragment_to_campaignsFragment)
            }

        }

        binding.btnMap.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_mapFragment)
        }

        binding.btnAccount.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_accountFragment)
        }

        binding.llSearch.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    private fun showError(messager: String) {
        CustomToast.makeText(context, messager, CustomToast.LENGTH_LONG, CustomToast.ERROR, false).show()
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

}