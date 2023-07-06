package com.example.assignment.data

import com.example.assignment.data.firebase.FirebaseConstants
import com.google.firebase.firestore.DocumentSnapshot
import com.google.gson.Gson
import java.io.Serializable

data class Post(
    val authorName: String,
    val authorImageURL: String,
    val postText: String,
    val postType: PostType,
    val likeCount: Int,
    val commentCount: Int,
    val multimedia: PostMultiMedia
) {
    companion object {
        fun fromDocumentSnapshot(ds: DocumentSnapshot): Post {
            val imageArrayStr = ds[FirebaseConstants.Post.Multimedia.imageArrayKey].toString()
            val audioFileStr = ds[FirebaseConstants.Post.Multimedia.audioFileKey].toString()
            val videoFileStr = ds[FirebaseConstants.Post.Multimedia.videoFileKey].toString()
            return Post(
                authorName = ds[FirebaseConstants.Post.authorNameKey].toString(),
                authorImageURL = ds[FirebaseConstants.Post.authorImageURLKey].toString(),
                postText = ds[FirebaseConstants.Post.postTextKey].toString(),
                postType = Gson().fromJson(
                    ds[FirebaseConstants.Post.postTypeKey].toString(),
                    PostType::class.java
                ),
                likeCount = ds[FirebaseConstants.Post.likeCountKey].toString().toInt(),
                commentCount = ds[FirebaseConstants.Post.commentCountKey].toString().toInt(),
                multimedia = PostMultiMedia(
                    imageArray = if (imageArrayStr == FirebaseConstants.nullId) null
                    else Gson().fromJson(imageArrayStr, mutableListOf<String>()::class.java),
                    audioFile = if (audioFileStr == FirebaseConstants.nullId) null else audioFileStr,
                    videoFile = if (videoFileStr == FirebaseConstants.nullId) null else videoFileStr,
                ),
            )
        }
    }

    fun toHashMap(): HashMap<String, Serializable> {
        return hashMapOf(
            FirebaseConstants.Post.authorNameKey to this.authorName,
            FirebaseConstants.Post.authorImageURLKey to this.authorImageURL,
            FirebaseConstants.Post.postTextKey to this.postText,
            FirebaseConstants.Post.postTypeKey to this.postType,
            FirebaseConstants.Post.likeCountKey to this.likeCount,
            FirebaseConstants.Post.commentCountKey to this.commentCount,
            FirebaseConstants.Post.Multimedia.imageArrayKey to
                    if (this.multimedia.imageArray != null)
                        Gson().toJson(this.multimedia.imageArray)
                    else FirebaseConstants.nullId,
            FirebaseConstants.Post.Multimedia.audioFileKey to
                    (this.multimedia.audioFile ?: FirebaseConstants.nullId),
            FirebaseConstants.Post.Multimedia.videoFileKey to
                    (this.multimedia.videoFile ?: FirebaseConstants.nullId),
        )
    }
}

enum class PostType {
    QUESTION, MARKETING,
}

data class PostMultiMedia(
    val imageArray: List<String>?,
    val audioFile: String?,
    val videoFile: String?
)

fun main() {
    val hash = TestData.getPostList()[0].toHashMap()
    for (i in hash) {
        println("${i.key}\t- ${i.value}")
    }
}