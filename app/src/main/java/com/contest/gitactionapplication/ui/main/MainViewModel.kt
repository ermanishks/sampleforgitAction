package com.contest.gitactionapplication.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contest.gitactionapplication.base.Resource
import com.contest.gitactionapplication.model.Movie
import com.contest.gitactionapplication.repo.MovieRepository
import kotlinx.coroutines.launch

class MainViewModel internal constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val movieDetail = MutableLiveData<Resource<Movie>>()

    val movieInfo:LiveData<Resource<Movie>>
     get() = movieDetail

    fun searchMovie(movie: String) {
        viewModelScope.launch {
            movieDetail.postValue(Resource.loading(null))
            try {
                val movieVO = movieRepository.getMovieDetailsByName(movie)
                movieDetail.postValue(Resource.success(movieVO))
            } catch (e: Exception) {
                movieDetail.postValue(Resource.error("Something Went horribly Wrong", null))
            }
        }
    }
}
