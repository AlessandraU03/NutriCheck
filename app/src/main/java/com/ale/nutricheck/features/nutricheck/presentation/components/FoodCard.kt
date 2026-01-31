package com.ale.nutricheck.features.nutricheck.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ale.nutricheck.features.nutricheck.domain.entities.Food

@Composable
fun FoodCard(food: Food, onAddClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth().height(140.dp)) {
        Card(
            modifier = Modifier.fillMaxSize().padding(start = 40.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            ),
            border = androidx.compose.foundation.BorderStroke(
                1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.2f)
            )
        ) {
            Row(
                modifier = Modifier.padding(start = 70.dp, end = 16.dp, top = 12.dp, bottom = 12.dp).fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        food.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "${food.calories.toInt()} kcal",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Desglose de Macronutrientes usando el nuevo componente
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        MacroIndicator(label = "P", value = food.proteins, color = Color(0xFF4CAF50))
                        MacroIndicator(label = "G", value = food.fats, color = Color(0xFFFFC107))
                        MacroIndicator(label = "C", value = food.carbs, color = Color(0xFF2196F3))
                    }
                }

                IconButton(
                    onClick = onAddClick,
                    modifier = Modifier.background(
                        if (food.isHealthy) Color(0xFF4CAF50) else MaterialTheme.colorScheme.primary,
                        CircleShape
                    ).size(36.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
                }
            }
        }
        AsyncImage(
            model = food.imageUrl,
            contentDescription = null,
            modifier = Modifier.size(100.dp).align(Alignment.CenterStart).clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}
