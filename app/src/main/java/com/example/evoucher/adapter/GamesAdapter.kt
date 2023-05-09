package com.example.evoucher.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.evoucher.R
import com.example.evoucher.databinding.ItemGameBinding
import com.example.evoucher.databinding.ItemIndustryBinding
import com.example.evoucher.databinding.ItemPartnerBinding
import com.example.evoucher.model.Industry
import com.example.evoucher.model.Partner
import com.example.evoucher.utils.ConstantUtils
import com.example.evoucher.utils.Utils
import dagger.hilt.InstallIn

class GamesAdapter(var list : List<String>) :
    RecyclerView.Adapter<GamesAdapter.ViewHolder>() {
    lateinit var context: Context
    var callBack : CallBack? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val inflate = LayoutInflater.from(context)
        return  ViewHolder(ItemGameBinding.inflate(inflate))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = list[position]
        var binding = holder.binding
        var imgResource : Int = R.drawable.ic_roulette
        if(position == 0) {
            imgResource = R.drawable.ic_lucky_number
        } else if(position == 1) {
            imgResource = R.drawable.ic_roll_dice
        }
        val options: RequestOptions = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.logo)
            .error(R.drawable.logo)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)
            .transforms(CenterCrop(), RoundedCorners(20))
        Glide.with(context)
            .load(imgResource)
            .apply(options)
            .into(binding.ivAvatar)
        binding.tvName.text = item
        binding.tvMoTa.text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry."
        binding.llContainer.setOnClickListener {
            callBack?.onClick(position)
        }
    }

    override fun getItemCount(): Int = list.count()

    interface CallBack {
        fun onClick(item: Int)
    }

    class ViewHolder(var binding: ItemGameBinding) : RecyclerView.ViewHolder(binding.root){}
}