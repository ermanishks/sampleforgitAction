package com.contest.gitactionapplication.repo

import com.contest.gitactionapplication.network.NetworkService
import com.contest.gitactionapplication.model.Movie

/**
 * Created by Manish Kumar
 */
class MovieRepository  constructor(
    private val movieService: NetworkService
) {

    suspend fun getMovieDetailsByName(movieName:String): Movie {
        return movieService.getMovieDetails(movieName)
    }
    companion object {
        // For Singleton instantiation
        @Volatile private var instance: MovieRepository? = null

        fun getInstance(movieService: NetworkService) =
            instance ?: synchronized(this) {
                instance ?: MovieRepository(movieService).also { instance = it }
            }
    }
}