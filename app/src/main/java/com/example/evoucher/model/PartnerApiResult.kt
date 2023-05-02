package com.example.evoucher.model

import com.google.gson.annotations.SerializedName

data class PartnersApiResult (
    @SerializedName("status_code"    ) var statusCode    : Int?     = null,
    @SerializedName("errorMessages" ) var statusMessage : ArrayList<String> = arrayListOf(),
    @SerializedName("isSuccess"        ) var success       : Boolean = false,
    @SerializedName("result"        ) var result        : List<Partner>?           = null,
)

data class Partner (

    @SerializedName("id"        ) var id        : Int?    = null,
    @SerializedName("userId"    ) var userId    : Int?    = null,
    @SerializedName("user"      ) var user      : User?   = User(),
    @SerializedName("email"     ) var email     : String? = null,
    @SerializedName("phone"     ) var phone     : String? = null,
    @SerializedName("diaChi"    ) var diaChi    : String? = null,
    @SerializedName("ngaySinh"  ) var ngaySinh  : String? = null,
    @SerializedName("tenDoiTac" ) var tenDoiTac : String = "",
    @SerializedName("mota"      ) var mota      : String? = null,
    @SerializedName("imgUrl"    ) var imgUrl    : String = ""
)