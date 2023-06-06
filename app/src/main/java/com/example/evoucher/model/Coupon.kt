package com.example.evoucher.model

import java.io.Serializable

data class Coupon(
    val id: Int,
    val trangThai: Int,
    val createdDate: String,
    val thoiHan: Int,
    val troChoiId: Int,
    val troChoi: Game,
    val userId: Int,
    val user: User,
    val loaiCouponId: Int,
    val chiendichId: Int
)

data class CouponResult(
    val coupon: Coupon,
    val loaiCoupon_Ten: String,
    val chienDich_Ten: String,
    val chiNhanh_Ten: String,
    val chiNhanh_DiaChi: String,
    val chiNhanhId: Int
) : Serializable