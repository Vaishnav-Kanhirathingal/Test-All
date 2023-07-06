package com.example.assignment.data

import com.example.assignment.data.firebase.FirebaseConstants
import com.google.firebase.firestore.DocumentSnapshot
import java.io.Serializable

data class Comment(
    val comment: String,
    val user: String,
    val userImageURL: String
) {
    companion object {
        fun fromDocumentSnapshot(ds: DocumentSnapshot): Comment {
            return Comment(
                comment = ds[FirebaseConstants.Comment.commentKey].toString(),
                user = ds[FirebaseConstants.Comment.userKey].toString(),
                userImageURL = ds[FirebaseConstants.Comment.userImageURLKey].toString(),
            )
        }
    }

    fun toHashMap(): HashMap<String, Serializable> {
        return hashMapOf(
            FirebaseConstants.Comment.commentKey to this.comment,
            FirebaseConstants.Comment.userKey to this.user,
            FirebaseConstants.Comment.userImageURLKey to this.userImageURL,
        )
    }
}