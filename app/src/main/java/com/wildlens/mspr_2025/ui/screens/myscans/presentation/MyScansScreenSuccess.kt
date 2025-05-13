package com.wildlens.mspr_2025.ui.screens.myscans.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.wildlens.mspr_2025.ui.screens.myscans.state.MyScansState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScansScreenSuccess(
    uiModel: MyScansState.Success,
    onAnimalSelected: (String) -> Unit
) {

    val animals = listOf(
        "beaver",
        "black_bear",
        "bob_cat",
        "coyote",
        "goose",
        "gray_fox",
        "horse",
        "lion",
        "mink",
        "mouse",
        "muledeer",
        "otter",
        "raccoon",
        "rat",
        "skunk",
        "turkey",
        "western_grey_squirrel"
    )
    var expanded by remember { mutableStateOf(false) }
    var selectedAnimal by remember { mutableStateOf(animals.first()) }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                readOnly = true,
                value = selectedAnimal,
                onValueChange = {},
                label = { Text("Choisir un animal") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                animals.forEach { animal ->
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

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            item {
                Text(
                    text = "Scan de l'animal ${uiModel.imagesTracks.nomFr}",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(8.dp)
                )
            }
            item {
                Text(
                    text = "Nombre de scans : ${uiModel.imagesTracks.images.size}",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(8.dp)
                )
            }

            items(uiModel.imagesTracks.images.size) { index ->
                val scan = uiModel.imagesTracks.images[index]
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .padding(8.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("http://192.168.1.26:5000/${scan.url}")
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                }
            }
        }
    }
}
