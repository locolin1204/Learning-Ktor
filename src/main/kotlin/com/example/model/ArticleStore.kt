package com.example.model

object ArticleStore {
    private val articles = mutableListOf<Article>(
        Article(1, "First Article", "Content of the first article"),
        Article(2, "Second Article", "Content of the second article"),
        Article(3, "Third Article", "Content of the third article")
    )
    fun getAllArticles(): List<Article> = articles
    fun createNewArticle(newArticle: Article): Article {
        articles.add(newArticle)
        return newArticle
    }
}