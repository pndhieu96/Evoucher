package com.example.evoucher.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList


data class IndustriesApiResult (
    @SerializedName("status_code"    ) var statusCode    : Int?     = null,
    @SerializedName("errorMessages" ) var statusMessage : ArrayList<String> = arrayListOf(),
    @SerializedName("isSuccess"        ) var success       : Boolean = false,
    @SerializedName("result"        ) var result        : List<Industry>           = arrayListOf(),
)

data class Industry (

    @SerializedName("id"     ) var id     : Int     = -1,
    @SerializedName("ten" ) var ten : String = "",
    @SerializedName("mota"    ) var mota    : String   = ""
) : Serializable