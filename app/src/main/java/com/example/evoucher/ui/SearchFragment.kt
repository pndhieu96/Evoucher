package com.example.evoucher.ui

import android.app.Activity
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.example.codebaseandroidapp.base.BaseFragment
import com.example.evoucher.R
import com.example.evoucher.adapter.CampaignAdapter
import com.example.evoucher.adapter.SearchAdapter
import com.example.evoucher.customView.CustomToast
import com.example.evoucher.customView.TopBar
import com.example.evoucher.databinding.FragmentSearchBinding
import com.example.evoucher.model.Campaign
import com.example.evoucher.utils.Utils
import com.example.evoucher.utils.Utils.Companion.observer
import com.example.evoucher.viewModel.SearchVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val searchVM: SearchVM by viewModels()
    lateinit var adapter: SearchAdapter

    override fun initObserve() {
        searchVM.canpaigns.observer(
            viewLifecycleOwner,
            onSuccess = {
                binding.pbLoading.visibility = GONE
                if(it.size > 0) {
                    adapter.list = it
                    adapter.notifyDataSetChanged()
                    binding.tvNotFound.visibility = GONE
                    binding.rv.visibility = VISIBLE
                } else {
                    binding.tvNotFound.visibility = VISIBLE
                    binding.rv.visibility = GONE
                }
            }, onError = {
                showError(it.statusMessage[0])
                binding.tvNotFound.visibility = VISIBLE
            }, onLoading = {
                binding.pbLoading.visibility = VISIBLE
            }
        )
    }

    override fun initialize() {
        searchVM.getCampaigns("")

        binding.rv.visibility = VISIBLE
        binding.tvNotFound.visibility = GONE

        adapter = SearchAdapter(arrayListOf())
        binding.rv.adapter = adapter
        adapter.callBack = object : SearchAdapter.CallBack {
            override fun onClick(item: Campaign) {

            }
        }

        binding.ivSearch.setOnClickListener {
            searchVM.getCampaigns(binding.et.text.toString())
            Utils.hideKeyboard(activity as Activity)
        }

        binding.et.addTextChangedListener {
            if(binding.et.text.isEmpty()) {
                searchVM.getCampaigns("")
            }
        }

        binding.tb.setTitle("Tìm kiếm chiến dịch")
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
        fun newInstance() = SearchFragment()
    }
}