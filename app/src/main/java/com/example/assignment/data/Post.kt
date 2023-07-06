package com.example.assignment.data

data class Post(
    val authorName: String,
    val authorImageURL: String,
    val postText: String,
    val postType: PostType,
    val likeCount: Int,
    val commentCount: Int,
    val multimedia: PostMultiMedia
)

enum class PostType {
    QUESTION, MARKETING,
}

data class PostMultiMedia(
    val imageArray: List<String>?,
    val audioFile: String?,
    val videoFile: String?
)