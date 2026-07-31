package edu.metrostate.ics342.mediatracker.ui.priorities

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import edu.metrostate.ics342.mediatracker.data.FakeMediaRepository
import edu.metrostate.ics342.mediatracker.data.model.Priority

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrioritiesScreen(
    priorities: List<Priority>,
    onNavigateBack: () -> Unit,
    onMediaClick: (Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Priorities") },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "Back")
                }
            }
        )

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(priorities.sortedBy { it.orderIndex }, key = { it.mediaId }) { item ->
                PriorityCard(item = item, onClick = { onMediaClick(item.mediaId) })
            }
        }
    }
}

@Composable
private fun PriorityCard(item: Priority, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(64.dp, 90.dp).clip(RoundedCornerShape(6.dp)),
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

            Column(modifier = Modifier.weight(1f)) {
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

@Preview(showBackground = true)
@Composable
private fun PrioritiesScreenPreview() {
    PrioritiesScreen(
        priorities = FakeMediaRepository.priorities,
        onNavigateBack = {},
        onMediaClick = {}
    )
}