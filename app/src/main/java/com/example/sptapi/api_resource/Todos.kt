package com.example.sptapi.api_resource

data class Todos(
    val completed: Boolean,
    val id: Int,
    val title: String,
    val userId: Int
)