package com.example.mspr_2025.ui.screens.animals.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mspr_2025.data.models.AnimalDataModel

@Composable
fun AnimalsScreenSuccess(animalDataModels: List<AnimalDataModel>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(animalDataModels.size) { index ->
            AnimalCard(animalDataModel = animalDataModels[index])
        }
    }
}
