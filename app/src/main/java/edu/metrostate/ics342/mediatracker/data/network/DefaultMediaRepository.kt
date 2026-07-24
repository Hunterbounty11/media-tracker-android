package edu.metrostate.ics342.mediatracker.data.network

import edu.metrostate.ics342.mediatracker.data.SessionRepository
import edu.metrostate.ics342.mediatracker.data.model.LibraryItem
import edu.metrostate.ics342.mediatracker.data.model.Media
import edu.metrostate.ics342.mediatracker.data.model.Review

data class MediaPage(
    val items: List<Media>,
    val nextCursor: String?,
    val hasMore: Boolean
)

class DefaultMediaRepository(sessionRepository: SessionRepository) {

    private val api = RetrofitInstance.mediaApiService(sessionRepository)

    suspend fun search(query: String, type: String?, after: String?): MediaPage {
        val response = api.searchMedia(
            query = query.ifBlank { null },
            type  = type?.ifBlank { null },
            after = after
        )
        val items      = response.body() ?: emptyList()
        val nextCursor = response.headers()["X-Next-Cursor"]
        val hasMore    = response.headers()["X-Has-More"] == "true"
        return MediaPage(items, nextCursor, hasMore)
    }
    suspend fun getMediaDetail(id: Int): Media = api.getMediaDetail(id)
    suspend fun getReviews(mediaId: Int): List<Review> =
        api.getReviews(mediaId).body() ?: emptyList()

    suspend fun addToLibrary(mediaId: Int, status: String = "want_to"): Boolean {
        val response = api.addToLibrary(LibraryRequest(mediaId, status))
        return response.isSuccessful || response.code() == 409
    }
    suspend fun getLibrary(status: String? = null): List<LibraryItem> {
        val response = api.getLibrary(status)
        return response.body() ?: emptyList()
    }

    suspend fun addFavorite(mediaId: Int): Boolean {
        val response = api.addFavorite(FavoriteRequest(mediaId))
        return response.isSuccessful || response.code() == 409
    }


}
