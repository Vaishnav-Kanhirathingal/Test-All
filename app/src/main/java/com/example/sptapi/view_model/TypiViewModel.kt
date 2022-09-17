package com.example.sptapi.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sptapi.CallStatus
import com.example.sptapi.api_resource.*
import com.example.sptapi.retrofitService
import kotlinx.coroutines.launch

class MarsViewModel : ViewModel() {
    val postsListLive = MutableLiveData<List<Posts>>()
    val commentsListLive = MutableLiveData<List<Comments>>()
    val albumsListLive = MutableLiveData<List<Albums>>()
    val photosListLive = MutableLiveData<List<Photos>>()
    val todosListLive = MutableLiveData<List<Todos>>()
    val usersListLive = MutableLiveData<List<Users>>()


    var currentCallStatus = MutableLiveData<CallStatus>(CallStatus.LOADING)


    fun getPosts() {
        currentCallStatus.value = CallStatus.LOADING
        viewModelScope.launch {
            postsListLive.value = try {
                currentCallStatus.value = CallStatus.DONE
                retrofitService.getPosts()
            } catch (e: Exception) {
                setError(e);mutableListOf()
            }
        }
    }

    fun getComments() {
        currentCallStatus.value = CallStatus.LOADING
        viewModelScope.launch {
            commentsListLive.value = try {
                currentCallStatus.value = CallStatus.DONE
                retrofitService.getComments()
            } catch (e: Exception) {
                setError(e);mutableListOf()
            }
        }
    }

    fun getAlbums() {
        currentCallStatus.value = CallStatus.LOADING
        viewModelScope.launch {
            albumsListLive.value = try {
                currentCallStatus.value = CallStatus.DONE
                retrofitService.getAlbums()
            } catch (e: Exception) {
                setError(e);mutableListOf()
            }
        }
    }

    fun getPhotos() {
        currentCallStatus.value = CallStatus.LOADING
        viewModelScope.launch {
            photosListLive.value = try {
                currentCallStatus.value = CallStatus.DONE
                retrofitService.getPhotos()
            } catch (e: Exception) {
                setError(e);mutableListOf()
            }
        }
    }

    fun getTodos() {
        currentCallStatus.value = CallStatus.LOADING
        viewModelScope.launch {
            todosListLive.value = try {
                currentCallStatus.value = CallStatus.DONE
                retrofitService.getTodos()
            } catch (e: Exception) {
                setError(e);mutableListOf()
            }
        }
    }

    fun getUsers() {
        currentCallStatus.value = CallStatus.LOADING
        viewModelScope.launch {
            usersListLive.value = try {
                currentCallStatus.value = CallStatus.DONE
                retrofitService.getUsers()
            } catch (e: Exception) {
                setError(e);mutableListOf()
            }
        }
    }

    private fun setError(e: Exception): MutableList<Any> {
        e.printStackTrace()
        currentCallStatus.value = CallStatus.ERROR
        return mutableListOf()
    }
}