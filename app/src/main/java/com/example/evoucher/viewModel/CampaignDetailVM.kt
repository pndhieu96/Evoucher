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
class CampaignDetailVM @Inject constructor(val netWorkService: NetWorkService) : ViewModel() {

    private val _campaign = MutableLiveData<Resource<CampaignDetail>>()
    val canpaign: LiveData<Resource<CampaignDetail>>
        get() = _campaign

    fun getCampaignDetail(id: String) {
        _campaign.value = Resource.Loading()
        viewModelScope.launch {
            val resource = netWorkService.getCampaignDetail(id)
            if(resource.status == ResourceStatus.SUCCESS) {
                _campaign.value = Resource.Success(data = resource.data?.result)
            } else {
                _campaign.value = Resource.Error(resource.error!!)
            }
        }
    }
}