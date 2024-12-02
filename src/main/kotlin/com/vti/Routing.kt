package com.vti

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        get("/note/{id}"){
            val id = call.parameters["id"]
            call.respondText("This is a note with id $id")
        }

        get("/note"){
            val id = call.request.queryParameters["id"]
            call.respondText("This is a note with id $id")

        }

        // localhost:8081/notes
        route("/notes") {
            get {
                call.respondText("This is a note")
            }

            route("create") {
                // localhost:8081/notes/create
                post {
                    val body = call.receive<String>()
                    call.respond(body)
                }
            }

            delete {
                val body = call.receive<String>()
                call.respond(body)
            }
        }

    }
}
