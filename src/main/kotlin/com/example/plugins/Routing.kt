package com.example.plugins

import com.example.model.Article
import com.example.model.ArticleStore
import com.example.resources.Articles
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        // default
        get("/") {
            call.respondText("Hello World!")
        }
        // use of parameter
        get("/{name}") {
            call.parameters["name"]?.let {
                call.respondText(it)
            }
        }
        // nested routes
        route("/user"){
            get("/firstname"){
                call.respondText("Colin")
            }
            get("/lastname"){
                call.respondText("Lo")
            }
        }
        // route extension function
        listOfName()

        // type-safe routing <get>
        get<Articles> {article ->
            val sortedArticle = if(article.sort == "new"){
                ArticleStore.getAllArticles().sortedByDescending { it.id }
            } else if(article.sort == "old"){
                ArticleStore.getAllArticles().sortedBy { it.id }
            } else {
                ArticleStore.getAllArticles()
            }
            call.respondText("List of sorted articles by ${article.sort}: \n $sortedArticle")
        }

        // type-safe routing <post>
        post<Articles.New> {
            try {
                val newArticle = call.receive<Article>()
                ArticleStore.createNewArticle(newArticle)
                call.respondText("Create a new article.\n $newArticle")
            } catch (e: Exception){
                call.respondText("Failed to created a article.")
            }
        }
    }
}

fun Route.listOfName(){
    get("/list"){
        call.respondText("Colin, Giroud")
    }
}