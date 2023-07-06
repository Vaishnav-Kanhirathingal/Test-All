package com.example.assignment.data

import kotlin.random.Random

object TestData {
//    val postList: MutableList<Post> = getPostList()

    fun getPostList(): MutableList<Post> {
        val listOfAudio = listOf(
            "https://www.learningcontainer.com/wp-content/uploads/2020/02/Kalimba.mp3",
            "https://file-examples.com/storage/fede3f30f864a1f979d2bf0/2017/11/file_example_MP3_700KB.mp3",
            "https://download.samplelib.com/mp3/sample-3s.mp3",
            "https://download.samplelib.com/mp3/sample-15s.mp3"
        )

        val listOfImages= listOf<String>(
            "https://img.freepik.com/free-photo/space-background-realistic-starry-night-cosmos-shining-stars-milky-way-stardust-color-galaxy_1258-154643.jpg",
            "https://images.pexels.com/photos/674010/pexels-photo-674010.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
            "https://img.freepik.com/premium-photo/background-image-vibrant-rainbow-flag-paint-with-smudges-generative-ai_786587-5370.jpg",
            "https://1.bp.blogspot.com/-kK7Fxm7U9o0/YN0bSIwSLvI/AAAAAAAACFk/aF4EI7XU_ashruTzTIpifBfNzb4thUivACLcBGAsYHQ/s1280/222.jpg",
            "https://e1.pxfuel.com/desktop-wallpaper/472/325/desktop-wallpaper-nature-for-android-group-3d-nature-for-mobile.jpg",
            "https://img.freepik.com/free-photo/blossom-floral-bouquet-decoration-colorful-beautiful-flowers-background-garden-flowers-plant-pattern-wallpapers-greeting-cards-postcards-design-wedding-invites_90220-1103.jpg?w=2000",
            "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/ef3a4158-e0e0-418a-9e74-d273edb3a686/dg02v46-c7d466d6-ef1d-4991-9cfa-3ed147d2d780.png/v1/fill/w_622,h_350,q_70,strp/sunset_coast_by_wopgnop_dg02v46-350t.jpg?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7ImhlaWdodCI6Ijw9NzIwIiwicGF0aCI6IlwvZlwvZWYzYTQxNTgtZTBlMC00MThhLTllNzQtZDI3M2VkYjNhNjg2XC9kZzAydjQ2LWM3ZDQ2NmQ2LWVmMWQtNDk5MS05Y2ZhLTNlZDE0N2QyZDc4MC5wbmciLCJ3aWR0aCI6Ijw9MTI4MCJ9XV0sImF1ZCI6WyJ1cm46c2VydmljZTppbWFnZS5vcGVyYXRpb25zIl19.CTqBememV5ebizN9KXImGG75YSpvv9htI7YFRTtsbG8"
        )



        val ret = mutableListOf<Post>()
        for (i in 0..10) {
            val post = Post(
                authorName = "Author $i",
                authorImageURL = "https://imgv3.fotor.com/images/blog-cover-image/10-profile-picture-ideas-to-make-you-stand-out.jpg",
                postText = "Post $i content",
                postType = if (i % 2 == 0) PostType.MARKETING else PostType.QUESTION,
                likeCount = i * 10,
                commentCount = i * 5,
                multimedia = PostMultiMedia(
                    imageArray = if (i % 3 == 0) listOf(
                        listOfImages.random(),
                        listOfImages.random(),
                        listOfImages.random(),
                        listOfImages.random()
                    ) else null,
                    audioFile = if (i % 3 == 1) listOfAudio.random() else null,
                    videoFile = if (i % 3 == 2) "https://example.com/video$i.mp4" else null

                )
            )
            ret.add(post)
        }
        return ret
    }
}