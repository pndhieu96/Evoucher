package com.example.evoucher.network

import com.example.codebaseandroidapp.base.BaseRepo
import com.example.evoucher.model.*
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import javax.inject.Inject

class NetWorkService @Inject constructor(
    retrofit: Retrofit
) : BaseRepo(){
    private var apiService: ApiService? = null

    init {
        apiService = retrofit.create(ApiService::class.java)
    }

    suspend fun login(jsonObject: String) : Resource<UserApiResult> {
        return safeApiCall { apiService?.login(jsonObject)!! }
    }

    suspend fun getIndustries() : Resource<IndustriesApiResult> {
        return safeApiCall { apiService?.getIndustries()!! }
    }

    suspend fun getCampaigns() : Resource<CampaignsApiResult> {
        return safeApiCall { apiService?.getCampaigns()!! }
    }

    suspend fun getPartners() : Resource<PartnersApiResult> {
        return safeApiCall { apiService?.getPartners()!! }
    }
}

interface ApiService {
    @POST("UsersAuth/login")
    @Headers("Content-Type: application/json")
    suspend fun login(@Body jsonObject: String) : Response<UserApiResult>

    @GET("NganhHang/GetAllNganhHang")
    suspend fun getIndustries() : Response<IndustriesApiResult>

    @GET("User/GetDoiTac")
    suspend fun getPartners() : Response<PartnersApiResult>

    @GET("ChienDich/GetAll")
    suspend fun getCampaigns() : Response<CampaignsApiResult>
}