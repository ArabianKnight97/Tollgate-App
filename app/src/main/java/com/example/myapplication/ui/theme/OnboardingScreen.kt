package com.example.myapplication.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
@Composable
fun OnboardingScreen(navController: NavController, userViewModel: UserViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top Section: Progress Indicator
        StepProgressIndicator(currentStep = 1, totalSteps = 3)

        // Middle Section: Input Fields
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Let's Get Started",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            TextField(
                value = userViewModel.fullName,
                onValueChange = { userViewModel.fullName = it },
                label = { Text("Full Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = userViewModel.mobileNumber,
                onValueChange = { userViewModel.mobileNumber = it },
                label = { Text("Mobile No.") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = userViewModel.email,
                onValueChange = { userViewModel.email = it },
                label = { Text("Email Address") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Password Field with Hide/Show Feature
            var isPasswordVisible by remember { mutableStateOf(false) }
            TextField(
                value = userViewModel.password,
                onValueChange = { userViewModel.password = it },
                label = { Text("Enter Password") },
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            imageVector = if (isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = if (isPasswordVisible) "Hide Password" else "Show Password"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = userViewModel.vehicleNumber,
                onValueChange = { userViewModel.vehicleNumber = it },
                label = { Text("Vehicle Number") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Radio Buttons for Category
            Text(
                text = "Category",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            val categories = listOf("Coup/Sedan", "SUV/Truck", "Semi")
            categories.forEach { category ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (category == userViewModel.selectedCategory),
                            onClick = { userViewModel.selectedCategory = category }
                        )
                ) {
                    RadioButton(
                        selected = (category == userViewModel.selectedCategory),
                        onClick = { userViewModel.selectedCategory = category }
                    )
                    Text(
                        text = category,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }

        // Bottom Section: Next Button
        Button(
            onClick = {
                // Navigate to the verification screen
                navController.navigate("verification")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Next")
        }
    }
}


@Composable
fun StepProgressIndicator(currentStep: Int, totalSteps: Int) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        repeat(totalSteps) { step ->
            val color =
                if (step < currentStep) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(
                    alpha = 0.3f
                )
            Divider(
                color = color,
                thickness = 4.dp,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp)
            )
        }
    }
}
