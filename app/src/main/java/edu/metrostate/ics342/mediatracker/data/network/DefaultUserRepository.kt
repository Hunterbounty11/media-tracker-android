package edu.metrostate.ics342.mediatracker.data.network

import edu.metrostate.ics342.mediatracker.BuildConfig
import edu.metrostate.ics342.mediatracker.data.ApiService
import edu.metrostate.ics342.mediatracker.data.RegisterResult
import edu.metrostate.ics342.mediatracker.data.UserRepository
import edu.metrostate.ics342.mediatracker.data.model.CreateUserRequest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.io.IOException

class DefaultUserRepository(
    private val api: ApiService = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(
            Json.asConverterFactory(
                "application/json; charset=utf-8".toMediaType()))
        .build()
        .create(ApiService::class.java)
) : UserRepository {

    override suspend fun register(
        email: String,
        password: String,
        username: String,
        displayName: String
    ): RegisterResult {
        return try {
            val request = CreateUserRequest(
                email = email,
                password = password,
                username = username,
                displayName = displayName,
                clientId = BuildConfig.CLIENT_ID,
                clientSecret = BuildConfig.CLIENT_SECRET
            )
            api.createUser(request)
            RegisterResult.Success
        } catch (e: HttpException) {
            if (e.code() == 409) {
                RegisterResult.Conflict
            } else {
                RegisterResult.UnknownError
            }
        } catch (e: IOException) {
            RegisterResult.NetworkError
        } catch (e: Exception) {
            RegisterResult.UnknownError
        }
    }
}