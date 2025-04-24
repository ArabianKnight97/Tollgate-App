package com.example.myapplication.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun PayNow(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Text(
            text = "Payment Details",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Payment Details Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color.Blue)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top Circular Design
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .padding(bottom = 16.dp)
                        .background(Color(0xFFFFA500), shape = CircleShape)
                )

                // Payment Total
                Text(
                    text = "Payment Total",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "$10.00",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Payment Details
                DetailsRow(label = "Date", value = "10/15/2024")
                DetailsRow(label = "Details", value = "Toll Payment")
                DetailsRow(label = "Reference num", value = "A06453826151")
                DetailsRow(label = "Account", value = "Nichalas Holdan")
                Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 16.dp))
                DetailsRow(label = "Total Payment", value = "$9.99")
                DetailsRow(label = "Processing fee", value = "$0.01")
                Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 16.dp))
                DetailsRow(label = "Total", value = "$3.99", isBold = true)
            }
        }

        // Pay Now Button
        Button(
            onClick = { navController.navigate("paymentcomplete")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500))
        ) {
            Text(text = "Pay Now", color = Color.White)
        }
    }
}

@Composable
fun DetailsRow(label: String, value: String, isBold: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp)
        )
        Text(
            text = value,
            style = if (isBold) {
                MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, fontSize = 14.sp)
            } else {
                MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp)
            }
        )
    }
}
