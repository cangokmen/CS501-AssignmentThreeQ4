package com.example.assignmentthreeq4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.assignmentthreeq4.ui.theme.AssignmentThreeQ4Theme
import kotlinx.coroutines.launch

// MainActivity is the app's entry point.
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AssignmentThreeQ4Theme {
                Main()
            }
        }
    }
}

// Main composable containing the entire screen structure.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main(modifier: Modifier = Modifier) {

    // State for showing snackbars and the scope to launch them.
    val hostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // State for the currently selected item in the bottom navigation.
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("Home", "Settings", "Profile")
    val icons = listOf(Icons.Filled.Home, Icons.Filled.Settings, Icons.Filled.Person)

    // Scaffold provides the main Material Design layout structure.
    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState) },
        topBar = {
            TopAppBar(
                title = { Text("MyApp") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(icons[index], contentDescription = item) },
                        label = { Text(item) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index }
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                // Launch a coroutine to show a snackbar on click.
                scope.launch {
                    hostState.showSnackbar(
                        message = "Message",
                        duration = SnackbarDuration.Short
                    )
                }
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
        // Main content area of the screen.
        ScreenContent(
            text = items[selectedItem],
            modifier = Modifier.padding(innerPadding)
        )
    }
}

// Displays the content for the currently selected screen.
@Composable
fun ScreenContent(text: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text)
    }
}


// Preview for the main screen layout.
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AssignmentThreeQ4Theme {
        Main()
    }
}
