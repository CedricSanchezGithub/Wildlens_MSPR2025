package com.wildlens.mspr_2025.ui.screens.animals.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wildlens.mspr_2025.data.models.MetasDataModel
import androidx.compose.foundation.lazy.items

@Composable
fun AnimalsScreenSuccess(metasDataModel: MetasDataModel) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text("Informations sur les animaux de la base de données.")

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(metasDataModel.metasData) { animal ->
                AnimalCard(animalDataModel = animal)
            }
        }
    }
}