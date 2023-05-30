package com.example.codebaseandroidapp.di

import android.content.Context
import androidx.recyclerview.widget.DiffUtil
import com.example.evoucher.utils.ConstantUtils.Companion.BASE_URL
import com.example.evoucher.utils.SharedPreferencesImp
import com.example.evoucher.utils.SharedPreferencesImp.Companion.TOKEN
import com.example.evoucher.utils.Utils
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Headers
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ActivityRetainedComponent::class)
class ActitvityRetainedModule {

    @Provides
    fun provideHttpLogingInterceptror(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    fun getInterceptor(
        sharedPreferencesImp: SharedPreferencesImp,
        @ApplicationContext appContext: Context
    ): Interceptor {
        return Interceptor {
            val request = it.request().newBuilder()
            val token = sharedPreferencesImp.getString(TOKEN)
            if(!token.isEmpty()) {
                request.addHeader("Authorization", "bearer $token")
            }
            val actualRequest = request.build()
            val response = it.proceed(actualRequest)
            if(response.code == 401 || response.code == 400) {
                sharedPreferencesImp.putString(TOKEN, "")
                Utils.reload(appContext)
            }
            response
        }
    }

    @Provides
    fun provideOkHttpClient (
        loggingInterceptor: HttpLoggingInterceptor,
        interceptor : Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addNetworkInterceptor(interceptor)
            .build()
    }

    @Provides
    fun provideRetrofit(
        client : OkHttpClient,
    ) : Retrofit {
        //Khởi tạo đối tượng retrofit
        return Retrofit.Builder()
            //Nói với retrofit cách xử lý dữ liệu lấy về từ service
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl(BASE_URL)
            .build()
    }

}
@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActitvityAbstractModule {

}