package com.example.evoucher.model

import com.google.gson.annotations.SerializedName

data class GameResult (

    @SerializedName("randomNumber"    ) var randomNumber    : Int?     = null,
    @SerializedName("dienGiai"        ) var dienGiai        : String?  = null,
    @SerializedName("nhanDuocVoucher" ) var nhanDuocVoucher : Boolean = false,
    @SerializedName("requestId"       ) var requestId       : String?  = null,
    @SerializedName("transectionLog"  ) var transectionLog  : String?  = null,
    @SerializedName("voucher"         ) var voucher         : Voucher? = Voucher(),
    @SerializedName("xxx"             ) var xxx             : Boolean? = null
)

data class Voucher (

    @SerializedName("loaiCouponId" ) var loaiCouponId : Int? = null,
    @SerializedName("trochoiId"    ) var trochoiId    : Int? = null,
    @SerializedName("chiendichId"  ) var chiendichId  : Int? = null,
    @SerializedName("userId"       ) var userId       : Int? = null

)