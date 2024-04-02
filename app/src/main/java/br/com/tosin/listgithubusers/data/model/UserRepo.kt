package br.com.tosin.listgithubusers.data.model

data class UserRepo(
    var id: Int,
    var name: String,
    var fullName: String,
    var private: Boolean,
    var owner: User,
    var htmlUrl: String,
    var description: String? = null,
    var fork: Boolean,
    var createdAt: String,
    var updatedAt: String,
    var pushedAt: String,
    var homepage: String? = null,
    var stargazersCount: Int,
    var watchersCount: Int,
    var language: String? = null,
    var visibility: String,
    var forks: Int,
    var watchers: Int
)
