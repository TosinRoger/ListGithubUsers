package br.com.tosin.listgithubusers.api

import br.com.tosin.listgithubusers.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GithubService {

    private const val BASE_URL = "https://api.github.com/"

    fun create(): GithubServiceDao {
        val retrofit = Retrofit.Builder()
        val okHttpClient = makeOkHttpClient(
            makeLoggingInterceptor(BuildConfig.DEBUG)
        )
        val gsonConverterFactory = makeGsonConverter()

        retrofit.apply {
            baseUrl(BASE_URL)
            client(okHttpClient)
            addCallAdapterFactory(CoroutineCallAdapterFactory())
            addConverterFactory(gsonConverterFactory)
        }

        return retrofit
            .build()
            .create(GithubServiceDao::class.java)

    }

    private fun makeGsonConverter(): GsonConverterFactory {
        val gson: Gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
            .create()
        return GsonConverterFactory.create(gson)
    }

    private fun makeOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.apply {
            addInterceptor(httpLoggingInterceptor)
        }

        return okHttpClient.build()
    }

    private fun makeLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }
}
