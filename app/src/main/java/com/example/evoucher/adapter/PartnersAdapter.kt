package com.example.evoucher.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.evoucher.databinding.ItemIndustryBinding
import com.example.evoucher.databinding.ItemPartnerBinding
import com.example.evoucher.model.Industry
import com.example.evoucher.model.Partner
import com.example.evoucher.utils.ConstantUtils
import com.example.evoucher.utils.Utils
import dagger.hilt.InstallIn

class PartnersAdapter(var list : List<Partner>) :
    RecyclerView.Adapter<PartnersAdapter.ViewHolder>() {
    lateinit var context: Context
    var callBack : CallBack? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val inflate = LayoutInflater.from(context)
        return  ViewHolder(ItemPartnerBinding.inflate(inflate))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = list[position]
        var binding = holder.binding
        Glide.with(context)
            .load(Utils.getImageUrl(item.imgUrl, ConstantUtils.TYPE_IMAGE_PARTNER))
            .into(binding.ivAvatar)
        binding.tvName.text = item.tenDoiTac
        binding.tvMoTa.text = item.mota
    }

    override fun getItemCount(): Int = list.count()

    interface CallBack {
        fun onClick(item: Partner)
    }

    class ViewHolder(var binding: ItemPartnerBinding) : RecyclerView.ViewHolder(binding.root){}
}