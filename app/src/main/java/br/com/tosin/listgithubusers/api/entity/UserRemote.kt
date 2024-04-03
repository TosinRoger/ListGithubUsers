package br.com.tosin.listgithubusers.api.entity

import com.google.gson.annotations.SerializedName

data class UserRemote(
    @SerializedName("login")
    var login: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("avatar_url")
    var avatarUrl: String,
    @SerializedName("gravatar_id")
    var gravatarId: String,
    @SerializedName("url")
    var url: String,
    @SerializedName("html_url")
    var htmlUrl: String,
    @SerializedName("type")
    var type: String,
    // User Detail
    @SerializedName("location")
    var location: String? = null,
    @SerializedName("public_repos")
    var publicRepos: Int? = null,
    @SerializedName("public_gists")
    var publicGists: Int? = null,
    @SerializedName("followers")
    var followers: Int? = null,
    @SerializedName("following")
    var following: Int? = null
)
