package com.example.models

import io.ktor.http.*
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val statusCode : String,
    val data : T,
    val errorMsg : String,
)
val baseResponse = mutableListOf<BaseResponse<Any>>()