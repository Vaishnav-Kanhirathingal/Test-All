package com.example.assignment.ui.composable

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import com.example.assignment.R
import com.example.assignment.data.Post
import com.example.assignment.data.PostType
import com.example.assignment.data.firebase.FirebaseFunctions
import com.example.assignment.values.CustomValues
import com.google.firebase.firestore.DocumentSnapshot

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedPage(
    toComments: (String) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { FeedPageTopBar() },
        content = {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .verticalScroll(ScrollState(0)),
                verticalArrangement = Arrangement.Center,
                content = {
                    val list = remember { mutableStateListOf<DocumentSnapshot>() }
                    LaunchedEffect(
                        key1 = list,
                        block = {
                            FirebaseFunctions.getListOfPosts { listOfDS ->
                                list.clear()
                                list.addAll(listOfDS)
                            }
                        }
                    )
                    if (list.isEmpty()) {
                        LoadingCard(modifier = Modifier.fillMaxSize())
                    } else {
                        for (i in list) {
                            FeedPost(
                                documentSnapshot = i,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(all = CustomValues.Padding.small),
                                toComments = toComments
                            )
                        }
                    }
                }
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedPageTopBar() {
    TopAppBar(
        title = { Text(text = "Feed") },
        actions = {
            IconButton(
                onClick = { TODO("Exit") },
                content = {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = null
                    )
                }
            )
            IconButton(
                onClick = {
                    FirebaseFunctions.uploadTestPostToFirebase()
//                    FirebaseFunctions.addComments()
                },
                content = {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = "Add Test objects to database"
                    )
                }
            )
        }
    )
}

@Composable
fun FeedPost(
    documentSnapshot: DocumentSnapshot,
    modifier: Modifier = Modifier,
    toComments: (String) -> Unit
) {
    val post = Post.fromDocumentSnapshot(documentSnapshot)
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
                            .clip(CircleShape)
                            .aspectRatio(1f),
                        model = post.authorImageURL,
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
                MultiMediaVideoContent(videoUrl = post.multimedia.videoFile)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = CustomValues.Padding.small),
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
                                modifier = Modifier.clickable(
                                    onClick = { toComments(documentSnapshot.id) }
                                ),
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
            .height(CustomValues.Padding.postHeight)
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
    Column(
        modifier = Modifier
            .height(CustomValues.Padding.postHeight)
            .background(Color(red = 0xF0, green = 0xF0, blue = 0xF0)),
        verticalArrangement = Arrangement.Center,
        content = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Play Audio File",
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
    )
}

@Composable
fun MultiMediaVideoContent(videoUrl: String) {
    // TODO: fix
    val sampleUrl = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"
    val context = LocalContext.current
    val exoPlayer = ExoPlayer.Builder(context).build()
    val mediaItem = MediaItem.fromUri(sampleUrl)
    exoPlayer.setMediaItem(mediaItem)

    val playerView = PlayerView(context)
    playerView.player = exoPlayer
    DisposableEffect(
        key1 = AndroidView(
            factory = { playerView },
            modifier = Modifier
                .fillMaxWidth()
                .height(CustomValues.Padding.postHeight)
        ),
        effect = {
            exoPlayer.prepare()
            exoPlayer.playWhenReady = true
            onDispose {
                exoPlayer.release()
            }
        }
    )
}

@Preview
@Composable
fun LoadingCard(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
        content = {
            Card(
                modifier = Modifier.padding(30.dp),
                content = {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .padding(30.dp)
                    )
                }
            )
        }
    )
}