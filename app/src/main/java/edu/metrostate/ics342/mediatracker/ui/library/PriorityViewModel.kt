package edu.metrostate.ics342.mediatracker.ui.priorities

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import edu.metrostate.ics342.mediatracker.data.datastore.DefaultSessionRepository
import edu.metrostate.ics342.mediatracker.data.model.Priority
import edu.metrostate.ics342.mediatracker.data.network.DefaultMediaRepository
import edu.metrostate.ics342.mediatracker.data.network.PriorityRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PrioritiesViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = DefaultMediaRepository(DefaultSessionRepository(application))

    private val _priorities = MutableStateFlow<List<Priority>>(emptyList())
    val priorities: StateFlow<List<Priority>> = _priorities.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        loadPriorities()
    }

    fun loadPriorities() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _priorities.value = repo.getPriorities().sortedBy { it.orderIndex }
            } catch (e: Exception) {
                android.util.Log.e("PrioritiesVM", "load failed", e)
                _priorities.value = emptyList()
            }
            _isLoading.value = false
        }
    }

    fun setPriority(
        mediaId: Int,
        priority: Int,
        orderIndex: Int,
        estimatedTimeHours: Double? = null,
        notes: String? = null
    ) {
        viewModelScope.launch {
            val ok = try {
                repo.putPriority(
                    PriorityRequest(mediaId, priority, orderIndex, estimatedTimeHours, notes)
                )
            } catch (e: Exception) {
                android.util.Log.e("PrioritiesVM", "put failed", e)
                false
            }
            if (ok) loadPriorities()   // refetch to reflect the change
        }
    }
}