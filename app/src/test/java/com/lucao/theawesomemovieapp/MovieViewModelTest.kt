package com.lucao.theawesomemovieapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth.assertThat
import com.lucao.theawesomemovieapp.data.repository.MovieRepositoryImpl
import com.lucao.theawesomemovieapp.domain.model.Movie
import com.lucao.theawesomemovieapp.ui.viewmodel.MovieViewModel
import com.lucao.theawesomemovieapp.util.DataState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.justRun
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()
    private val dispatcher = UnconfinedTestDispatcher()

    private val repository = mockk<MovieRepositoryImpl>()
    private lateinit var viewModel: MovieViewModel

    private val screenStateObserver: Observer<DataState> = mockk(relaxed = true)
    private val screenStateValues = mutableListOf<DataState>()

    private val movie = mockk<Movie>()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this, relaxed = true)

        justRun { screenStateObserver.onChanged(capture(screenStateValues)) }

        coEvery { repository.getMovieData() } returns Result.failure(Throwable("Test"))
        viewModel = MovieViewModel(repository)
        viewModel.screenState.observeForever(screenStateObserver)
        screenStateValues.clear()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        viewModel.screenState.removeObserver(screenStateObserver)
        screenStateValues.clear()
    }

    @Test
    fun getData_whenRepository_hasData_shouldChangeStateToSuccess() = runBlocking {
        coEvery { repository.getMovieData() } returns Result.success(listOf(movie))

        viewModel.loadData()

        assertThat(screenStateValues).isEqualTo(listOf(DataState.Loading, DataState.Success))
    }

    @Test
    fun getData_whenRepository_hasError_shouldChangeStateToError() = runBlocking {
        coEvery { repository.getMovieData() } returns Result.failure(Throwable("Test"))

        viewModel.loadData()

        assertThat(screenStateValues).isEqualTo(listOf(DataState.Loading, DataState.Error))
    }

    @Test
    fun getData_whenRepository_hasData_shouldEmitList() = runBlocking {
        val list = listOf(movie)
        coEvery { repository.getMovieData() } returns Result.success(list)

        viewModel.loadData()

        assertThat(viewModel.listLiveData.value).isEqualTo(list)
    }

    @Test
    fun getData_whenRepository_hasError_shouldNotEmitList() = runBlocking {
        coEvery { repository.getMovieData() } returns Result.failure(Throwable("Test"))

        viewModel.loadData()

        assertThat(viewModel.listLiveData.value).isNull()
    }

}