package com.example.codebaseandroidapp.base

import com.example.evoucher.model.ApiError
import com.example.evoucher.model.Resource
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BaseRepo {
    suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>) : Resource<T> {

        return withContext(Dispatchers.IO) {
            try {
                val response: Response<T> = apiToBeCalled()

                if (response.isSuccessful) {
                    Resource.Success(data = response.body()!!)
                } else {
                    val apiError = Gson().fromJson(response.errorBody()?.string(), ApiError::class.java)
                    if(apiError.statusMessage.size == 0) {
                        apiError.statusMessage.add("Something went wrong")
                    }
                    Resource.Error(apiError)
                }
            } catch (e:HttpException) {
                var listStatus = ArrayList<String>()
                listStatus.add(e.message ?: "Something went wrong")
                Resource.Error(ApiError(statusMessage = listStatus))
            } catch (e: IOException) {
                var listStatus = ArrayList<String>()
                listStatus.add("Please check your network connection")
                Resource.Error(ApiError(statusMessage = listStatus))
            } catch (e: Exception) {
                var listStatus = ArrayList<String>()
                listStatus.add("Something went wrong")
                Resource.Error(ApiError(statusMessage = listStatus))
            }
        }
    }
}