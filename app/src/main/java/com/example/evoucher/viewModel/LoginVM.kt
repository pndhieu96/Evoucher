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
class LoginVM @Inject constructor(
    val netWorkService: NetWorkService
) : ViewModel() {
    private val _UserResult = MutableLiveData<Resource<UserResult>>()
    val userResult: LiveData<Resource<UserResult>>
    get() = _UserResult

    fun login(user: String, pass: String) {
        _UserResult.value = Resource.Loading()
        viewModelScope.launch {
            var jsonObject = JSONObject()
            jsonObject.put("userName", user)
            jsonObject.put("password", pass)
            val resourceLogin = netWorkService.login(jsonObject.toString())
            if(resourceLogin.status == ResourceStatus.SUCCESS) {
                var mUserResult =  Resource.Success(data = resourceLogin.data?.result)
                _UserResult.value = mUserResult as Resource<UserResult>
            } else {
                _UserResult.value = Resource.Error(ApiError())
            }
        }
    }
}