package com.example.evoucher.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class UserResult (

    @SerializedName("user"     ) var user     : User?     = User(),
    @SerializedName("moreInfo" ) var moreInfo : MoreInfo? = MoreInfo(),
    @SerializedName("token"    ) var token    : String   = "",
    @SerializedName("isBlockchain"    ) var isBlockchain    : Boolean   = true,
) : Serializable

class User (
    @SerializedName("id"          ) var id          : Int?    = null,
    @SerializedName("username"    ) var username    : String? = null,
    @SerializedName("password"    ) var password    : String? = null,
    @SerializedName("role"        ) var role        : String? = null,
    @SerializedName("hoTen"       ) var hoTen       : String? = null,
    @SerializedName("createdDate" ) var createdDate : String? = null,
    @SerializedName("updatedDate" ) var updatedDate : String? = null
)

data class MoreInfo (
    @SerializedName("id"           ) var id           : Int?    = null,
    @SerializedName("userId"       ) var userId       : Int?    = null,
    @SerializedName("user"         ) var user         : User?   = User(),
    @SerializedName("email"        ) var email        : String? = null,
    @SerializedName("phone"        ) var phone        : String? = null,
    @SerializedName("diaChi"       ) var diaChi       : String? = null,
    @SerializedName("ngaySinh"     ) var ngaySinh     : String? = null,
    @SerializedName("thongTinKhac" ) var thongTinKhac : String? = null,
    @SerializedName("imgUrl") var imgUrl : String = ""
)