package com.example.assignment.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedPage(
    toComments: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = {
            Button(
                modifier = Modifier.padding(it),
                onClick = toComments,
                content = {
                    Text(text = "Comments")
                }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun FeedPost() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        content = {

        }
    )
}