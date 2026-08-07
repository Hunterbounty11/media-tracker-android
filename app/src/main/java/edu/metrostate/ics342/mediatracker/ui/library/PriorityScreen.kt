package edu.metrostate.ics342.mediatracker.ui.priorities

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.DragIndicator
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import edu.metrostate.ics342.mediatracker.data.model.Media
import edu.metrostate.ics342.mediatracker.data.model.Priority
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrioritiesScreen(
    priorities: List<Priority>,
    isLoading: Boolean,
    errorMessage: String?,
    onMove: (from: Int, to: Int) -> Unit,
    onNavigateBack: () -> Unit,
    onMediaClick: (Int) -> Unit,
    onDismissError: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            onDismissError()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Priorities") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Box(Modifier.fillMaxSize().padding(innerPadding)) {
            when {
                isLoading && priorities.isEmpty() ->
                    CircularProgressIndicator(Modifier.align(Alignment.Center))

                priorities.isEmpty() ->
                    Text(
                        "No priorities set — mark a 'Want To' item as a priority to see it here.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.align(Alignment.Center).padding(32.dp)
                    )

                else ->
                    ReorderableList(
                        items = priorities,
                        onMove = onMove,
                        onMediaClick = onMediaClick
                    )
            }
        }
    }
}

@Composable
private fun ReorderableList(
    items: List<Priority>,
    onMove: (Int, Int) -> Unit,
    onMediaClick: (Int) -> Unit
) {
    val listState = rememberLazyListState()
    val haptics = LocalHapticFeedback.current
    val density = LocalDensity.current
    val rowHeightPx = with(density) { 106.dp.toPx() }

    var draggingIndex by remember { mutableStateOf<Int?>(null) }
    var dragOffsetY by remember { mutableFloatStateOf(0f) }

    Column(Modifier.fillMaxSize()) {
        Text(
            "Drag to reorder",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(start = 16.dp, top = 12.dp, bottom = 4.dp)
        )

        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(items, key = { _, item -> item.mediaId }) { index, item ->
                val isDragging = index == draggingIndex
                PriorityCard(
                    item = item,
                    onClick = { if (draggingIndex == null) onMediaClick(item.mediaId) },
                    dragHandleModifier = Modifier.pointerInput(item.mediaId) {
                        detectDragGesturesAfterLongPress(
                            onDragStart = {
                                draggingIndex = index
                                dragOffsetY = 0f
                                haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                dragOffsetY += dragAmount.y
                                val from = draggingIndex ?: return@detectDragGesturesAfterLongPress
                                val target = (from + (dragOffsetY / rowHeightPx).roundToInt())
                                    .coerceIn(0, items.lastIndex)
                                if (target != from) {
                                    onMove(from, target)
                                    draggingIndex = target
                                    dragOffsetY -= (target - from) * rowHeightPx
                                }
                            },
                            onDragEnd = { draggingIndex = null; dragOffsetY = 0f },
                            onDragCancel = { draggingIndex = null; dragOffsetY = 0f }
                        )
                    },
                    modifier = Modifier
                        .zIndex(if (isDragging) 1f else 0f)
                        .graphicsLayer { translationY = if (isDragging) dragOffsetY else 0f }
                        .then(if (isDragging) Modifier.shadow(8.dp, RoundedCornerShape(12.dp)) else Modifier)
                )
            }
        }
    }
}

@Composable
private fun PriorityCard(
    item: Priority,
    onClick: () -> Unit,
    dragHandleModifier: Modifier,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {

            // Left drag handle (6-dot grip)
            Icon(
                Icons.Outlined.DragIndicator,
                contentDescription = "Drag to reorder",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = dragHandleModifier.padding(end = 8.dp)
            )

            Box(
                Modifier.size(64.dp, 90.dp).clip(RoundedCornerShape(6.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (item.media.coverUrl != null) {
                    AsyncImage(
                        model = item.media.coverUrl,
                        contentDescription = item.media.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                when (item.media.mediaType) {
                                    "book" -> "📖"; "movie" -> "🎬"; "show" -> "📺"
                                    else -> "?"
                                },
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.width(12.dp))

            Column(Modifier.weight(1f)) {
                Text(
                    item.media.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2
                )
                Spacer(Modifier.height(4.dp))
                SuggestionChip(
                    onClick = {},
                    label = { Text(priorityLabel(item.priority), style = MaterialTheme.typography.labelSmall) }
                )
                if (item.estimatedTimeHours != null || !item.notes.isNullOrBlank()) {
                    Spacer(Modifier.height(4.dp))
                    Text(
                        buildString {
                            item.estimatedTimeHours?.let { append("Est. ${formatHours(it)} hours") }
                            if (item.estimatedTimeHours != null && !item.notes.isNullOrBlank()) append(" · ")
                            item.notes?.let { if (it.isNotBlank()) append("\"$it\"") }
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

private fun priorityLabel(priority: Int): String = when (priority) {
    1 -> "High Priority"
    2 -> "Medium Priority"
    else -> "Low Priority"
}

private fun formatHours(hours: Double): String =
    if (hours == hours.toLong().toDouble()) hours.toLong().toString() else hours.toString()




private fun previewMedia(id: Int, title: String, type: String) = Media(
    id = id,
    mediaType = type,
    title = title,
    author = null,
    director = null,
    creator = null,
    network = null,
    coverUrl = null,
    publishedYear = 2024,
    averageRating = 0F,
    ratingCount = 0,
    genres = emptyList()
)

private val previewPriorities = listOf(
    Priority(mediaId = 1, priority = 1, orderIndex = 0, estimatedTimeHours = 6.0, notes = "Friend recommended", media = previewMedia(1, "Dune", "book")),
    Priority(mediaId = 2, priority = 2, orderIndex = 1, estimatedTimeHours = 2.5, notes = "For movie night", media = previewMedia(2, "Inception", "movie")),
    Priority(mediaId = 3, priority = 3, orderIndex = 2, estimatedTimeHours = null, notes = null, media = previewMedia(3, "Severance", "show"))
)

@Preview(showBackground = true, name = "Priorities — populated")
@Composable
private fun PrioritiesPopulatedPreview() {
    PrioritiesScreen(
        priorities = previewPriorities,
        isLoading = false,
        errorMessage = null,
        onMove = { _, _ -> },
        onNavigateBack = {},
        onMediaClick = {},
        onDismissError = {}
    )
}

@Preview(showBackground = true, name = "Priorities — empty")
@Composable
private fun PrioritiesEmptyPreview() {
    PrioritiesScreen(
        priorities = emptyList(),
        isLoading = false,
        errorMessage = null,
        onMove = { _, _ -> },
        onNavigateBack = {},
        onMediaClick = {},
        onDismissError = {}
    )
}

@Preview(showBackground = true, name = "Priorities — loading")
@Composable
private fun PrioritiesLoadingPreview() {
    PrioritiesScreen(
        priorities = emptyList(),
        isLoading = true,
        errorMessage = null,
        onMove = { _, _ -> },
        onNavigateBack = {},
        onMediaClick = {},
        onDismissError = {}
    )
}