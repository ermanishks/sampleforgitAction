package com.contest.gitactionapplication.network

import android.content.Context
import com.contest.gitactionapplication.AppUtils
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * Created by Manish Kumar
 */
// singleton
// with cache support
object CustomOkhttpClient {

    private val readTimeOut = 30L // in seconds
    private val connectTimeOut = 30L // in seconds
    private val api_key = "91aa27e5ebmsh6f596b78515b5a3p1da929jsn0b9134c05ad9"
    private val api_host = "imdb-internet-movie-database-unofficial.p.rapidapi.com"

    val cacheSize = (5 * 1024 * 1024).toLong()


    fun getOkhttpClient(context: Context): OkHttpClient {
        val interceptors = arrayListOf<Interceptor>()
        val loggingInterceptorBody = HttpLoggingInterceptor()
        loggingInterceptorBody.level = HttpLoggingInterceptor.Level.BASIC

        val headerInterceptor = object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                requestBuilder.header("x-rapidapi-host", api_host)
                requestBuilder.header("x-rapidapi-key", api_key)

                val request = requestBuilder.build()
                return chain.proceed(request)
            }
        }
        val cacheInterceptor = object :Interceptor{
            override fun intercept(chain: Interceptor.Chain): Response {
                var request = chain.request()
                request = if (AppUtils.hasNetwork(context)!!) {
                    /*
                      *  If there is Internet, get the cache that was stored 5 seconds ago.
                      *  If the cache is older than 5 seconds, then discard it,
                      *  and indicate an error in fetching the response.
                      *  The 'max-age' attribute is responsible for this behavior.
                      */
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                }
                else {
                    /*
                          *  If there is no Internet, get the cache that was stored 7 days ago.
                          *  If the cache is older than 7 days, then discard it,
                          *  and indicate an error in fetching the response.
                          *  The 'max-stale' attribute is responsible for this behavior.
                          *  The 'only-if-cached' attribute indicates to not retrieve new data; fetch the cache only instead.
                          */
                    request.newBuilder().header(
                        "Cache-Control",
                        "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                    ).build()
                }
                return chain.proceed(request)
            }
        }
        interceptors.add(cacheInterceptor)
        interceptors.add(headerInterceptor)
        interceptors.add(loggingInterceptorBody)

        val clientBuilder = OkHttpClient.Builder()
            .readTimeout(readTimeOut, TimeUnit.SECONDS)
            .connectTimeout(connectTimeOut, TimeUnit.SECONDS)

        interceptors.forEach { interceptor ->
            clientBuilder.addInterceptor(interceptor)
        }
        val myCache = Cache(context.cacheDir, cacheSize)
        clientBuilder.cache(myCache)

        return clientBuilder.build()
    }
}