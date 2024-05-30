package com.example.plugins

import com.example.model.Connection
import com.example.model.Customer
import io.ktor.network.sockets.*
import io.ktor.serialization.kotlinx.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.serialization.json.Json
import java.time.Duration
import java.util.Collections

fun Application.configureSockets() {
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
        contentConverter = KotlinxWebsocketSerializationConverter(Json)
    }

    routing {
        // echo messages
        webSocket("/echo") {
            send("Please enter your name")
            for (frame in incoming) {
                frame as? Frame.Text ?: continue
                val receivedText = frame.readText()
                if (receivedText.equals("bye", ignoreCase = true)) {
                    close(CloseReason(CloseReason.Codes.NORMAL, "Client say bye."))
                } else {
                    send(Frame.Text("Hi, $receivedText!"))
                }
            }
        }

        // a pool of users in a chat
        val connections = Collections.synchronizedSet<Connection?>(LinkedHashSet())
        webSocket("/chat") {
            println("Adding user!")
            val curConnection = Connection(this)
            connections += curConnection
            try {
                send("You are connected! There are ${connections.count()} users here.")
                for (frame in incoming) {
                    frame as? Frame.Text ?: continue
                    val receivedText = frame.readText()
                    val textWithUsername = "[${curConnection.name}]: $receivedText"
                    connections.forEach {
                        it.session.send(textWithUsername)
                    }
                }
            } catch (e: Exception) {
                println(e.localizedMessage)
            } finally {
                println("Removing $curConnection!")
                connections -= curConnection
            }
        }

        // receive serialized object and send serialized object
        webSocket("/customer") {
            send("Send your id, firstName and lastName JSON")
            val receivedCustomer = receiveDeserialized<Customer>()
            send("Your id is ${receivedCustomer.id}, full name is ${receivedCustomer.firstName} ${receivedCustomer.lastName}")
            send("This is me: ")
            sendSerialized(Customer(1, "Jane", "Smith"))
            for (frame in incoming) {
                send("You sent a message")
            }
        }
    }
}
