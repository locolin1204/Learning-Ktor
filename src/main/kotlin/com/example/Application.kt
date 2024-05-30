package com.example

import com.example.plugins.*
import io.ktor.server.application.*
import io.ktor.server.resources.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    install(Resources)
    configureSerialization()
    configureSockets()
    configureRouting()
}
