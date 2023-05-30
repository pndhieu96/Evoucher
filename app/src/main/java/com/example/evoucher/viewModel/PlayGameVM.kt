package com.example.evoucher.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evoucher.model.GameResult
import com.example.evoucher.model.Resource
import com.example.evoucher.model.ResourceStatus
import com.example.evoucher.network.NetWorkService
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class PlayGameVM @Inject constructor(val netWorkService: NetWorkService) : ViewModel() {
    private val _games = MutableLiveData<Resource<GameResult>>()
    val games: LiveData<Resource<GameResult>>
        get() = _games

    fun playGames(userId: Int, token: String, min: Int, max: Int,
                  trochoiId: Int, chiendichId: Int, xxx: Boolean) {

        val requestBodyJson = Gson().toJson(
            mapOf(
                "userId" to userId,
                "token" to token,
                "min" to min,
                "max" to max,
                "trochoiId" to trochoiId,
                "chiendichId" to chiendichId,
                "xxx" to xxx
            )
        )
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = requestBodyJson.toRequestBody(mediaType)

        _games.value = Resource.Loading()
        viewModelScope.launch {

            val resource = netWorkService.playGame(requestBody)
            if(resource.status == ResourceStatus.SUCCESS) {
                var gamesResource = Resource.Success(data = resource.data?.result)
                _games.value = gamesResource
            } else {
                _games.value = Resource.Error(resource.error!!)
            }
        }
    }

    fun getCouponType(){

    }
}