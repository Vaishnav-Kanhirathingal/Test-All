package com.example.sptapi

import com.example.sptapi.api_resource.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

val retrofit: Retrofit = Retrofit
    .Builder()
    .baseUrl("https://jsonplaceholder.typicode.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val retrofitService: TypicodeApi by lazy { retrofit.create(TypicodeApi::class.java) }

interface TypicodeApi {
    @GET("posts")
    suspend fun getPosts(): List<Posts>

    @GET("comments")
    suspend fun getComments(): List<Comments>

    @GET("albums")
    suspend fun getAlbums(): List<Albums>

    @GET("photos")
    suspend fun getPhotos(): List<Photos>

    @GET("todos")
    suspend fun getTodos(): List<Todos>

    @GET("users")
    suspend fun getUsers(): List<Users>
}

enum class CallStatus {
    LOADING, ERROR, DONE
}