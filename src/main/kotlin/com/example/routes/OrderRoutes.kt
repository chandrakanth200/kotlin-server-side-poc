package com.example.routes

import com.example.models.orderStorage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.orderRouting(){
    route("/order"){
        get {
            if (orderStorage.isNotEmpty()){
                call.respond(orderStorage)
            }else call.respondText("No Orders found", status = HttpStatusCode.OK)
        }
        get("{id?}"){
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing Id",
                status = HttpStatusCode.BadRequest
            )
            val orders = orderStorage.find { it.number == id } ?: return@get call.respondText(
                "No Order with id",
                status = HttpStatusCode.NotFound
            )
            call.respond(orders)
        }
        get("{id?}/total"){
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Bad Request",
                status = HttpStatusCode.BadRequest
            )
            val order = orderStorage.find { it.number == id } ?: return@get call.respondText(
                "Not found",
                status = HttpStatusCode.NotFound
            )
            val total = order.contents.sumOf { it.amount * it.price }
            call.respond(total)
        }
    }
}