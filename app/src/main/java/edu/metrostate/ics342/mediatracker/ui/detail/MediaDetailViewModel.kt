package edu.metrostate.ics342.mediatracker.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import edu.metrostate.ics342.mediatracker.data.datastore.DefaultSessionRepository
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

    fun load(mediaId: Int) {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading
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