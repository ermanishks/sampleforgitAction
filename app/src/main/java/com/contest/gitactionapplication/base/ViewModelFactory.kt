package com.contest.gitactionapplication.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.contest.gitactionapplication.repo.MovieRepository
import com.contest.gitactionapplication.ui.main.MainViewModel

/**
 * Created by Manish Kumar
 */
class ViewModelFactory(private val movieRepository: MovieRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = MainViewModel(movieRepository) as T
}