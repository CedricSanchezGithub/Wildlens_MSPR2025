package com.wildlens.mspr_2025.presentation.screens.animals.ui

// Add imports for accessibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.wildlens.mspr_2025.R
import com.wildlens.mspr_2025.data.models.MetaDataModel

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.ui.platform.LocalContext

@Composable
fun AnimalCard(animalDataModel: MetaDataModel) {
    val context = LocalContext.current
    val wikipediaUrl = "https://fr.wikipedia.org/wiki/${animalDataModel.nomLatin.replace(" ", "_")}"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 8.dp)
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(wikipediaUrl))
                context.startActivity(intent)
            }
            .semantics {
                contentDescription = "${animalDataModel.nomFr}, ${animalDataModel.nomLatin}"
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.iconwhite),
                contentDescription = null,
                modifier = Modifier
                    .size(96.dp) // Agrandissement
                    .aspectRatio(1f)
                    .clip(MaterialTheme.shapes.medium)
            )
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = animalDataModel.nomFr,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.semantics { heading() }
                )
                Text(
                    text = animalDataModel.nomLatin,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = animalDataModel.description,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.animal_location, animalDataModel.localisation),
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = stringResource(R.string.animal_estimated_population, animalDataModel.populationEstimee),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}
