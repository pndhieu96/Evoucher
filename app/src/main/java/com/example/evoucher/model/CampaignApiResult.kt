package com.example.evoucher.model

import com.google.gson.annotations.SerializedName


data class CampaignsApiResult (
    @SerializedName("status_code"    ) var statusCode    : Int?     = null,
    @SerializedName("errorMessages" ) var statusMessage : ArrayList<String> = arrayListOf(),
    @SerializedName("isSuccess"        ) var success       : Boolean = false,
    @SerializedName("result"        ) var result        : List<Campaign>           = arrayListOf(),
)

data class Campaign (
    @SerializedName("id"          ) var id          : Int?    = null,
    @SerializedName("chiNhanhId"  ) var chiNhanhId  : Int?    = null,
    @SerializedName("chiNhanh"    ) var chiNhanh    : String? = null,
    @SerializedName("ten"         ) var ten         : String = "",
    @SerializedName("ngayBatDau"  ) var ngayBatDau  : String? = null,
    @SerializedName("ngayKetThuc" ) var ngayKetThuc : String? = null,
    @SerializedName("trangThaiId" ) var trangThaiId : Int?    = null,
    @SerializedName("trangThai"   ) var trangThai   : String? = null,
    @SerializedName("imgUrl"      ) var imgUrl      : String = "",
    @SerializedName("mota"        ) var mota        : String? = null,
    @SerializedName("createdDate" ) var createdDate : String? = null

)