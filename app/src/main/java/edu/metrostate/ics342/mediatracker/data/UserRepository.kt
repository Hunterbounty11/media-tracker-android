package edu.metrostate.ics342.mediatracker.data

interface UserRepository {
    suspend fun register(
        email: String,
        password: String,
        username: String,
        displayName: String
    ): RegisterResult
}