package com.ale.nutricheck.features.nutricheck.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ale.nutricheck.features.nutricheck.presentation.screens.NutriCheckUiState

@Composable
fun CalorieHeader(
    uiState: NutriCheckUiState,
    onGoalChange: (String) -> Unit
) {
    val isExceeded = uiState.consumedCalories > uiState.dailyCalorieGoal && uiState.dailyCalorieGoal > 0
    
    // Animación de color de fondo
    val backgroundColor by animateColorAsState(
        targetValue = if (isExceeded) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.surfaceVariant,
        animationSpec = tween(durationMillis = 500)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Tu Meta Diaria",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = if (isExceeded) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onSurfaceVariant
            )

            OutlinedTextField(
                value = if (uiState.dailyCalorieGoal == 0.0) "" else uiState.dailyCalorieGoal.toInt().toString(),
                onValueChange = onGoalChange,
                label = { Text("Kcal permitidas") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface
                )
            )

            if (uiState.dailyCalorieGoal > 0) {
                // Animación suave de la barra de progreso
                val animatedProgress by animateFloatAsState(
                    targetValue = (uiState.consumedCalories / uiState.dailyCalorieGoal).toFloat().coerceIn(0f, 1.1f),
                    animationSpec = tween(durationMillis = 800)
                )

                LinearProgressIndicator(
                    progress = { animatedProgress.coerceAtMost(1f) },
                    modifier = Modifier.fillMaxWidth().height(12.dp).clip(CircleShape),
                    color = if (animatedProgress > 1f) Color.Red else MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)
                )

                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${uiState.consumedCalories.toInt()} / ${uiState.dailyCalorieGoal.toInt()} kcal",
                        style = MaterialTheme.typography.labelLarge,
                        color = if (isExceeded) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Bold
                    )
                    if (isExceeded) {
                        Text("¡Excedido!", color = Color.Red, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.ExtraBold)
                    }
                }
            }
        }
    }
}
