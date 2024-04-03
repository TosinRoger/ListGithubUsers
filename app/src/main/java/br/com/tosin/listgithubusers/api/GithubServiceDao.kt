package br.com.tosin.listgithubusers.api

import br.com.tosin.listgithubusers.api.entity.UserRepoRemote
import br.com.tosin.listgithubusers.api.entity.UserRemote
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubServiceDao {

    @GET("/users")
    suspend fun getUsers(
        @Query("since") lastID: Int
    ): List<UserRemote>

    @GET("/users/{username}")
    suspend fun fetchUserByUsername(@Path("username") username: String): UserRemote


    @GET("/users/{username}/repos")
    suspend fun fetchRepositoryFromUser(@Path("username") username: String): List<UserRepoRemote>
}
