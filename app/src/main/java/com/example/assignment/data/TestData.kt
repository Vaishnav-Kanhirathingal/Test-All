package com.example.assignment.data

object TestData {
    val postList = getPostList()

    fun getPostList(): List<Post> {
        val ret = mutableListOf<Post>()
        for (i in 1..10) {
            val post = Post(
                authorName = "Author $i",
                authorImageURL = "https://example.com/author$i.jpg",
                postText = "Post $i content",
                postType = if (i % 2 == 0) PostType.MARKETING else PostType.QUESTION,
                likeCount = i * 10,
                commentCount = i * 5,
                multimedia = PostMultiMedia(
                    imageArray = if (i % 3 == 0) listOf(
                        "https://example.com/image1.jpg",
                        "https://example.com/image2.jpg"
                    ) else null,
                    audioFile = if (i % 3 == 1) "https://example.com/audio$i.mp3" else null,
                    videoFile = if (i % 3 == 2) "https://example.com/video$i.mp4" else null

                )
            )
            ret.add(post)
        }
        return ret
    }
}