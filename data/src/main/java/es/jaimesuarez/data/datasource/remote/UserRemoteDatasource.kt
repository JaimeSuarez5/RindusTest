package es.jaimesuarez.data.datasource.remote

import es.jaimesuarez.domain.util.Result
import es.jaimesuarez.data.model.UserRemote
import es.jaimesuarez.data.datasource.remote.service.UserService
import es.jaimesuarez.data.extensions.perform

class UserRemoteDatasource(
    private val userService: UserService
) {

    suspend fun getUser(): Result<List<UserRemote>> {
        return userService.getUsers().perform()
    }
}
