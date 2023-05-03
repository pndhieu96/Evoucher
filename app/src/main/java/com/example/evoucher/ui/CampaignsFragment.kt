package com.example.evoucher.ui

import android.view.View
import androidx.fragment.app.viewModels
import com.example.codebaseandroidapp.base.BaseFragment
import com.example.evoucher.adapter.CampaignAdapter
import com.example.evoucher.adapter.SearchAdapter
import com.example.evoucher.customView.CustomToast
import com.example.evoucher.customView.TopBar
import com.example.evoucher.databinding.FragmentCampaignsBinding
import com.example.evoucher.model.Campaign
import com.example.evoucher.utils.Utils.Companion.observer
import com.example.evoucher.viewModel.CampaignsVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CampaignsFragment :
    BaseFragment<FragmentCampaignsBinding>(FragmentCampaignsBinding::inflate) {

    private val campaignsVM: CampaignsVM by viewModels()
    lateinit var adapter: SearchAdapter

    override fun initObserve() {
        campaignsVM.canpaigns.observer(
            viewLifecycleOwner,
            onSuccess = {
                binding.pbLoading.visibility = View.GONE
                if(it.size > 0) {
                    adapter.list = it
                    adapter.notifyDataSetChanged()
                    binding.rv.visibility = View.VISIBLE
                } else {
                    binding.rv.visibility = View.GONE
                }
            }, onError = {
                showError(it.statusMessage[0])
            }, onLoading = {
                binding.pbLoading.visibility = View.VISIBLE
            }
        )
    }

    override fun initialize() {
        campaignsVM.getCampaigns("")
        adapter = SearchAdapter(arrayListOf())
        binding.rv.adapter = adapter
        adapter.callBack = object : SearchAdapter.CallBack {
            override fun onClick(item: Campaign) {

            }
        }

        binding.tb.setTitle("Danh sách chiến dịch")
        binding.tb.callBack = object : TopBar.CallBack {
            override fun onClick() {
                navController.popBackStack()
            }

        }
    }

    private fun showError(messager: String) {
        CustomToast.makeText(context, messager, CustomToast.LENGTH_LONG, CustomToast.ERROR, false).show()
    }

    companion object {
        fun newInstance() = CampaignsFragment()
    }
}