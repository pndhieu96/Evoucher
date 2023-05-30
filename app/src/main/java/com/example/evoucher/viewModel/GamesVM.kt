package com.example.evoucher.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evoucher.model.Game
import com.example.evoucher.model.Industry
import com.example.evoucher.model.Resource
import com.example.evoucher.model.ResourceStatus
import com.example.evoucher.network.NetWorkService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesVM @Inject constructor(val netWorkService: NetWorkService) : ViewModel() {

    private val _games = MutableLiveData<Resource<List<Game>>>()
    val games: LiveData<Resource<List<Game>>>
        get() = _games

    fun getGames() {
        _games.value = Resource.Loading()
        viewModelScope.launch {
            val resource = netWorkService.getGames()
            if(resource.status == ResourceStatus.SUCCESS) {
                var gamesResource =  Resource.Success(data = resource.data?.result)
                _games.value = gamesResource
            } else {
                _games.value = Resource.Error(resource.error!!)
            }
        }
    }
}