package com.example.assignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.assignment.ui.composable.CommentScreen
import com.example.assignment.ui.composable.FeedPage
import com.example.assignment.ui.composable.destinations.Destination
import com.example.assignment.ui.theme.AssignmentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AssignmentTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AssignmentNavHost(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AssignmentNavHost(modifier: Modifier = Modifier) {
    val postIdKey = "postIdKey"
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Destination.feedPage,
        builder = {
            composable(
                route = Destination.feedPage,
                content = {
                    FeedPage(
                        toComments = { postId ->
                            navController.navigate("${Destination.commentScreen}/$postId")
                        }
                    )
                }
            )
            composable(
                route = "${Destination.commentScreen}/{$postIdKey}",
                arguments = listOf(
                    navArgument(
                        name = postIdKey,
                        builder = {
                            type = NavType.StringType
                        }
                    )
                ),
                content = { navBackStackEntry ->
                    val postId = navBackStackEntry.arguments?.getString(postIdKey)
                    CommentScreen(
                        postId = postId,
                        navigateUp = { navController.navigateUp() }
                    )
                }
            )
        }
    )
}
