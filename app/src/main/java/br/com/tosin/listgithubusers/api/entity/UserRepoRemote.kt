package br.com.tosin.listgithubusers.api.entity

import com.google.gson.annotations.SerializedName
import java.util.Date

data class UserRepoRemote(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("full_name")
    var fullName: String,
    @SerializedName("private")
    var private: Boolean,
    @SerializedName("owner")
    var owner: UserRemote,
    @SerializedName("html_url")
    var htmlUrl: String,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("fork")
    var fork: Boolean,
    @SerializedName("created_at")
    var createdAt: Date,
    @SerializedName("updated_at")
    var updatedAt: Date,
    @SerializedName("pushed_at")
    var pushedAt: Date,
    @SerializedName("homepage")
    var homepage: String? = null,
    @SerializedName("stargazers_count")
    var stargazersCount: Int,
    @SerializedName("watchers_count")
    var watchersCount: Int,
    @SerializedName("language")
    var language: String? = null,
    @SerializedName("visibility")
    var visibility: String,
    @SerializedName("forks")
    var forks: Int,
    @SerializedName("watchers")
    var watchers: Int
)
