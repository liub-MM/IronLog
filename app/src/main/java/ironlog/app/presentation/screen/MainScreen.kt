package ironlog.app.presentation.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ironlog.app.presentation.navigation.IronLogBottomNavigation

@Composable
fun MainScreen() {
    var workoutText by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Scaffold(

        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {

            Text(
                text = "Привіт! \uD83E\uDDBE",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Вівторок, 12 травня",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
            )

             ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    TextField(
                        value = workoutText,
                        onValueChange = { workoutText = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = 120.dp),
                        placeholder = {
                            Text(
                                "Опиши своє тренування...\nНаприклад: Жим лежачи 100кг 8х3",
                                color = Color.Gray
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        enabled = !isLoading
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { /* TODO: Голосовий ввід */ }) {
                            Icon(
                                imageVector = Icons.Default.Mic,
                                contentDescription = "Голосовий ввід",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }


                        Button(
                            onClick = {
                                if (workoutText.isNotBlank()) {
                                    isLoading = true
                                }
                            },
                            enabled = !isLoading && workoutText.isNotBlank(),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            if (isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Icon(Icons.Default.AutoAwesome, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Проаналізувати")
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))


            Text(
                text = "Швидкі шаблони",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            val chips = listOf("Груди + Трицепс", "День ніг", "Спина + Біцепс")
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(chips) { chipText ->
                    SuggestionChip(
                        onClick = { workoutText = chipText },
                        label = { Text(chipText) }
                    )
                }
            }
        }
    }
}
