package br.com.tosin.listgithubusers.data.model

data class User(
    val id: Int,
    val login: String,
    val avatarUrl: String? = null,
    val url: String,
    // User Detail
    val location: String? = null,
    val publicRepos: Int? = null,
    val publicGists: Int? = null,
    val followers: Int? = null,
    val following: Int? = null,
)
