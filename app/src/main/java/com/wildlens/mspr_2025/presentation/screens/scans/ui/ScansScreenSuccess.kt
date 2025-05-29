package com.wildlens.mspr_2025.presentation.screens.scans.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.wildlens.mspr_2025.R
import com.wildlens.mspr_2025.presentation.screens.scans.state.MyScansScreenState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScansScreenSuccess(
    uiModel: MyScansScreenState.Success,
    onAnimalSelected: (String) -> Unit
) {
    val speciesList = uiModel.data.speces.species.map { it }
    var expanded by remember { mutableStateOf(false) }
    var selectedAnimal by remember { mutableStateOf(speciesList.firstOrNull().orEmpty()) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    readOnly = true,
                    value = selectedAnimal,
                    onValueChange = {},
                    label = { Text(stringResource(R.string.choose_animal)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    speciesList.forEach { animal ->
                        DropdownMenuItem(
                            text = { Text(animal) },
                            onClick = {
                                selectedAnimal = animal
                                expanded = false
                                onAnimalSelected(animal)
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (uiModel.data.imagesTracks != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    uiModel.data.imagesTracks.let { speces ->
                        Text(
                            text = stringResource(
                                R.string.track_photos_count,
                                speces.images.size,
                                speces.nomFr
                            ),
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .weight(1f)
                        )
                    }

                    val context = LocalContext.current
                    Text(
                        text = "En savoir plus",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.primary,
                            textDecoration = TextDecoration.Underline
                        ),
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .clickable {
                                val animalName = uiModel.data.imagesTracks.nomLatin
                                val wikipediaUrl = "https://fr.wikipedia.org/wiki/$animalName"
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(wikipediaUrl))
                                context.startActivity(intent)
                            }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            uiModel.data.imagesTracks?.images?.let { images ->
                items(images.size) { index ->
                    val scan = images[index]
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("http://90.51.140.217:5001/${scan.url}")
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clip(MaterialTheme.shapes.large)
                            .border(2.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.large)
                    )
                }
            }
        }
    }
}