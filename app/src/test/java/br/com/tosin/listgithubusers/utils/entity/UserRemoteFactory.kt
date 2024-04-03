package br.com.tosin.listgithubusers.utils.entity

import br.com.tosin.listgithubusers.api.entity.UserRemote
import br.com.tosin.listgithubusers.utils.DataFactory

object UserRemoteFactory {

    fun makeFull():UserRemote {
        return UserRemote(
            login = DataFactory.randomString(),
            id = DataFactory.randomInt(),
            avatarUrl = DataFactory.randomString(),
            gravatarId = DataFactory.randomString(),
            url = DataFactory.randomString(),
            htmlUrl = DataFactory.randomString(),
            type = DataFactory.randomString()
        )
    }
}
