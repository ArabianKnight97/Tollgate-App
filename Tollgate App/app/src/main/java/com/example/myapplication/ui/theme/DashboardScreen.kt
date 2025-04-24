package com.example.myapplication.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController, userViewModel: UserViewModel) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(navController) {
                coroutineScope.launch { drawerState.close() }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Dashboard") },
                    navigationIcon = {
                        IconButton(onClick = { coroutineScope.launch { drawerState.open() } }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { paddingValues ->
            DashboardContent(
                userViewModel = userViewModel,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}

@Composable
fun DashboardContent(userViewModel: UserViewModel, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome to your Dashboard, ${userViewModel.fullName}!",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Here are your latest activities and stats:",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        DashboardCard(
            title = "Recent Transactions",
            content = "Last payment: $3.99 on 10/15/2024",
            backgroundColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )

        DashboardCard(
            title = "Your Account Stats",
            content = "Total paid tolls: $45.89",
            backgroundColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        DashboardCard(
            title = "Upcoming Payments",
            content = "Next payment due: $10.00 on 12/05/2024",
            backgroundColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.onTertiary
        )

        DashboardCard(
            title = "Notifications",
            content = "You have 1 new alerts.",
            backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.weight(1f))


    }
}


@Composable
fun DashboardCard(
    title: String,
    content: String,
    backgroundColor: androidx.compose.ui.graphics.Color,
    contentColor: androidx.compose.ui.graphics.Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = title, style = MaterialTheme.typography.titleMedium.copy(color = contentColor))
            Text(text = content, style = MaterialTheme.typography.bodyMedium.copy(color = contentColor))
        }
    }
}

@Composable
fun DrawerContent(navController: NavController, onCloseDrawer: () -> Unit) {
    val menuItems = listOf(
        "Dashboard" to "dashboard",
        "Profile" to "profile_screen",
        "Payment" to "payment",
        "Toll Estimator" to "toll_estimator"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Navigation",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        menuItems.forEach { (label, route) ->
            Button(
                onClick = {
                    navController.navigate(route)
                    onCloseDrawer()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(text = label)
            }
        }
    }
}
