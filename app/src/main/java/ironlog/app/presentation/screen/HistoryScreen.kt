package ironlog.app.presentation.screen
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import ironlog.app.presentation.viewmodel.HistoryViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val historyItems = viewModel.pagedWorkouts.collectAsLazyPagingItems()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Історія тренувань \uD83D\uDCCB",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {

                items(historyItems.itemCount) { index ->
                    val workout = historyItems[index]

                    if (workout != null) {

                        val dateFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("uk", "UA"))
                        val dateString = dateFormat.format(Date(workout.dateTimestamp))

                        val summary = workout.exercises.joinToString(", ") { it.name }

                        val finalSummary = if (summary.isNotEmpty()) summary else "Порожнє тренування"

                        var totalTonnage = 0.0f
                        for (exercise in workout.exercises) {
                            for (set in exercise.sets) {
                                totalTonnage += (set.weight * set.reps)
                            }
                        }

                        WorkoutHistoryCard(
                            date = dateString,
                            summary = finalSummary,
                            tonnage = "${totalTonnage.toInt()} кг"
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun WorkoutHistoryCard(date: String, summary: String, tonnage: String) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.FitnessCenter,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(12.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = date,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = summary,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    maxLines = 1
                )
                Text(
                    text = "Тоннаж: $tonnage",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 4.dp),
                    fontWeight = FontWeight.SemiBold
                )
            }

            IconButton(onClick = {  }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Меню", tint = Color.Gray)
            }
        }
    }
}