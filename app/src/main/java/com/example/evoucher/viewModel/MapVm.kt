package com.example.evoucher.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evoucher.model.ChiNhanh
import com.example.evoucher.model.Game
import com.example.evoucher.model.Resource
import com.example.evoucher.model.ResourceStatus
import com.example.evoucher.network.NetWorkService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapVm @Inject constructor(val netWorkService: NetWorkService)
    : ViewModel() {

    private val _branch = MutableLiveData<Resource<List<ChiNhanh>>>()
    val branch: LiveData<Resource<List<ChiNhanh>>>
        get() = _branch

    fun getBranch() {
        _branch.value = Resource.Loading()
        viewModelScope.launch {
            val resource = netWorkService.getBranch()
            if(resource.status == ResourceStatus.SUCCESS) {
                val gamesResource =  Resource.Success(data = resource.data?.result)
                _branch.value = gamesResource
            } else {
                _branch.value = Resource.Error(resource.error!!)
            }
        }
    }
}