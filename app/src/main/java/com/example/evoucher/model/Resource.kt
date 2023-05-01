package com.example.evoucher.model

import java.util.concurrent.atomic.AtomicBoolean

sealed class Resource<T>(
    val data: T? = null,
    val error: ApiError? = null,
    val status: ResourceStatus
) {
    var hasBeenHandled = AtomicBoolean(false)
        set get

    class Success<T>(data: T?) : Resource<T>(data = data, status = ResourceStatus.SUCCESS)

    class Error<T>(error: ApiError) : Resource<T>(error = error, status = ResourceStatus.ERROR)

    class Loading<T>() : Resource<T>(status = ResourceStatus.LOADING)

    fun getContentIfNotHandled() : T?{
        if(hasBeenHandled.getAndSet(true).not())
            return data
        else
            return null
    }

    fun getErrorIfNotHandled() : ApiError?{
        if(hasBeenHandled.getAndSet(true).not())
            return error
        else
            return null
    }

    fun isLoadingIfNotHandled() : Boolean? {
        if(hasBeenHandled.getAndSet(true).not()) {
            return status == ResourceStatus.LOADING
        } else {
            return null
        }
    }
}