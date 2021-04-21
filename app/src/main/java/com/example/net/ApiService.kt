package com.example.net

import com.example.response.BaseResponse
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * Author : ljt
 * Description :
 * CreateTime  : 4/20/21
 */
interface ApiService {
    @Headers("Content-Type:application/json;charset=utf-8")
    @POST("/user/register_user")
    suspend fun registerUser(@Body requestBody: RequestBody): BaseResponse
}