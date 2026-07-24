package edu.metrostate.ics342.mediatracker.data.network

import kotlinx.serialization.Serializable

@Serializable
data class FavoriteRequest(val mediaId: Int) {
}