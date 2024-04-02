package br.com.tosin.listgithubusers.api

import br.com.tosin.listgithubusers.api.entity.UserRemote
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubServiceDao {

    @GET("/users")
    suspend fun getUsers(
        @Query("since") lastID: Int
    ): List<UserRemote>
}
