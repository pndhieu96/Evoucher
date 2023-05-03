package com.example.evoucher.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evoucher.model.*
import com.example.evoucher.network.NetWorkService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(val netWorkService: NetWorkService) : ViewModel() {

    private val _industries = MutableLiveData<Resource<List<Industry>>>()
    val industries: LiveData<Resource<List<Industry>>>
        get() = _industries

    private val _campaigns = MutableLiveData<Resource<List<Campaign>>>()
    val canpaigns: LiveData<Resource<List<Campaign>>>
        get() = _campaigns

    private val _partners = MutableLiveData<Resource<List<Partner>>>()
    val partners: LiveData<Resource<List<Partner>>>
        get() = _partners

    fun getIndustries() {
        _industries.value = Resource.Loading()
        viewModelScope.launch {
            val resource = netWorkService.getIndustries()
            if(resource.status == ResourceStatus.SUCCESS) {
                var industriesResource =  Resource.Success(data = resource.data?.result)
                _industries.value = industriesResource
            } else {
                _industries.value = Resource.Error(resource.error!!)
            }
        }
    }

    fun getCampaigns() {
        _campaigns.value = Resource.Loading()
        viewModelScope.launch {
            val resource = netWorkService.getCampaigns()
            if(resource.status == ResourceStatus.SUCCESS) {
                var list = listOf<Campaign>()
                resource.data?.result?.let {
                    list = it
                }
                if(list?.size!! >= 5) {
                    list = list.subList(0,5)
                }
                val campainResource =  Resource.Success(data = list)
                _campaigns.value = campainResource
            } else {
                _campaigns.value = Resource.Error(resource.error!!)
            }
        }
    }

    fun getPartners() {
        _partners.value = Resource.Loading()
        viewModelScope.launch {
            val resource = netWorkService.getPartners()
            if(resource.status == ResourceStatus.SUCCESS) {
                var partnersResource =  Resource.Success(data = resource.data?.result)
                _partners.value = partnersResource
            } else {
                _partners.value = Resource.Error(resource.error!!)
            }
        }
    }
}