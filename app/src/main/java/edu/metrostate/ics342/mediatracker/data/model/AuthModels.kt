package edu.metrostate.ics342.mediatracker.data.model

import android.R
import android.view.Display
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.StructureKind

@Serializable
data class CreateUserRequest(
    val email: String,
    val password: String,
    val username: String,
    val displayName: String,
    val clientId: String,
    val clientSecret: String
)

@Serializable
data class TokenRequest(
    val grantType: String,
    val email: String? = null,
    val password: String? = null,
    val refreshToken: String? = null,
    val clientId: String,
    val clientSecret: String
)

@Serializable
data class CreateUserResponse(
    val id: String,
    val username: String,
    val email: String,
    val displayName: String,
    val bio: String,
    val avatarUrl:String,
    val followerCount: R.integer,
    val followingCount: R.integer,
    val isFollowing: R.bool,
    val createdAt: String,
    )

@Serializable
data class TokenResponse(
    val accessToken:String,
    val refreshToken:String,
    val user: Object,
)