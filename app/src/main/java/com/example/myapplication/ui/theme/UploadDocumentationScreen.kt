package com.example.myapplication.ui.theme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun UploadDocumentationScreen(navController: NavController) {
    // State to track if the document is uploaded
    var isUploaded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top Section: Progress Indicator
        StepProgressIndicator(currentStep = 3, totalSteps = 3)

        // Middle Section: Upload Button
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Upload Documentation",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Button(
                onClick = {
                    isUploaded = true // Simulates the upload process
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = if (isUploaded) "Uploaded" else "Upload Photo")
            }
        }

        // Bottom Section: Finish Button
        Button(
            onClick = {
                navController.navigate("dashboard")
            },
            enabled = isUploaded,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Finish")
        }


    }
}