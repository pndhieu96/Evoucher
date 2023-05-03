package com.example.evoucher.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evoucher.model.*
import com.example.evoucher.network.NetWorkService
import com.example.evoucher.utils.Utils.Companion.removeNonSpacingMarks
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class CampaignsVM @Inject constructor(val netWorkService: NetWorkService) : ViewModel() {

    private val _campaigns = MutableLiveData<Resource<List<Campaign>>>()
    val canpaigns: LiveData<Resource<List<Campaign>>>
        get() = _campaigns

    fun getCampaigns(key: String) {
        _campaigns.value = Resource.Loading()
        viewModelScope.launch {
            val resource = netWorkService.getCampaigns()
            if(resource.status == ResourceStatus.SUCCESS) {
                var list = resource.data?.result
                if(!key.isEmpty()) {
                    list = list?.filter {
                        it.ten.removeNonSpacingMarks().lowercase()
                            .contains(key.removeNonSpacingMarks().lowercase())
                    } }
                val campainResource =  Resource.Success(data = list)
                _campaigns.value = campainResource
            } else {
                _campaigns.value = Resource.Error(resource.error!!)
            }
        }
    }
}