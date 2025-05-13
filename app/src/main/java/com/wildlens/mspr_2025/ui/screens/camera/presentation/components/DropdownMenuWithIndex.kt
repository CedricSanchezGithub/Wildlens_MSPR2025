import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DropdownMenuWithIndex(
    items: List<String>,
    selectedIndex: Int,
    onSelected: (Int) -> Unit,
    enabledStates: List<Boolean> = List(items.size) { true }
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedButton(onClick = { expanded = true }) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = items[selectedIndex])
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Ouvrir le menu déroulant",
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEachIndexed { index, label ->
                val suffix = if (!enabledStates[index]) " (non supporté)" else ""
                DropdownMenuItem(
                    enabled = enabledStates[index],
                    onClick = {
                        expanded = false
                        onSelected(index)
                    },
                    text = { Text(label + suffix) }
                )
            }
        }
    }
}
