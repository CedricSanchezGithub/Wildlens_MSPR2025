package com.example.mspr_2025.ui.screens.animals.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mspr_2025.data.models.AnimalDataModel

@Composable
fun AnimalCard(animalDataModel: AnimalDataModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = animalDataModel.frenchName, style = MaterialTheme.typography.titleLarge)
            Text(text = animalDataModel.latinName, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.height(8.dp))
            Text(text = animalDataModel.description, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(4.dp))
            Text(text = "Localisation : ${animalDataModel.location}", style = MaterialTheme.typography.labelSmall)
            Text(text = "Population estimée : ${animalDataModel.estimatedPopulation}", style = MaterialTheme.typography.labelSmall)
        }
    }
}
