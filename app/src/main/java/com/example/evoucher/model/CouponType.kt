package com.example.evoucher.model

import com.google.gson.annotations.SerializedName

data class CouponType (
    @SerializedName("id"       ) var id       : Int?    = null,
    @SerializedName("ten"      ) var ten      : String? = null,
    @SerializedName("phanTram" ) var phanTram : Int?    = null,
    @SerializedName("trucTiep" ) var trucTiep : Int?    = null,
    @SerializedName("mota"     ) var mota     : String? = null
)