package com.example.evoucher.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.evoucher.databinding.ItemCampaignBinding
import com.example.evoucher.databinding.ItemIndustryBinding
import com.example.evoucher.model.Campaign
import com.example.evoucher.model.Industry
import com.example.evoucher.utils.ConstantUtils.Companion.TYPE_IMAGE_CAMPAIGN
import com.example.evoucher.utils.Utils
import dagger.hilt.InstallIn

class CampaignAdapter(var list : List<Campaign>) :
    RecyclerView.Adapter<CampaignAdapter.ViewHolder>() {
    lateinit var context: Context
    var callBack : CallBack? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val inflate = LayoutInflater.from(context)
        return ViewHolder(ItemCampaignBinding.inflate(inflate))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = list[position]
        var binding = holder.binding
        Glide.with(context)
            .load(Utils.getImageUrl(item.imgUrl, TYPE_IMAGE_CAMPAIGN))
            .into(binding.ivBg)
        binding.tvTitle.text = item.ten
        binding.tvDes.text = item.mota
        binding.tvDate.text = "25/5"
        binding.llContainer.setOnClickListener {
            callBack?.onClick(item)
        }
    }

    override fun getItemCount(): Int = list.count()

    interface CallBack {
        fun onClick(item: Campaign)
    }

    class ViewHolder(var binding: ItemCampaignBinding) : RecyclerView.ViewHolder(binding.root)
}