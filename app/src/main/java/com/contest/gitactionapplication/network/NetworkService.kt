package com.contest.gitactionapplication.network

import android.content.Context
import android.util.Log
import com.contest.gitactionapplication.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Manish Kumar
 */
class NetworkService constructor(context: Context){
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://imdb-internet-movie-database-unofficial.p.rapidapi.com/")
        .client(CustomOkhttpClient.getOkhttpClient(context))
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val imdbMovieService = retrofit.create(ImdbMovieService::class.java)

    suspend fun getMovieDetails(movieName: String): Movie = withContext(Dispatchers.Default) {
        Log.d("MANISH","Current Thread ${Thread.currentThread().name}")
       imdbMovieService.searchMovieByTitle(movieName)
    }

    interface ImdbMovieService {
        @GET("/film/{title}")
        suspend fun searchMovieByTitle(@Path("title")  movieName:String) : Movie
    }
}