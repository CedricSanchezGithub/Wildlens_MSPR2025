package com.wildlens.mspr_2025.ui.screens.home.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.wildlens.mspr_2025.R
import com.wildlens.mspr_2025.core.navigation.AppRoute

@Composable
fun HomeScreenSuccess(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(R.string.app_name),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Identifiez les empreintes animales grâce à l'Intelligence Artificielle",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(32.dp))
            Image(
                painter = painterResource(R.drawable.aug_0_1505),
                contentDescription = "Illustration empreinte",
                modifier = Modifier
                    .height(180.dp)
                    .width(180.dp)
                    .clip(MaterialTheme.shapes.large)
                    .border(2.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.large)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { navController.navigate(AppRoute.Camera.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(Icons.Filled.CameraAlt, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Scanner une empreinte")
//                Spacer(modifier = Modifier.height(16.dp))
//                TextButton(onClick = { navController.navigate(AppRoute.Results.route) }) {
//                    Text("Voir mes derniers résultats")
//                }
            }
                Spacer(modifier = Modifier.weight(1f))
        }
    }
}