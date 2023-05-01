package com.example.codebaseandroidapp.di

import androidx.recyclerview.widget.DiffUtil
import com.example.evoucher.utils.ConstantUtils.Companion.BASE_URL
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Qualifier

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
    fun provideOkHttpClient (
        interceptor : HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Provides
    fun provideRetrofit(
        client : OkHttpClient
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
@InstallIn(ActivityComponent::class)
abstract class ActitvityAbstractModule {

}