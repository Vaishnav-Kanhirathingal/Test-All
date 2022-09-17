package com.example.sptapi

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.sptapi.databinding.ActivityMainBinding
import com.example.sptapi.recyclers.*
import com.example.sptapi.view_model.TypiViewModel

class MainActivity : AppCompatActivity() {
    private val TAG = this::class.java.simpleName
    private val viewModel: TypiViewModel = TypiViewModel()

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        applyBinding()
    }

    private fun applyBinding() {
        val albumAdapter = AlbumAdapter()
        val commentAdapter = CommentAdapter()
        val photosAdapter = PhotosAdapter(layoutInflater = layoutInflater, context = this)
        val postsAdapter = PostsAdapter()
        val todosAdapter = TodosAdapter()
        val usersAdapter = UsersAdapter()

        viewModel.apply {
            getAlbums();getComments();getPhotos();getPosts();getTodos();getUsers()

            albumsListLive.observe(this@MainActivity) { albumAdapter.submitList(it) }
            commentsListLive.observe(this@MainActivity) { commentAdapter.submitList(it) }
            photosListLive.observe(this@MainActivity) { photosAdapter.submitList(it) }
            postsListLive.observe(this@MainActivity) { postsAdapter.submitList(it) }
            todosListLive.observe(this@MainActivity) { todosAdapter.submitList(it) }
            usersListLive.observe(this@MainActivity) { usersAdapter.submitList(it) }

            currentCallStatus.observe(this@MainActivity) {
                when (it) {
                    CallStatus.ERROR -> {
                        binding.statusDisplay.visibility = View.VISIBLE
                        binding.statusDisplay.setImageResource(R.drawable.error_24)
                    }
                    CallStatus.LOADING -> {
                        binding.statusDisplay.visibility = View.VISIBLE
                        binding.statusDisplay.setImageResource(R.drawable.error_24)
                    }
                    CallStatus.DONE -> binding.statusDisplay.visibility = View.GONE
                }
            }
        }
        binding.appBar.setOnMenuItemClickListener {
            binding.recycler.adapter = when (it.itemId) {
                R.id.albums -> {
                    viewModel.getAlbums();albumAdapter
                }
                R.id.comments -> {
                    viewModel.getComments();commentAdapter
                }
                R.id.photos -> {
                    viewModel.getPhotos();photosAdapter
                }
                R.id.posts -> {
                    viewModel.getPosts();postsAdapter
                }
                R.id.todos -> {
                    viewModel.getTodos();todosAdapter
                }
                R.id.users -> {
                    viewModel.getUsers();usersAdapter
                }
                else -> albumAdapter
            }
            return@setOnMenuItemClickListener true
        }
    }
}