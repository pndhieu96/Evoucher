package com.example.evoucher.model

import com.google.gson.annotations.SerializedName

data class CampaignDetail (
    @SerializedName("chienDich"   ) var chienDich   : Campaign?             = Campaign(),
    @SerializedName("troChoiId"   ) var troChoiId   : ArrayList<String>              = arrayListOf(),
    @SerializedName("loaiCouponX" ) var loaiCouponX : ArrayList<LoaiCouponX> = arrayListOf()
)

data class LoaiCouponX (

    @SerializedName("loaiCouponId"   ) var loaiCouponId   : Int?    = null,
    @SerializedName("loaiCouponName" ) var loaiCouponName : String? = null,
    @SerializedName("soLuong"        ) var soLuong        : Int?    = null,
    @SerializedName("conLai"         ) var conLai         : Int?    = null

)