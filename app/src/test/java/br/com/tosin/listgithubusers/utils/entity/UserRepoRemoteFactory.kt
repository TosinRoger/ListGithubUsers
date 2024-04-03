package br.com.tosin.listgithubusers.utils.entity

import br.com.tosin.listgithubusers.api.entity.UserRepoRemote
import br.com.tosin.listgithubusers.utils.DataFactory

object UserRepoRemoteFactory {

    fun makeFull() = UserRepoRemote(
        id = DataFactory.randomInt(),
        name = DataFactory.randomString(),
        fullName = DataFactory.randomString(),
        private = DataFactory.randomBoolean(),
        owner = UserRemoteFactory.makeFull(),
        htmlUrl = DataFactory.randomString(),
        description = DataFactory.randomString(),
        forks = DataFactory.randomInt(),
        fork = DataFactory.randomBoolean(),
        createdAt = DataFactory.randomDate(),
        updatedAt = DataFactory.randomDate(),
        pushedAt = DataFactory.randomDate(),
        homepage = DataFactory.randomString(),
        stargazersCount = DataFactory.randomInt(),
        watchersCount = DataFactory.randomInt(),
        language = DataFactory.randomString(),
        visibility = DataFactory.randomString(),
        watchers = DataFactory.randomInt()
    )
}
