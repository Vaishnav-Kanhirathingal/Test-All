package com.example.assignment.data.firebase

import android.util.Log
import com.example.assignment.data.TestData
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseFunctions {
    val TAG = this::class.java.simpleName

    fun uploadTestPostToFirebase() {
        val postList = TestData.getPostList()
        val size = postList.size
        var counter = 0
        val firebase = FirebaseFirestore.getInstance()
        val listCollection = firebase
            .collection(FirebaseConstants.listOfPostKey)
        postList.forEach { post ->
            listCollection
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

    @Deprecated("unstable")
    fun uploadTestPostToFirebaseTest() {
        val onFailure = { e: java.lang.Exception -> e.printStackTrace() }
        val firebase = FirebaseFirestore.getInstance()
        val pageListDoc = firebase
            .collection(FirebaseConstants.listOfPostKey)
            .document(FirebaseConstants.listOfPostKey)
        val limit = 10
        var pageCounter = 0
        repeat(
            times = limit,
            action = { pageNumber ->
                pageListDoc
                    .set(hashMapOf(FirebaseConstants.listOfPostKey to FirebaseConstants.listOfPostKey))
                    .addOnSuccessListener {
                        val pageCollection = pageListDoc
                            .collection("${FirebaseConstants.listOfPostKey}_$pageNumber")
                        val postList = TestData.getPostList()
                        val size = postList.size
                        var postCounter = 0
                        postList.forEach { post ->
                            pageCollection.document().set(post.toHashMap()).addOnSuccessListener {
                                postCounter++
                                if (postCounter == size) {
                                    pageCounter++
                                    Log.d(TAG, "added a page to database")
                                    if (pageCounter == limit) {
                                        Log.d(TAG, "all pages added to firebase")
                                    }
                                }
                            }.addOnFailureListener(onFailure)
                        }
                    }.addOnFailureListener(onFailure)
            }
        )
    }

    fun getListOfPosts(onReceive: (List<DocumentSnapshot>) -> Unit) {
        val firebase = FirebaseFirestore.getInstance()
        firebase
            .collection(FirebaseConstants.listOfPostKey)
            .get()
            .addOnSuccessListener { onReceive(it.documents) }
            .addOnFailureListener { it.printStackTrace() }
    }

    @Deprecated("unstable")
    fun getListOfPostsTest(
        pageNumber: Int,
        onReceive: (List<DocumentSnapshot>) -> Unit
    ) {
        val firebase = FirebaseFirestore.getInstance()
        firebase
            .collection(FirebaseConstants.listOfPostKey)
            .document(FirebaseConstants.listOfPostKey)
            .collection("${FirebaseConstants.listOfPostKey}_$pageNumber")
            .get()
            .addOnSuccessListener { onReceive(it.documents) }
            .addOnFailureListener { it.printStackTrace() }
    }

    fun getPost(
        postId: String,
        onReceive: (DocumentSnapshot) -> Unit
    ) {
        val firebase = FirebaseFirestore.getInstance()
        firebase
            .collection(FirebaseConstants.listOfPostKey)
            .document(postId)
            .get()
            .addOnSuccessListener(onReceive)
            .addOnFailureListener { it.printStackTrace() }
    }

    fun addComments() {
        val firebase = FirebaseFirestore.getInstance()
        val listDoc = firebase.collection(FirebaseConstants.commentListKey)
            .document(FirebaseConstants.commentListKey)

        val testList = TestData.getCommentList()
        val limit = testList.size
        var counter = 0

        val onFailure = { e: Exception -> e.printStackTrace() }

        listDoc.set(hashMapOf("random" to "random")).addOnSuccessListener {
            val listCollection = listDoc.collection(FirebaseConstants.commentListKey)
            testList.forEach {
                listCollection.add(it.toHashMap()).addOnSuccessListener {
                    counter++
                    if (counter == limit) {
                        Log.d(TAG, "comments added")
                    }
                }.addOnFailureListener(onFailure)
            }
        }.addOnFailureListener(onFailure)
    }

    fun getComments(onReceive: (List<DocumentSnapshot>) -> Unit) {
        val firebase = FirebaseFirestore.getInstance()
        firebase
            .collection(FirebaseConstants.commentListKey)
            .document(FirebaseConstants.commentListKey)
            .collection(FirebaseConstants.commentListKey)
            .get()
            .addOnSuccessListener { onReceive(it.documents) }
            .addOnFailureListener { it.printStackTrace() }
    }
}