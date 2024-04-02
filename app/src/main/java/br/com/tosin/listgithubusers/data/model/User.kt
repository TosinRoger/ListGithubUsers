package br.com.tosin.listgithubusers.data.model

data class User(
    val id: Int,
    var login: String,
    var avatarUrl: String? = null,
    var url: String
)
