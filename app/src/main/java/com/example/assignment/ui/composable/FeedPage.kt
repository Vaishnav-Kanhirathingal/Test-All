package com.example.assignment.ui.composable

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.assignment.R
import com.example.assignment.data.PostType
import com.example.assignment.data.TestData
import com.example.assignment.values.CustomValues

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedPage(
    toComments: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = {
            Column(
                modifier = Modifier.padding(it),
                content = {
                    Button(
                        modifier = Modifier.padding(it),
                        onClick = toComments,
                        content = {
                            Text(text = "Comments")
                        }
                    )
                    FeedPost()
                }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun FeedPost() {
    val post = TestData.getPostList()[0]
    Column(
        modifier = Modifier.fillMaxWidth().padding(all = CustomValues.Padding.tiny),
        content = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                content = {
                    AsyncImage(
                        modifier = Modifier
                            .padding(start = CustomValues.Padding.tiny)
                            .height(50.dp)
                            .aspectRatio(1f),
                        model = "https://imgv3.fotor.com/images/blog-cover-image/10-profile-picture-ideas-to-make-you-stand-out.jpg",
                        contentDescription = null,
                        error = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentScale = ContentScale.Crop
                    )
                    Column(
                        modifier = Modifier
                            .padding(start = CustomValues.Padding.small),
                        content = {
                            Text(text = post.authorName)
                            Text(
                                text = when (post.postType) {
                                    PostType.MARKETING -> "Marketing"
                                    PostType.QUESTION -> "Question"
                                }
                            )
                        }
                    )
                }
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = post.postText
            )
            if (post.multimedia.imageArray != null) {
                Row(
                    modifier = Modifier
                        .height(200.dp)
                        .padding(vertical = CustomValues.Padding.small)
                        .fillMaxWidth()
                        .horizontalScroll(ScrollState(0)),
                    content = {
                        for (imageUrl in post.multimedia.imageArray) {
                            AsyncImage(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(horizontal = CustomValues.Padding.tiny),
                                model = imageUrl, contentDescription = null,
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                )
            } else if (post.multimedia.audioFile != null) {
            } else if (post.multimedia.videoFile != null) {
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                content = {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.favorite_24),
                                contentDescription = null
                            )
                            Text(text = "\t${post.likeCount} likes")
                        }
                    )
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.chat_bubble_24),
                                contentDescription = null
                            )
                            Text(text = "\t${post.commentCount} comments")
                        }
                    )

                }
            )
        }
    )
}