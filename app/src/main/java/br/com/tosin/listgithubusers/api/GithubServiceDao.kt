package br.com.tosin.listgithubusers.api

import br.com.tosin.listgithubusers.api.entity.UserRemote
import retrofit2.http.GET

interface GithubServiceDao {

    @GET("/users")
    suspend fun getUsers(): List<UserRemote>

}
