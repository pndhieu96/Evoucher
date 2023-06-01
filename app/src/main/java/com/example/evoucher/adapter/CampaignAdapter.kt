package com.example.evoucher.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.evoucher.R
import com.example.evoucher.databinding.ItemCampaignBinding
import com.example.evoucher.model.Campaign
import com.example.evoucher.utils.ConstantUtils.Companion.TYPE_IMAGE_CAMPAIGN
import com.example.evoucher.utils.Utils
import java.text.ParseException
import java.text.SimpleDateFormat


class CampaignAdapter(var list : List<Campaign>) :
    RecyclerView.Adapter<CampaignAdapter.ViewHolder>() {
    lateinit var context: Context
    var callBack : CallBack? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val inflate = LayoutInflater.from(context)
        return ViewHolder(ItemCampaignBinding.inflate(inflate, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = list[position]
        var binding = holder.binding
        val options: RequestOptions = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.img_campaign_error)
            .error(R.drawable.img_campaign_error)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)
            .transforms(CenterCrop(), RoundedCorners(20))
        Glide.with(context)
            .load(Utils.getImageUrl(item.imgUrl, TYPE_IMAGE_CAMPAIGN))
            .apply(options)
            .into(binding.ivBg)
        binding.tvTitle.text = item.ten
        binding.tvDes.text = item.mota
        try {
            val date = Utils.stringToDate(item.ngayBatDau.toString())
            val dateFormat = SimpleDateFormat("dd/MM/yy")
            binding.tvDate.text =dateFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
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