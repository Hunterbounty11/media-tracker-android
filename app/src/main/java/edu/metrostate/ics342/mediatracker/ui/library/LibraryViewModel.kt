package edu.metrostate.ics342.mediatracker.ui.library

import androidx.lifecycle.ViewModel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import edu.metrostate.ics342.mediatracker.data.FakeMediaRepository
import edu.metrostate.ics342.mediatracker.data.datastore.DefaultSessionRepository
import edu.metrostate.ics342.mediatracker.data.model.LibraryItem
import edu.metrostate.ics342.mediatracker.data.model.LibraryStatus
import edu.metrostate.ics342.mediatracker.data.network.DefaultMediaRepository
import edu.metrostate.ics342.mediatracker.data.network.PriorityRequest
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LibraryViewModel(application: Application): AndroidViewModel(application) {
    private val repo = DefaultMediaRepository(DefaultSessionRepository(application))

    private val _libraryItems = MutableStateFlow<List<LibraryItem>>(emptyList())
    val libraryItems: StateFlow<List<LibraryItem>> = _libraryItems.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

   private val _filterState = MutableStateFlow( value = LibraryStatus.WANT_TO)
    val filterState: StateFlow<LibraryStatus> = _filterState.asStateFlow()
    init {
        loadLibrary()
    }

    fun loadLibrary() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _libraryItems.value = repo.getLibrary(_filterState.value.toApiString())
            } catch (e: Exception) {
                android.util.Log.e("LibraryVM", "load failed", e)
                _libraryItems.value = emptyList()
            }
            _isLoading.value = false
        }
    }

    fun removeItem(mediaId: Int) {
        _libraryItems.value = _libraryItems.value.filter { it.mediaId != mediaId }
    }

    fun updateStatus(mediaId: Int, newStatus: LibraryStatus) {
        _libraryItems.value = _libraryItems.value.map { item ->
            if (item.mediaId == mediaId) item.copy(status = newStatus) else item
        }
    }

    fun updateFilter(status: LibraryStatus) {
        _filterState.value = status
    }

    fun addToPriorities(
        mediaId: Int,
        priority: Int,
        orderIndex: Int,
        estimatedTimeHours: Double? = null,
        notes: String? = null
    ) {
        viewModelScope.launch {
            try {
                repo.putPriority(
                    PriorityRequest(mediaId, priority, orderIndex, estimatedTimeHours, notes)
                )
            } catch (e: Exception) {
                android.util.Log.e("LibraryVM", "add to priorities failed", e)
            }
        }
    }
}
