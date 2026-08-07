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

    companion object { const val MAX_PRIORITIES = 5 }

    private val repo = DefaultMediaRepository(DefaultSessionRepository(application))

    private val _priorities = MutableStateFlow<List<Priority>>(emptyList())
    val priorities: StateFlow<List<Priority>> = _priorities.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    val isFull: Boolean get() = _priorities.value.size >= MAX_PRIORITIES

    init {
        loadPriorities()
    }

    fun loadPriorities() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                _priorities.value = repo.getPriorities().sortedBy { it.orderIndex }
            } catch (e: Exception) {
                android.util.Log.e("PrioritiesVM", "load failed", e)
                _errorMessage.value = "Couldn't load priorities. Check your connection and try again."
            }
            _isLoading.value = false
        }
    }

    fun onMove(fromIndex: Int, toIndex: Int) {
        val current = _priorities.value.toMutableList()
        if (fromIndex !in current.indices || toIndex !in current.indices) return
        val moved = current.removeAt(fromIndex)
        current.add(toIndex, moved)
        val renumbered = current.mapIndexed { index, p -> p.copy(orderIndex = index) }
        _priorities.value = renumbered
        persistOrder(renumbered)
    }

    private fun persistOrder(list: List<Priority>) {
        viewModelScope.launch {
            try {
                list.forEach { p ->
                    repo.putPriority(
                        PriorityRequest(
                            mediaId = p.mediaId,
                            priority = p.priority,
                            orderIndex = p.orderIndex,
                            estimatedTimeHours = p.estimatedTimeHours,
                            notes = p.notes
                        )
                    )
                }
            } catch (e: Exception) {
                android.util.Log.e("PrioritiesVM", "reorder persist failed", e)
                _errorMessage.value = "Reorder didn't save. Pull to refresh."
                loadPriorities()
            }
        }
    }

    fun setPriority(
        mediaId: Int,
        priority: Int,
        orderIndex: Int,
        estimatedTimeHours: Double? = null,
        notes: String? = null
    ) {
        if (isFull && _priorities.value.none { it.mediaId == mediaId }) {
            _errorMessage.value = "You can only prioritize $MAX_PRIORITIES items. Remove one first."
            return
        }
        viewModelScope.launch {
            val ok = try {
                repo.putPriority(
                    PriorityRequest(mediaId, priority, orderIndex, estimatedTimeHours, notes)
                )
            } catch (e: Exception) {
                android.util.Log.e("PrioritiesVM", "put failed", e)
                false
            }
            if (ok) loadPriorities()
        }
    }

    fun clearError() { _errorMessage.value = null }
}