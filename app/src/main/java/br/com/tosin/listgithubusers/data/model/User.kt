package br.com.tosin.listgithubusers.data.model

data class User(
    val id: Int,
    val login: String,
    val avatarUrl: String? = null,
    val url: String
)
