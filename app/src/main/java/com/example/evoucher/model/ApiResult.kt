package com.example.evoucher.model

import com.google.gson.annotations.SerializedName
import java.util.Objects

data class ApiResult<T> (
    @SerializedName("statusCode"    ) var statusCode    : Int?               = 0,
    @SerializedName("isSuccess"     ) var isSuccess     : Boolean?           = true,
    @SerializedName("errorMessages" ) var errorMessages : ArrayList<String> = arrayListOf(),
    @SerializedName("result"        ) var result        : T?           = null
)