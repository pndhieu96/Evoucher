package com.example.evoucher.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.evoucher.R
import com.example.evoucher.databinding.ItemCampaignBinding
import com.example.evoucher.databinding.ItemCouponBinding
import com.example.evoucher.databinding.ItemSearchBinding
import com.example.evoucher.model.Campaign
import com.example.evoucher.model.CouponResult
import com.example.evoucher.ui.GamesFragmentArgs
import com.example.evoucher.utils.ConstantUtils.Companion.TYPE_IMAGE_CAMPAIGN
import com.example.evoucher.utils.Utils
import com.example.evoucher.viewModel.GamesVM
import java.text.ParseException
import java.text.SimpleDateFormat


class CouponsAdapter(var list : List<CouponResult>) :
    RecyclerView.Adapter<CouponsAdapter.ViewHolder>() {
    lateinit var context: Context
    var callBack : CallBack? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val inflate = LayoutInflater.from(context)
        return ViewHolder(ItemCouponBinding.inflate(inflate, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        val binding = holder.binding

        binding.tvIndustry.text = item.chiNhanh_Ten
        val percent = item.loaiCoupon_Ten.substring(item.loaiCoupon_Ten.lastIndexOf(" ") + 1)
        binding.tvPercent.text = percent
        binding.llContainer.setOnClickListener {
            callBack?.onClick(item)
        }
    }

    override fun getItemCount(): Int = list.count()

    interface CallBack {
        fun onClick(item: CouponResult)
    }

    class ViewHolder(var binding: ItemCouponBinding) : RecyclerView.ViewHolder(binding.root)
}