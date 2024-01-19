package com.lucao.theawesomemovieapp

import android.provider.ContactsContract.Data
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucao.theawesomemovieapp.placeholder.PlaceholderContent
import com.lucao.theawesomemovieapp.placeholder.PlaceholderContent.PlaceholderItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    private val movieDetails = MovieData("Titulo", "Nota", "Descriçao")

    //DETALHES
    private val _movieDetailsLiveData = MutableLiveData<MovieData>()
    val movieDetailsLiveData: LiveData<MovieData> get() = _movieDetailsLiveData

    //LISTA
    private val _movieListLiveData = MutableLiveData<MutableList<PlaceholderItem>>()
    val movieListLiveData: LiveData<MutableList<PlaceholderItem>> get() = _movieListLiveData

    private val _viewDataStateLiveData = MutableLiveData<DataState>()
    val viewDataStateLiveData: LiveData<DataState> get() = _viewDataStateLiveData

    private val _navigationToMovieDetailsLiveData = MutableLiveData<Unit>()
    val navigationToMovieDetailsLiveData get() = _navigationToMovieDetailsLiveData

    init {
        _movieListLiveData.postValue(PlaceholderContent.ITEMS)
    }

    fun loadData(){
        _viewDataStateLiveData.postValue(DataState.Loading)

        viewModelScope.launch {
            delay(4000)

            val success = true

            if(success){
                val movieData = movieDetails
                _movieDetailsLiveData.postValue(movieData)
                _viewDataStateLiveData.postValue(DataState.Success)
            }else {
                _viewDataStateLiveData.postValue(DataState.Error)
            }
        }
    }

    fun onMovieSelected(position: Int) {
        _movieDetailsLiveData.postValue(movieDetails)
        _navigationToMovieDetailsLiveData.postValue(Unit)
    }

}