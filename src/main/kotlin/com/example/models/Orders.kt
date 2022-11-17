package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Orders(
    val number : String,
    val contents : List<OrdersItem>
)

@Serializable
data class OrdersItem(
    val item : String,
    val amount : Int,
    val price : Double
)

val orderStorage = mutableListOf<Orders>()
