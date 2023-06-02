package com.example.evoucher.model

import com.google.gson.annotations.SerializedName

data class Game (

    @SerializedName("id"       ) var id       : Int?    = null,
    @SerializedName("ten"      ) var ten      : String = "",
    @SerializedName("luatChoi" ) var luatChoi : String = "",
    @SerializedName("imgUrl"   ) var imgUrl   : String = ""
) : java.io.Serializable