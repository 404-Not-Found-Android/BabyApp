package com.example.net

import com.example.request.LoginRequest
import com.example.request.RegisterUserRequest
import com.example.response.BaseResponse
import com.example.util.JsonUtil
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.StringBuilder
import java.util.concurrent.TimeUnit

/**
 * Author : ljt
 * Description :
 * CreateTime  : 4/20/21
 */
object ApiRetrofit {

    private const val PORT: Int = 8080
    private const val BASE_URL = "http://10.0.2.2"

    private fun getBaseUrl(): String {
        return StringBuilder().append(BASE_URL).append(":").append(PORT).toString()
    }

    private val okHttpClient: OkHttpClient by lazy {
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build()
    }

    private val loggingInterceptor: HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor()
    }


    private val apiService: ApiService by lazy {
        Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
    }

    suspend fun registerUser(request: RegisterUserRequest): BaseResponse {
        val requestBody = JsonUtil.toJson(request).toRequestBody()
        return apiService.registerUser(requestBody)
    }

    suspend fun login(request: LoginRequest): BaseResponse {
        val paraMap = HashMap<String, String>()
        paraMap["userName"] = request.userName
        paraMap["password"] = request.password
        return apiService.login(paraMap)
    }
}