package br.com.tosin.listgithubusers.data.mapper

import br.com.tosin.listgithubusers.api.entity.UserRepoRemote
import br.com.tosin.listgithubusers.data.model.UserRepo

fun UserRepoRemote.asModel() = UserRepo(
    id = id,
    name = name,
    fullName = fullName,
    private = private,
    owner = owner.asModel(),
    htmlUrl = htmlUrl,
    description = description,
    fork = fork,
    createdAt = createdAt,
    updatedAt = updatedAt,
    pushedAt = pushedAt,
    homepage = homepage,
    stargazersCount = stargazersCount,
    watchersCount = watchersCount,
    language = language,
    visibility = visibility,
    forks = forks,
    watchers = watchers
)

