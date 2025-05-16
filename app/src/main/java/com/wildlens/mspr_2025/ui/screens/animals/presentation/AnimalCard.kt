package com.wildlens.mspr_2025.ui.screens.animals.presentation

// Add imports for accessibility
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.wildlens.mspr_2025.R
import com.wildlens.mspr_2025.data.models.MetaDataModel

@Composable
fun AnimalCard(animalDataModel: MetaDataModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            // Increase touch target size for better accessibility
            .padding(vertical = 8.dp)
            // Add semantic properties for accessibility services
            .semantics {
                // Add a content description for screen readers
                contentDescription = "${animalDataModel.nomFr}, ${animalDataModel.nomLatin}"
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                // Make the entire column clickable with a larger touch target
                .fillMaxWidth()
        ) {
            // Use semantics to provide better screen reader support
            Text(
                text = animalDataModel.nomFr,
                style = MaterialTheme.typography.titleLarge,
                // Add semantic properties for accessibility
                modifier = Modifier.semantics {
                    heading() // Mark as a heading for screen readers
                }
            )

            Text(
                text = animalDataModel.nomLatin,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = animalDataModel.description,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = stringResource(R.string.animal_location, animalDataModel.localisation),
                style = MaterialTheme.typography.labelSmall,
                // Increase the minimum touch target size
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Text(
                text = stringResource(R.string.animal_estimated_population, animalDataModel.populationEstimee),
                style = MaterialTheme.typography.labelSmall,
                // Increase the minimum touch target size
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}
