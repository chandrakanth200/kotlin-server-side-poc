package com.example.routes

import com.example.models.BaseResponse
import com.example.models.Customer
import com.example.models.baseResponse
import com.example.models.customerStorage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.customerRouting() {
    route("/customer") {
        get {
            if (customerStorage.isNotEmpty()){
                call.respond(customerStorage)
            }else
            {
                call.respondText("No customers found", status = HttpStatusCode.OK)
               // val error = listOf<String>("No customers found")
                /*baseResponse.add(BaseResponse(statusCode = HttpStatusCode.OK.toString(), data = customerStorage, errorMsg = "No customers found"))
                call.respond(status = HttpStatusCode.OK,baseResponse)*/
            }
        }
        get("{id?}") {
            val id = call.parameters["id"]?:return@get call.respondText("Missing Id", status = HttpStatusCode.BadRequest)
            val customer =
                customerStorage.find { it.id == id } ?: return@get call.respondText(
                    "No customer with id $id",
                    status = HttpStatusCode.NotFound
                )
            call.respond(customer)
        }
        post {
            val customer = call.receive<Customer>()
            customerStorage.add(customer)
            call.respondText("Customer Stored Successfully", status = HttpStatusCode.Created)
        }
/*
        delete("{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respondText("Missing Id", status = HttpStatusCode.BadRequest)
            if (customerStorage.removeIf { id == it.id }){
                call.respondText("Customer removed successfully", status = HttpStatusCode.Accepted)
            }else{
                call.respondText("Not found", status = HttpStatusCode.NotFound)
            }
        }
*/
        delete("delete/{?id}"){
            val id = call.parameters["id"] ?: return@delete call.respondText("Missing Id", status = HttpStatusCode.BadRequest)
            if (customerStorage.removeIf { id == it.id }){
                call.respondText("Customer removed successfully", status = HttpStatusCode.Accepted)
            }else{
                call.respondText("Not found", status = HttpStatusCode.NotFound)
            }
        }
    }
}