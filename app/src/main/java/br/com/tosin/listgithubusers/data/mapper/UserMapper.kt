package br.com.tosin.listgithubusers.data.mapper

import br.com.tosin.listgithubusers.api.entity.UserRemote
import br.com.tosin.listgithubusers.data.model.User

fun UserRemote.asModel() = User(
    id = id,
    login = login,
    avatarUrl = avatarUrl,
    url = url
)
