package com.example.assignment.data.firebase

import android.util.Log
import com.example.assignment.data.Post
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseFunctions {
    val TAG = this::class.java.simpleName

    fun uploadToFirebase(postList: List<Post>) {
        val size = postList.size
        var counter = 0
        val firebase = FirebaseFirestore.getInstance()
        postList.forEach { post ->
            firebase
                .collection(FirebaseConstants.listOfPostKey)
                .document()
                .set(post.toHashMap())
                .addOnSuccessListener {
                    counter++
                    if (counter == size) {
                        Log.d(TAG, "added multiple posts to database")
                    }
                }.addOnFailureListener { it.printStackTrace() }
        }
    }

    fun getListOfPosts(onReceive: (List<DocumentSnapshot>) -> Unit) {
        val firebase = FirebaseFirestore.getInstance()
        firebase
            .collection(FirebaseConstants.listOfPostKey)
            .get()
            .addOnSuccessListener { onReceive(it.documents) }
            .addOnFailureListener { it.printStackTrace() }
    }

    fun addComments(){

    }
}