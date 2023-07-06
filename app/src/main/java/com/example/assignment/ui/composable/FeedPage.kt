package com.example.assignment.ui.composable

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.assignment.R
import com.example.assignment.data.Post
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
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .verticalScroll(ScrollState(0)),
                content = {
                    for (i in TestData.getPostList()) {
                        FeedPost(
                            post = i, modifier = Modifier
                                .fillMaxWidth()
                                .padding(all = CustomValues.Padding.small)
                        )
                    }
                }
            )
        }
    )
}

@Composable
fun FeedPost(post: Post, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        content = {
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = CustomValues.Padding.small)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
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
                MultiMediaImageArrayContent(
                    imageArray = post.multimedia.imageArray,
                    modifier = Modifier.padding(vertical = CustomValues.Padding.small)
                )
            } else if (post.multimedia.audioFile != null) {
                MultiMediaAudioContent(audioUrl = post.multimedia.audioFile)
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

@Composable
fun MultiMediaImageArrayContent(imageArray: List<String>, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .height(200.dp)
            .fillMaxWidth()
            .horizontalScroll(ScrollState(0)),
        content = {
            for (imageUrl in imageArray) {
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
}

@Composable
fun MultiMediaAudioContent(audioUrl: String) {
    val context = LocalContext.current
    val mediaPlayer = MediaPlayer()
    try {
        mediaPlayer.setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_UNKNOWN)
                .build()
        )
        mediaPlayer.setDataSource(context, Uri.parse(audioUrl))
        mediaPlayer.prepare()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    val playing = remember { mutableStateOf(false) }
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "Audio",
        fontSize = CustomValues.FontSize.Big,
        textAlign = TextAlign.Center
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        content = {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(bottom = CustomValues.Padding.small)
                    .clickable {
                        try {
                            if (playing.value) {
                                mediaPlayer.pause()
                            } else {
                                mediaPlayer.start()
                            }
                            playing.value = !playing.value
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    },
                content = {
                    Row(
                        modifier = Modifier
                            .padding(CustomValues.Padding.small)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        content = {
                            Icon(
                                painter = painterResource(if (playing.value) R.drawable.pause_24 else R.drawable.play_24),
                                contentDescription = null
                            )
                        }
                    )
                }
            )
        }
    )
}

@Composable
fun MultiMediaVideoContent(videoUrl: String) {
}