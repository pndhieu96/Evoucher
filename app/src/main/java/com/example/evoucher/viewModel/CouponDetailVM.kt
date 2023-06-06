package com.example.evoucher.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evoucher.model.*
import com.example.evoucher.network.NetWorkService
import com.example.evoucher.ui.CampaignsFragment
import com.example.evoucher.utils.Utils.Companion.removeNonSpacingMarks
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class CouponDetailVM @Inject constructor(val netWorkService: NetWorkService) : ViewModel() {

    private val _branch = MutableLiveData<Resource<List<ChiNhanh>>>()
    val branch: LiveData<Resource<List<ChiNhanh>>>
        get() = _branch

    fun getBranch(id: Int) {
        _branch.value = Resource.Loading()
        viewModelScope.launch {
            val resource = netWorkService.getBranch()
            if(resource.status == ResourceStatus.SUCCESS) {
                val gamesResource =  Resource.Success(data = resource.data?.result?.filter { it.id == id })
                _branch.value = gamesResource
            } else {
                _branch.value = Resource.Error(resource.error!!)
            }
        }
    }
}