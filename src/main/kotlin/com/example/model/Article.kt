package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val id: Long,
    val title: String,
    val content: String
)