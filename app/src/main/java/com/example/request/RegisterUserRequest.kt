package com.example.request

/**
 * Author : ljt
 * Description :
 * CreateTime  : 4/20/21
 */
data class RegisterUserRequest(
    val name: String,
    val age: String,
    val userName: String,
    val password: String,
    val nickName: String
)
