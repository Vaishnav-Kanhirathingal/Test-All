package com.example.assignment.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.assignment.data.Comment
import com.example.assignment.data.firebase.FirebaseFunctions
import com.example.assignment.values.CustomValues
import com.google.firebase.firestore.DocumentSnapshot

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentScreen(
    postId: String?,
    navigateUp: () -> Unit,
) {
    val commentList = remember { mutableStateListOf<DocumentSnapshot>() }
    LaunchedEffect(
        key1 = commentList,
        block = { FirebaseFunctions.getComments { list -> commentList.addAll(list) } }
    )
    Scaffold(
        topBar = { CommentTopBar(navigateUp = navigateUp) },
        content = {
            Column(
                modifier = Modifier.padding(it),
                content = {
                    val documentSnapshot = remember { mutableStateOf<DocumentSnapshot?>(null) }
                    postId?.let {
                        FirebaseFunctions.getPost(
                            postId = postId,
                            onReceive = { ds -> documentSnapshot.value = ds }
                        )
                    }
                    if (documentSnapshot.value != null) {
                        FeedPost(
                            documentSnapshot = documentSnapshot.value!!,
                            toComments = {},
                            modifier = Modifier.padding(bottom = 60.dp)
                        )
                    }
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(CustomValues.Padding.small)
                            .background(Color(red = 0xF0, green = 0xF0, blue = 0xF0)),
                        content = {
                            items(
                                count = commentList.size,
                                itemContent = { count: Int ->
                                    Comment(comment = Comment.fromDocumentSnapshot(commentList[count]))
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
@Preview(showBackground = true)
fun CommentTopBarPrev() {
    CommentTopBar {}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentTopBar(navigateUp: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = "Comment Screen")
        },
        navigationIcon = {
            IconButton(
                onClick = navigateUp,
                content = {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            )
        }
    )
}

@Composable
fun Comment(comment: Comment) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        content = {
            val rowHeight = 50.dp
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(rowHeight),
                content = {
                    AsyncImage(
                        modifier = Modifier
                            .height(rowHeight)
                            .aspectRatio(1f),
                        model = comment.userImageURL,
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = comment.user
                    )
                }
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = comment.comment
            )
        }
    )
}