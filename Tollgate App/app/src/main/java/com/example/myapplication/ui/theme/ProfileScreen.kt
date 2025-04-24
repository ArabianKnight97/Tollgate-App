package com.example.myapplication.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, userViewModel: UserViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Profile") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    TextButton(onClick = { /* Handle edit action */ }) {
                        Text(text = "Edit", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // Personal Information Section
            Text(
                text = "Personal Information",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            PersonalInfoSection(userViewModel)

            Divider(modifier = Modifier.padding(vertical = 16.dp))

            // Payment Settings Section
            Text(
                text = "Payment Settings",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            PaymentSettingsSection(navController)

            Spacer(modifier = Modifier.weight(1f))

            // Logout Button
            Button(
                onClick = { /* Handle logout action */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text(text = "Logout", color = MaterialTheme.colorScheme.onError)
            }
        }
    }
}

@Composable
fun PersonalInfoSection(userViewModel: UserViewModel) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        ProfileInfoItem(label = "Name", value = userViewModel.fullName)
        ProfileInfoItem(label = "Your Email", value = userViewModel.email)
        ProfileInfoItem(label = "Phone Number", value = userViewModel.mobileNumber)
        ProfileInfoItem(label = "Password", value = "••••••••") // Masked for security
    }
}

@Composable
fun ProfileInfoItem(label: String, value: String) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
        )
    }
}

@Composable
fun PaymentSettingsSection(navController: NavController) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        PaymentMethodCard(cardType = "Master Card", lastDigits = "6356")
        PaymentMethodCard(cardType = "Visa", lastDigits = "5645")
        Button(
            onClick = { navController.navigate("add_payment") }, // Navigate to add payment screen
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(text = "Add Payment", color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}

@Composable
fun PaymentMethodCard(cardType: String, lastDigits: String) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "$cardType ****$lastDigits",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
