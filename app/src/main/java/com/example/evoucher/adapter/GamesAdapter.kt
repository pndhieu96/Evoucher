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
import com.example.evoucher.model.Game
import com.example.evoucher.model.Industry
import com.example.evoucher.model.Partner
import com.example.evoucher.utils.ConstantUtils
import com.example.evoucher.utils.Utils
import dagger.hilt.InstallIn

class GamesAdapter(var list : List<Game>) :
    RecyclerView.Adapter<GamesAdapter.ViewHolder>() {
    lateinit var context: Context
    var callBack : CallBack? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val inflate = LayoutInflater.from(context)
        return  ViewHolder(ItemGameBinding.inflate(inflate, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        val binding = holder.binding
        val options: RequestOptions = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.logo)
            .error(R.drawable.logo)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)
            .transforms(CenterCrop(), RoundedCorners(20))
        Glide.with(context)
            .load(Utils.getImageUrl(item.imgUrl, ConstantUtils.TYPE_IMAGE_GAMES))
            .apply(options)
            .into(binding.ivAvatar)
        binding.tvName.text = item.ten
        binding.tvMoTa.text = item.luatChoi
        binding.llContainer.setOnClickListener {
            callBack?.onClick(item)
        }
    }

    override fun getItemCount(): Int = list.count()

    interface CallBack {
        fun onClick(item: Game)
    }

    class ViewHolder(var binding: ItemGameBinding) : RecyclerView.ViewHolder(binding.root){}
}