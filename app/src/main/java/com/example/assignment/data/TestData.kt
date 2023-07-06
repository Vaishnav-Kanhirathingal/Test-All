package com.example.assignment.data

object TestData {
//    val postList: MutableList<Post> = getPostList()

    fun getPostList(): MutableList<Post> {
        val ret = mutableListOf<Post>()
        for (i in 0..9) {
            val post = Post(
                authorName = "Author $i",
                authorImageURL = "https://example.com/author$i.jpg",
                postText = "Post $i content",
                postType = if (i % 2 == 0) PostType.MARKETING else PostType.QUESTION,
                likeCount = i * 10,
                commentCount = i * 5,
                multimedia = PostMultiMedia(
                    imageArray = if (i % 3 == 0) listOf(
                        "https://img.freepik.com/free-photo/space-background-realistic-starry-night-cosmos-shining-stars-milky-way-stardust-color-galaxy_1258-154643.jpg",
                        "https://images.pexels.com/photos/674010/pexels-photo-674010.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
                        "https://img.freepik.com/premium-photo/background-image-vibrant-rainbow-flag-paint-with-smudges-generative-ai_786587-5370.jpg",
                        "https://1.bp.blogspot.com/-kK7Fxm7U9o0/YN0bSIwSLvI/AAAAAAAACFk/aF4EI7XU_ashruTzTIpifBfNzb4thUivACLcBGAsYHQ/s1280/222.jpg"
                    ) else null,
                    audioFile = if (i % 3 == 1) "https://www.learningcontainer.com/wp-content/uploads/2020/02/Kalimba.mp3" else null,
                    videoFile = if (i % 3 == 2) "https://example.com/video$i.mp4" else null

                )
            )
            ret.add(post)
        }
        return ret
    }
}