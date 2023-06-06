package com.example.evoucher.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
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
import com.example.evoucher.databinding.FragmentCouponDetailBinding
import com.example.evoucher.model.ChiNhanh
import com.example.evoucher.model.Partner
import com.example.evoucher.model.Partners
import com.example.evoucher.utils.ConstantUtils
import com.example.evoucher.utils.SharedPreferencesImp
import com.example.evoucher.utils.Utils
import com.example.evoucher.utils.Utils.Companion.observer
import com.example.evoucher.utils.Utils.Companion.plus
import com.example.evoucher.viewModel.CouponDetailVM
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.text.ParseException
import java.text.SimpleDateFormat
import javax.inject.Inject

@AndroidEntryPoint
class CouponDetailFragment : BaseFragment<FragmentCouponDetailBinding>(FragmentCouponDetailBinding::inflate) {
    @Inject
    lateinit var sPregerences: SharedPreferencesImp
    private val vm : CouponDetailVM by viewModels()
    private val args: CouponDetailFragmentArgs by navArgs()

    override fun initObserve() {
        vm.branch.observer(
            viewLifecycleOwner,
            onSuccess = {
                val branch = it[0]
                binding.pbLoading.visibility = View.GONE
                Glide.with(requireContext())
                    .load(Utils.getImageUrl(branch.imgUrl ?: "", ConstantUtils.TYPE_IMAGE_BRANCH))
                    .into(binding.ivBg)

                binding.tvAddress.text = "Địa chỉ: ${branch.diaChi}"
                binding.tvDes.text = branch.mota
            },
            onLoading = {
                binding.pbLoading.visibility = View.VISIBLE
            },
            onError = {
                binding.pbLoading.visibility = View.GONE
            }
        )
    }

    override fun initialize() {
        binding.tb.setTitle("Chi tiết mã giảm giá")
        binding.tb.callBack = object : TopBar.CallBack {
            override fun onClick() {
                navController.popBackStack()
            }
        }
        val item = args.couponResultArg
        item?.let {
            binding.tvIndustry.text = item.chiNhanh_Ten
            val percent = item.loaiCoupon_Ten.substring(item.loaiCoupon_Ten.lastIndexOf(" ") + 1)
            binding.tvPercent.text = percent
            try {
                val date = Utils.stringToDate(it.coupon.createdDate).plus(it.coupon.thoiHan)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy")
                binding.tvDate.text = "Ngày hết hạn: ${dateFormat.format(date)}"
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            vm.getBranch(item.chiNhanhId)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = CouponDetailFragment()
    }
}