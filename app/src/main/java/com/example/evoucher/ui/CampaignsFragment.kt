package com.example.evoucher.ui

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.codebaseandroidapp.base.BaseFragment
import com.example.evoucher.R
import com.example.evoucher.adapter.CampaignAdapter
import com.example.evoucher.adapter.SearchAdapter
import com.example.evoucher.customView.CustomToast
import com.example.evoucher.customView.TopBar
import com.example.evoucher.databinding.FragmentCampaignsBinding
import com.example.evoucher.model.Campaign
import com.example.evoucher.model.Industry
import com.example.evoucher.model.Partner
import com.example.evoucher.utils.Utils.Companion.observer
import com.example.evoucher.viewModel.CampaignsVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CampaignsFragment :
    BaseFragment<FragmentCampaignsBinding>(FragmentCampaignsBinding::inflate) {

    private val args : CampaignsFragmentArgs by navArgs()
    private val campaignsVM: CampaignsVM by viewModels()
    private var type: Int = 0
    private var industry: Industry? = null
    private var partner: Partner? = null
    private var campaigns : List<Campaign>? = null

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
                    binding.llEmpty.visibility = View.GONE
                } else {
                    binding.rv.visibility = View.GONE
                    binding.llEmpty.visibility = View.VISIBLE
                }
            }, onError = {
                showError(it.statusMessage[0])
            }, onLoading = {
                binding.pbLoading.visibility = View.VISIBLE
            }
        )
    }

    override fun initialize() {
        type = args.typeArg
        campaigns = args.campaignsArg?.toList()
        adapter = SearchAdapter(arrayListOf())
        industry = args.industryArg
        partner = args.partnerArg
        binding.llEmpty.visibility = View.VISIBLE
        binding.rv.visibility = View.GONE

        if (type == BY_INDUSTRY) {
            industry?.let {
                binding.tvBy.text = "Theo ngành hàng: ${it.ten}"
            }
        } else if (type == BY_PARTNER) {
            partner?.let {
                binding.tvBy.text = "Theo đối tác: ${it.tenDoiTac}"
            }
        }

        campaigns?.let {
            campaignsVM.getCampaigns(
                type,
                partner?.id ?: 0,
                industry?.id ?: 0,
                campaigns!!
            )
        }

        binding.rv.adapter = adapter
        adapter.callBack = object : SearchAdapter.CallBack {
            override fun onClick(item: Campaign) {
                val action = CampaignsFragmentDirections.actionCampaignsFragmentToCampaignDetailFragment(item)
                navController.navigate(action)
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
        const val BY_PARTNER = 0
        const val BY_INDUSTRY = 1

        fun newInstance() = CampaignsFragment()
    }
}