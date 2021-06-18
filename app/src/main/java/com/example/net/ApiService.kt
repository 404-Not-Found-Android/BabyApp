package com.example.net

import com.example.response.BaseResponse
import okhttp3.MultipartBody
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

    @FormUrlEncoded
    @POST("/user/login")
    suspend fun login(@FieldMap paraMap: HashMap<String, String>): BaseResponse

    @Streaming
    @POST("/upload")
    suspend fun uploadFile(@Body body: MultipartBody): BaseResponse
}