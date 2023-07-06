package com.example.assignment.data.firebase

object FirebaseConstants {
    const val listOfPostKey = "listOfPostKey"

    object Post {
        const val authorNameKey = "authorName_key"
        const val authorImageURLKey = "authorImageURL_key"
        const val postTextKey = "postText_key"
        const val postTypeKey = "postType_key"
        const val likeCountKey = "likeCount_key"
        const val commentCountKey = "commentCount_key"

        object Multimedia {
            const val imageArrayKey = "imageArray_key"
            const val audioFileKey = "audioFile_key"
            const val videoFileKey = "videoFile_key"
        }
    }

    object Comment {
        const val commentKey = "commentKey"
        const val userKey = "userKey"
        const val userImageURLKey = "userImageURLKey"
    }

    const val nullId = "null_key"
    const val commentListKey = "commentListKey"
}