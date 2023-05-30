package com.example.evoucher.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class Campaign (
    @SerializedName("id"          ) var id          : Int?    = null,
    @SerializedName("chiNhanhId"  ) var chiNhanhId  : Int?    = null,
    @SerializedName("chiNhanh"    ) var chiNhanh    : ChiNhanh? = ChiNhanh(),
    @SerializedName("ten"         ) var ten         : String = "",
    @SerializedName("ngayBatDau"  ) var ngayBatDau  : String? = null,
    @SerializedName("ngayKetThuc" ) var ngayKetThuc : String? = null,
    @SerializedName("trangThaiId" ) var trangThaiId : Int?    = null,
    @SerializedName("trangThai"   ) var trangThai   : String? = null,
    @SerializedName("imgUrl"      ) var imgUrl      : String = "",
    @SerializedName("mota"        ) var mota        : String? = null,
    @SerializedName("createdDate" ) var createdDate : String? = null

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        TODO("chiNhanh"),
        parcel.readString().toString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString().toString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeValue(chiNhanhId)
        parcel.writeString(ten)
        parcel.writeString(ngayBatDau)
        parcel.writeString(ngayKetThuc)
        parcel.writeValue(trangThaiId)
        parcel.writeString(trangThai)
        parcel.writeString(imgUrl)
        parcel.writeString(mota)
        parcel.writeString(createdDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Campaign> {
        override fun createFromParcel(parcel: Parcel): Campaign {
            return Campaign(parcel)
        }

        override fun newArray(size: Int): Array<Campaign?> {
            return arrayOfNulls(size)
        }
    }
}

data class ChiNhanh (

    @SerializedName("id"          ) var id          : Int?    = null,
    @SerializedName("doiTacId"    ) var doiTacId    : Int?    = null,
    @SerializedName("doiTac"      ) var doiTac      : String? = null,
    @SerializedName("ten"         ) var ten         : String? = null,
    @SerializedName("diaChi"      ) var diaChi      : String? = null,
    @SerializedName("mota"        ) var mota        : String? = null,
    @SerializedName("imgUrl"      ) var imgUrl      : String? = null,
    @SerializedName("nganhHangId" ) var nganhHangId : Int?    = null,
    @SerializedName("nganhHang"   ) var nganhHang   : String? = null

)