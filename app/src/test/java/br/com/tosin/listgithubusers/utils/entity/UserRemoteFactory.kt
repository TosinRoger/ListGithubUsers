package br.com.tosin.listgithubusers.utils.entity

import br.com.tosin.listgithubusers.api.entity.UserRemote
import br.com.tosin.listgithubusers.utils.DataFactory

object UserRemoteFactory {

    fun makeFull():UserRemote {
        return UserRemote(
            login = DataFactory.randomString(),
            id = DataFactory.randomInt(),
            nodeId = DataFactory.randomString(),
            avatarUrl = DataFactory.randomString(),
            gravatarId = DataFactory.randomString(),
            url = DataFactory.randomString(),
            htmlUrl = DataFactory.randomString(),
            followersUrl = DataFactory.randomString(),
            followingUrl = DataFactory.randomString(),
            gistsUrl = DataFactory.randomString(),
            starredUrl = DataFactory.randomString(),
            subscriptionsUrl = DataFactory.randomString(),
            organizationsUrl = DataFactory.randomString(),
            reposUrl = DataFactory.randomString(),
            eventsUrl = DataFactory.randomString(),
            receivedEventsUrl = DataFactory.randomString(),
            type = DataFactory.randomString(),
            siteAdmin = DataFactory.randomBoolean()
        )
    }
}
