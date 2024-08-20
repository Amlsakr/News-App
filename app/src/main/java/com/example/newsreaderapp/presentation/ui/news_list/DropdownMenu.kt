package com.example.newsreaderapp.presentation.ui.news_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun ExposedDropdownMenu(  newsListViewModel: NewsListViewModel = hiltViewModel()) {
    // State to manage the expanded state of the menu
    var expanded by remember { mutableStateOf(false) }

    // State to manage the selected value
    val selectedOption by newsListViewModel.selectedCategory.collectAsState()

    // List of options for the dropdown menu
    val options =
        listOf("business", "entertainment", "general", "health", "science", "sports", "technology")

    // Column to hold the TextField and DropdownMenu
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()


    ) {
        // TextField to display the selected option and handle dropdown menu
        TextField(
            value = selectedOption,
            onValueChange = {  },
            readOnly = true, // Make TextField read-only to trigger the dropdown
            label = { Text("Select an option") },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    Modifier.clickable { expanded = !expanded }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded } // Expand the menu when clicked
        )

        // Dropdown menu to show the options
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                        newsListViewModel.onOptionSelected(option)
                        expanded = false // Collapse the menu after selecting an option
                    }
                )
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun PreviewExposedDropdownMenuSample(){
    ExposedDropdownMenu()
}
