package com.example.myapplication.ui.theme

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class UserViewModel : ViewModel() {
    // User's onboarding inputs
    var fullName by mutableStateOf("")
    var mobileNumber by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var vehicleNumber by mutableStateOf("")
    var chassisNumber by mutableStateOf("")
    var selectedCategory by mutableStateOf("Coup/Sedan")

}
