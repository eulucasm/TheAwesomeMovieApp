package com.lucao.theawesomemovieapp.ui.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucao.theawesomemovieapp.data.repository.MovieRepositoryImpl
import com.lucao.theawesomemovieapp.domain.model.Movie
import com.lucao.theawesomemovieapp.util.DataState
import com.lucao.theawesomemovieapp.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

@HiltViewModel
class MovieViewModel @Inject constructor(
    private var repository: MovieRepositoryImpl
) : ViewModel() {

    private val _movieLiveData = MutableLiveData<Movie?>()
    val movieLiveData: LiveData<Movie?>
        get() = _movieLiveData

    private val _listLiveData = MutableLiveData<List<Movie>?>()
    val listLiveData: LiveData<List<Movie>?>
        get() = _listLiveData

    private val _navigationToDetailsLiveData = MutableLiveData<Event<Unit>>()
    val navigationToDetailsLiveData: LiveData<Event<Unit>>
        get() = _navigationToDetailsLiveData

    private val _screenState = MutableLiveData<DataState>()
    val screenState: LiveData<DataState>
        get() = _screenState

    private val _postersLiveData = MutableLiveData<List<CarouselItem>?>()
    val postersLiveData: LiveData<List<CarouselItem>?>
        get() = _postersLiveData

    init {
        loadData()
    }

    @VisibleForTesting
    fun loadData() {
        _screenState.postValue(DataState.Loading)

        viewModelScope.launch {
            val result = repository.getMovieData()

            result.fold(
                onSuccess = {
                    it?.let {
                        _listLiveData.postValue(it)
                    }
                    _screenState.postValue(DataState.Success)

                },
                onFailure = {
                    _screenState.postValue(DataState.Error)
                },
            )
        }
    }

    private fun getMovieDetail(movie: Movie) {
        _screenState.postValue(DataState.Loading)
        viewModelScope.launch {
            val result = repository.getMovieDetail(movie)

            result.fold(
                onSuccess = {
                    _movieLiveData.postValue(it)
                    _screenState.postValue(DataState.Success)
                    _navigationToDetailsLiveData.postValue(Event(Unit))

                },
                onFailure = {
                    _screenState.postValue(DataState.Error)
                    _movieLiveData.postValue(null)
                },
            )
        }
    }

    fun getMoviePosters(movie: Movie) {
        _screenState.postValue(DataState.Loading)

        viewModelScope.launch {

            val result = repository.getMoviePosters(movie)

            result.fold(
                onSuccess = {
                    val carouselList = mutableListOf<CarouselItem>()
                    it?.let {
                        it.forEach { poster ->
                            carouselList.add(CarouselItem(poster.posterPath))
                        }.also {
                            _postersLiveData.postValue(carouselList)
                        }
                        _screenState.postValue(DataState.Success)
                    }
                },
                onFailure = {
                    _screenState.postValue(DataState.Error)
                    _postersLiveData.postValue(null)
                },
            )
        }
    }

    fun onMovieSelected(position: Int) {
        val list = _listLiveData.value
        val movie = list?.get(position)
        movie?.let {
            getMovieDetail(it)
        }
    }

}