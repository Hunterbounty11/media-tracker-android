package edu.metrostate.ics342.mediatracker.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import edu.metrostate.ics342.mediatracker.data.datastore.DefaultSessionRepository
import edu.metrostate.ics342.mediatracker.data.model.LibraryStatus
import edu.metrostate.ics342.mediatracker.data.model.Media
import edu.metrostate.ics342.mediatracker.data.model.Review
import edu.metrostate.ics342.mediatracker.data.network.DefaultMediaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MediaDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = DefaultMediaRepository(DefaultSessionRepository(application))

    sealed class DetailUiState {
        object Loading : DetailUiState()
        data class Success(val media: Media, val reviews: List<Review>) : DetailUiState()
        data class Error(val message: String) : DetailUiState()
    }

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    private val _inLibrary =MutableStateFlow(false)
    val inLibrary: StateFlow<Boolean> = _inLibrary.asStateFlow()

    private val _libraryPending = MutableStateFlow(false)
    val libraryPending: StateFlow<Boolean> = _libraryPending.asStateFlow()

    private val _inFavorites = MutableStateFlow(false)
    val inFavorites: StateFlow<Boolean> = _inFavorites.asStateFlow()

   private val _favoritePending = MutableStateFlow(false)
    val favoritePending: StateFlow<Boolean> = _favoritePending.asStateFlow()


    fun addFavorite(mediaId: Int) {
        if (_favoritePending.value || _inFavorites.value) return
        viewModelScope.launch {
            _favoritePending.value = true
            val ok = try {
                repo.addFavorite(mediaId)
            } catch (e: Exception) {
                android.util.Log.e("DetailVM", "add favorite failed", e)
                false
            }
            if (ok) _inFavorites.value = true
            _favoritePending.value = false
        }
    }

    fun addToLibrary(mediaId: Int) {
        if (_libraryPending.value || _inLibrary.value) return
        viewModelScope.launch {
            _libraryPending.value = true
            val ok = try {
                repo.addToLibrary(mediaId, LibraryStatus.WANT_TO.toApiString())
            } catch (e: Exception) {
                android.util.Log.e("DetailVM", "add to library failed", e)
                false
            }
            if (ok) _inLibrary.value = true
            _libraryPending.value = false
        }
    }


    fun load(mediaId: Int) {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading
            _inLibrary.value = false
            _inFavorites.value = false
            try {
                val media = repo.getMediaDetail(mediaId)
                val reviews = try {
                    repo.getReviews(mediaId)
                } catch (e: Exception) {
                    android.util.Log.e("DetailVM", "reviews failed", e)
                    emptyList()
                }
                _uiState.value = DetailUiState.Success(media, reviews)
            } catch (e: Exception) {
                android.util.Log.e("DetailVM", "load failed", e)
                _uiState.value = DetailUiState.Error("Couldn't load this item")
            }
        }
    }
}