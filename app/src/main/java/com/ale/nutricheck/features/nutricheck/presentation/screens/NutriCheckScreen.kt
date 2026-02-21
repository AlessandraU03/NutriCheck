package com.ale.nutricheck.features.nutricheck.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ale.nutricheck.features.nutricheck.presentation.viewmodel.FoodViewModel
import com.ale.nutricheck.features.nutricheck.presentation.components.FoodCard
import com.ale.nutricheck.features.nutricheck.presentation.components.CalorieHeader

@Composable
fun NutriCheckScreen(viewModel: FoodViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.snackbarMessage) {
        uiState.snackbarMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearSnackbar()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize().padding(16.dp)) {

            CalorieHeader(
                uiState = uiState,
                onGoalChange = { viewModel.onGoalEntered(it) }
            )

            OutlinedTextField(
                value = uiState.searchText,
                onValueChange = { viewModel.onSearchTextChange(it) },
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                placeholder = { Text("Busca alimentos de tu preferencia (jugo, pan, etc.)") },
                shape = RoundedCornerShape(16.dp),
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    viewModel.searchFoods(uiState.searchText)
                    focusManager.clearFocus()
                }),
                singleLine = true
            )

            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    uiState.isLoading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                    uiState.error != null -> {
                        Column(
                            modifier = Modifier.align(Alignment.Center).padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = null,
                                tint = Color.Red,
                                modifier = Modifier.size(48.dp)
                            )
                            Text(
                                text = "Error: ${uiState.error}",
                                color = Color.Red,
                                textAlign = TextAlign.Center
                            )
                            Button(onClick = { viewModel.searchFoods(uiState.searchText) }) {
                                Text("Reintentar")
                            }
                        }
                    }
                    uiState.foods.isEmpty() -> {
                        Text(
                            text = if (uiState.searchText.isEmpty()) "Busca algo para empezar"
                            else "No se encontraron resultados",
                            modifier = Modifier.align(Alignment.Center),
                            color = Color.Gray
                        )
                    }
                    else -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            contentPadding = PaddingValues(bottom = 16.dp)
                        ) {
                            items(uiState.foods) { food ->
                                FoodCard(
                                    food = food,
                                    onAddClick = { viewModel.addFoodToIntake(food) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}