package com.example.sptapi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sptapi.databinding.ActivityMainBinding
import com.example.sptapi.recyclers.AlbumAdapter
import com.example.sptapi.recyclers.CommentAdapter
import com.example.sptapi.recyclers.PhotosAdapter
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
        val photosAdapter = PhotosAdapter(layoutInflater = layoutInflater, context = this)
        val commentAdapter = CommentAdapter()

        viewModel.apply {
            getAlbums();getComments();getPhotos();getPosts();getTodos();getUsers()

            albumsListLive.observe(this@MainActivity) { albumAdapter.submitList(it) }
            photosListLive.observe(this@MainActivity) { photosAdapter.submitList(it) }
            commentsListLive.observe(this@MainActivity) { commentAdapter.submitList(it) }
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
                    viewModel.getPosts();albumAdapter
                }
                R.id.todos -> {
                    viewModel.getTodos();albumAdapter
                }
                R.id.users -> {
                    viewModel.getUsers();albumAdapter
                }
                else -> albumAdapter
            }
            return@setOnMenuItemClickListener true
        }
    }
}