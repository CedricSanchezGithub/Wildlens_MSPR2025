package com.wildlens.mspr_2025.ui.screens.animals.presentation

// Accessibility imports
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.wildlens.mspr_2025.R
import com.wildlens.mspr_2025.data.models.MetasDataModel

@Composable
fun AnimalsScreenSuccess(metasDataModel: MetasDataModel) {

    val interactionSource = remember { MutableInteractionSource() }
    val animalInfoDescription = stringResource(R.string.animal_info_description)

    Column(
        modifier = Modifier
            .padding(8.dp)
            .semantics {
                // Mark as a traversal group for better screen reader navigation
                isTraversalGroup = true
                // Add a content description for the entire screen
                contentDescription = animalInfoDescription
            }
    ) {
        // Screen title with heading semantic for screen readers
        Text(
            text = stringResource(R.string.animal_info_description),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .semantics {
                    // Mark as a heading for screen readers
                    heading()
                }
        )

        // List of animals with semantic properties for accessibility
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                // Make focusable for keyboard navigation
                .focusable(interactionSource = interactionSource)
                .semantics {
                    // Add a content description for the list
                    contentDescription = "List of ${metasDataModel.metasData.size} animals"
                },
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(metasDataModel.metasData) { animal ->
                AnimalCard(animalDataModel = animal)
            }
        }
    }
}
