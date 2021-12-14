package com.example.loanapp.di.data

import android.content.Context
import com.example.loanapp.data.remote.api.LoansApi
import com.example.loanapp.data.remote.interceptor.TokenInterceptor
import com.example.loanapp.util.Common.BASE_URL
import com.example.loanapp.data.remote.util.ResponseHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {


    @Provides
    @Singleton
    fun provideOkHttpClient(tokenInterceptor: TokenInterceptor): OkHttpClient =
        OkHttpClient().newBuilder()
            .addInterceptor(tokenInterceptor)
            .callTimeout(2, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    @Provides
    @Singleton
    fun provideLoansApi(retrofit: Retrofit): LoansApi =
        retrofit.create(LoansApi::class.java)

    @Provides
    @Singleton
    fun provideResponseHandler(@ApplicationContext context: Context): ResponseHandler =
        ResponseHandler(context)
}