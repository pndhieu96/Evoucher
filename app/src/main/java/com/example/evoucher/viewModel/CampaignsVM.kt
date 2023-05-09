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
class CampaignsVM @Inject constructor(val netWorkService: NetWorkService) : ViewModel() {

    private val _campaigns = MutableLiveData<Resource<List<Campaign>>>()
    val canpaigns: LiveData<Resource<List<Campaign>>>
        get() = _campaigns

    fun getCampaigns(type : Int, idPartner : Int, idIndustry : Int, campaigns: List<Campaign>) {
        _campaigns.value = Resource.Loading()
        viewModelScope.launch {
            var list : List<Campaign> = listOf()
            if(type == CampaignsFragment.BY_PARTNER) {
                list = campaigns.filter {
                    it.chiNhanh?.doiTacId == idPartner
                }
            } else if(type == CampaignsFragment.BY_INDUSTRY) {
                list = campaigns.filter {
                    it.chiNhanh?.nganhHangId == idIndustry
                }
            }
            _campaigns.value = Resource.Success(data = list)
        }
    }
}