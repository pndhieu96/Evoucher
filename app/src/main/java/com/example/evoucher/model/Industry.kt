package com.example.evoucher.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

data class Industry (

    @SerializedName("id"     ) var id     : Int     = -1,
    @SerializedName("ten" ) var ten : String = "",
    @SerializedName("mota"    ) var mota    : String   = ""
) : Serializable