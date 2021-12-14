package com.example.loanapp.data.remote.interceptor

import com.example.loanapp.domain.use_case.auth.GetTokenUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(private val getTokenUseCase: GetTokenUseCase) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { getTokenUseCase().first() }
        var request = chain.request()
        if (token.isNotBlank()) {
            request = request.newBuilder().addHeader("Authorization", token)
                .build()
        }
        return chain.proceed(request)
    }
}