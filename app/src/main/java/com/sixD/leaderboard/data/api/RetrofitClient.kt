package com.sixD.leaderboard.data.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

/**
 * Singleton object that provides and configures the Retrofit API service.
 */
object RetrofitClient {
    private const val BASE_URL = "https://mocki.io/"

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttp = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    /**
     * The single instance of [ApiService] for making network requests.
     */
    val api: ApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttp)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(ApiService::class.java)
}
