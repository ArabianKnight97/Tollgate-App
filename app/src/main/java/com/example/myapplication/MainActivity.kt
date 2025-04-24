package com.example.myapplication


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.ui.theme.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the Google Places API
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, "AIzaSyCa-6HCPD5_WLoSrMTr2nTxM94NKeg8r3Q")
        }
        val placesClient: PlacesClient = Places.createClient(this)

        setContent {
            MyApplicationTheme {
                MyApp(placesClient)
            }
        }
    }
}

@Composable
fun MyApp(placesClient: PlacesClient) {
    val navController = rememberNavController()
    val userViewModel: UserViewModel = viewModel() // Shared ViewModel for user data

    NavHost(navController = navController, startDestination = "onboarding") {
        // Onboarding Screen
        composable("onboarding") {
            OnboardingScreen(navController = navController, userViewModel = userViewModel)
        }

        // Profile Screen
        composable("profile_screen") {
            ProfileScreen(navController = navController, userViewModel = userViewModel)
        }
        composable("add_payment")  {
            AddPaymentScreen(navController = navController)
        }

        // Verification Screen
        composable("verification") {
            VerificationScreen(navController = navController)
        }

        // Dashboard Screen
        composable("dashboard") {
            DashboardScreen(navController = navController, userViewModel = userViewModel)
        }

        // Payment Screen
        composable("payment") {
            PaymentScreen(navController = navController, userViewModel = userViewModel)
        }

        // Pay Now Screen
        composable("paynow") {
            PayNow(navController = navController)
        }

        // Payment Complete Screen
        composable("paymentcomplete") {
            PaymentComplete(navController = navController)
        }

        // Toll Estimator Screen
        composable("toll_estimator") {
            val context = navController.context
            val intent = Intent(context, TollEstimator::class.java)
            context.startActivity(intent)
        }

        // Upload Documentation Screen
        composable("upload_documentation") {
            UploadDocumentationScreen(navController = navController)
        }
    }
}
