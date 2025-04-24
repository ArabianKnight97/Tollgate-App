package com.example.myapplication.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun PaymentComplete(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Circular Success Indicator
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(Color(0xFFFFA500), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            // (Optional) Add a checkmark text or shape
            Text(
                text = "✓",
                fontSize = 48.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        // Payment Successful Text
        Text(
            text = "Payment Successful",
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
            modifier = Modifier.padding(top = 16.dp),
            color = Color.Black
        )

        // Back to Dashboard Button
        Button(
            onClick = { navController.navigate("dashboard") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500))
        ) {
            Text(text = "Back to Dashboard", color = Color.White)
        }
    }
}
