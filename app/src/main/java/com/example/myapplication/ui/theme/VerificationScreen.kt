package com.example.myapplication.ui.theme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun VerificationScreen(navController: NavController, userViewModel: UserViewModel = viewModel()) {
    // State for phone number and verification status
    var phoneNumber by remember { mutableStateOf("XXX-XXX-XXXX") } // Replace with actual data from previous step
    var isVerified by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top Section: Progress Indicator
        StepProgressIndicator(currentStep = 2, totalSteps = 3)

        // Middle Section: Phone Number Display and Verify Button
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Verification",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "Phone Number",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = phoneNumber,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Button(
                onClick = { isVerified = true }, // Simulates verification success
                enabled = !isVerified,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = if (isVerified) "Verified" else "Verify")
            }
        }

        // Bottom Section: Next Button
        Button(
            onClick = { navController.navigate("upload_documentation") },
            enabled = isVerified,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(text = "Next")
        }
    }
}

