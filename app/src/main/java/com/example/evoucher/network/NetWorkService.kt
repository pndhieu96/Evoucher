package com.example.evoucher.network

import com.example.codebaseandroidapp.base.BaseRepo
import com.example.evoucher.model.*
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import javax.inject.Inject

class NetWorkService @Inject constructor(
    retrofit: Retrofit
) : BaseRepo(){
    private var apiService: ApiService? = null

    init {
        apiService = retrofit.create(ApiService::class.java)
    }

    suspend fun login(jsonObject: String) : Resource<ApiResult<UserResult>> {
        return safeApiCall { apiService?.login(jsonObject)!! }
    }

    suspend fun getIndustries() : Resource<ApiResult<List<Industry>>> {
        return safeApiCall { apiService?.getIndustries()!! }
    }

    suspend fun getCampaigns() : Resource<ApiResult<List<Campaign>>> {
        return safeApiCall { apiService?.getCampaigns()!! }
    }

    suspend fun getPartners() : Resource<ApiResult<List<Partner>>> {
        return safeApiCall { apiService?.getPartners()!! }
    }

    suspend fun getCouponType(id: String) : Resource<ApiResult<CouponType>> {
        return safeApiCall { apiService?.getCouponType(id)!! }
    }

    suspend fun getGames() : Resource<ApiResult<List<Game>>> {
        return safeApiCall { apiService?.getGames()!! }
    }

    suspend fun playGame(requestBody: RequestBody) : Resource<ApiResult<GameResult>> {
        return safeApiCall { apiService?.playGame(requestBody)!! }
    }
}

interface ApiService {
    @POST("UsersAuth/login")
    @Headers("Content-Type: application/json")
    suspend fun login(@Body jsonObject: String) : Response<ApiResult<UserResult>>

    @GET("NganhHang/GetAllNganhHang")
    suspend fun getIndustries() : Response<ApiResult<List<Industry>>>

    @GET("User/GetDoiTac")
    suspend fun getPartners() : Response<ApiResult<List<Partner>>>

    @GET("ChienDich/GetAllDetailed")
    suspend fun getCampaigns() : Response<ApiResult<List<Campaign>>>

    @GET("LoaiCoupon/GetLoaiCoupon/{id}")
    suspend fun getCouponType(@Path("id") id : String) : Response<ApiResult<CouponType>>

    @GET("TroChoi/GetAllTroChoi")
    suspend fun getGames() : Response<ApiResult<List<Game>>>

    @POST("ChoiTroChoi")
    suspend fun playGame(@Body requestBody: RequestBody) : Response<ApiResult<GameResult>>
}