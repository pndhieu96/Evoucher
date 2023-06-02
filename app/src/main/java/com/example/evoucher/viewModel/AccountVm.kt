package com.example.evoucher.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evoucher.model.*
import com.example.evoucher.network.NetWorkService
import com.example.evoucher.ui.CampaignsFragment
import com.example.evoucher.utils.Utils
import com.example.evoucher.utils.Utils.Companion.plus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AccountVm @Inject constructor(val netWorkService: NetWorkService) : ViewModel(){
    private val _coupons = MutableLiveData<Resource<List<CouponResult>>>()
    val coupons: LiveData<Resource<List<CouponResult>>>
        get() = _coupons

    fun getCoupon(id: Int) {
        _coupons.value = Resource.Loading()
        viewModelScope.launch {
            val resource = netWorkService.getCoupon()
            if(resource.status == ResourceStatus.SUCCESS) {
                val couponResource = Resource.Success(data = resource.data?.result?.filter {
                    it.coupon.userId == id
                    && Utils.stringToDate(it.coupon.createdDate).plus(it.coupon.thoiHan) >= Date()
                })
                _coupons.value = couponResource
            } else {
                _coupons.value = Resource.Error(resource.error!!)
            }
        }
    }
}