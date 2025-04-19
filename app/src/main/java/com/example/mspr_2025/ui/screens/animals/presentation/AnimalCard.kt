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
import com.example.mspr_2025.data.models.Animal

@Composable
fun AnimalCard(animal: Animal) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = animal.frenchName, style = MaterialTheme.typography.titleLarge)
            Text(text = animal.latinName, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.height(8.dp))
            Text(text = animal.description, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(4.dp))
            Text(text = "Localisation : ${animal.location}", style = MaterialTheme.typography.labelSmall)
            Text(text = "Population estimée : ${animal.estimatedPopulation}", style = MaterialTheme.typography.labelSmall)
        }
    }
}
