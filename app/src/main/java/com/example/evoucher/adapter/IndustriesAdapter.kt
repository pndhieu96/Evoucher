package com.example.evoucher.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.evoucher.databinding.ItemIndustryBinding
import com.example.evoucher.model.Industry
import dagger.hilt.InstallIn

class IndustriesAdapter(var list : List<Industry>) :
    RecyclerView.Adapter<IndustriesAdapter.ViewHolder>() {

    var callBack : CallBack? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        return  ViewHolder(ItemIndustryBinding.inflate(inflate))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = list[position]
        var binding = holder.binding
        binding.tvName.text = item.ten
        binding.tvName.setOnClickListener {
            callBack?.onClick(list[position])
        }
    }

    override fun getItemCount(): Int = list.count()

    interface CallBack {
        fun onClick(item: Industry)
    }

    class ViewHolder(var binding: ItemIndustryBinding) : RecyclerView.ViewHolder(binding.root)
}