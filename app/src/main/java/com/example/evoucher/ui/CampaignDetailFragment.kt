package com.example.evoucher.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.codebaseandroidapp.base.BaseFragment
import com.example.evoucher.R
import com.example.evoucher.customView.TopBar
import com.example.evoucher.databinding.FragmentCampaignDetailBinding
import com.example.evoucher.databinding.FragmentCampaignsBinding
import com.example.evoucher.model.Partner
import com.example.evoucher.model.Partners
import com.example.evoucher.utils.ConstantUtils
import com.example.evoucher.utils.SharedPreferencesImp
import com.example.evoucher.utils.SharedPreferencesImp.Companion.PARTNERS_INFO
import com.example.evoucher.utils.Utils
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.text.ParseException
import java.text.SimpleDateFormat
import javax.inject.Inject

@AndroidEntryPoint
class CampaignDetailFragment : BaseFragment<FragmentCampaignDetailBinding>(FragmentCampaignDetailBinding::inflate) {
    @Inject
    lateinit var sPregerences: SharedPreferencesImp
    private val args: CampaignDetailFragmentArgs by navArgs()
    private var parners : Partners = Partners()
    private var partner : Partner = Partner()

    override fun initObserve() {

    }

    override fun initialize() {
        binding.tb.setTitle("Chi tiết chiến dịch")
        binding.tb.callBack = object : TopBar.CallBack {
            override fun onClick() {
                navController.popBackStack()
            }

        }

        val item = args.campaignArg
        item?.let {
            Glide.with(requireContext())
                .load(Utils.getImageUrl(item.imgUrl, ConstantUtils.TYPE_IMAGE_CAMPAIGN))
                .into(binding.ivBg)
            binding.tvName.text = item.ten

            try {
                parners = Gson().fromJson(sPregerences.getString(PARTNERS_INFO), Partners::class.java)
                parners.result = parners.result?.filter {
                    it.id == item.chiNhanh?.doiTacId
                }
                partner = parners.result?.get(0)!!
            } catch (_: Exception){}

            try {
                val startDate = Utils.stringToDate(item.ngayBatDau.toString())
                val endDate = Utils.stringToDate(item.ngayKetThuc.toString())
                val dateFormat = SimpleDateFormat("dd/MM/yy")
                binding.tvDate.text = dateFormat.format(startDate) + " - " + dateFormat.format(endDate)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            if(parners.result != null && parners.result!!.isNotEmpty()) {
                var parner = parners.result!![0]
                val options: RequestOptions = RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.logo)
                    .error(R.drawable.logo)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .transforms(CenterCrop(), RoundedCorners(20))
                Glide.with(requireContext())
                    .load(Utils.getImageUrl(parner.imgUrl, ConstantUtils.TYPE_IMAGE_PARTNER))
                    .apply(options)
                    .into(binding.ivAvatar)

                binding.tvPartner.text = parner.tenDoiTac
            }

            binding.tvAddress.text = "Địa chỉ: ${item.chiNhanh?.diaChi}"
            binding.tvDes.text = item.mota
        }

        binding.btnGames.setOnClickListener {
            val action = CampaignDetailFragmentDirections.actionCampaignDetailFragmentToGamesFragment(
                item,
                partner
            )
            navController.navigate(action)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = CampaignDetailFragment()
    }
}