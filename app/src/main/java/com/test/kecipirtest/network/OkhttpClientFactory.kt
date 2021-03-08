package com.test.kecipirtest.network

import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object OkhttpClientFactory {
    private const val NETWORK_CALL_TIMEOUT = 60

    fun create(): OkHttpClient {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val builder = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(interceptor)

        val dispatcher = Dispatcher()
        dispatcher.maxRequests = NETWORK_CALL_TIMEOUT
        builder.dispatcher(dispatcher)
        return builder.build()
    }
}