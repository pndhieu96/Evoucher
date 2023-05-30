package com.example.evoucher.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Partners (
    var result        : List<Partner>?           = listOf()
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
) : Serializable