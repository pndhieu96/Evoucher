package com.example.evoucher.model

import com.google.gson.annotations.SerializedName

data class ApiError (

    @SerializedName("status_code"    ) var statusCode    : Int?     = null,
    @SerializedName("errorMessages" ) var statusMessage : ArrayList<String> = arrayListOf(),
    @SerializedName("isSuccess"        ) var success       : Boolean = false

)