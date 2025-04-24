package com.example.myapplication.ui.theme;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TollEstimator extends AppCompatActivity {

    private AutoCompleteTextView startDestination, endDestination;
    private Button estimateButton;
    private TextView tollCostTextView, tollCountTextView;
    private GoogleMap googleMap;
    private PlacesClient placesClient;
    private List<LatLng> routePoints = new ArrayList<>();
    private static final String GOOGLE_API_KEY = "AIzaSyCa-6HCPD5_WLoSrMTr2nTxM94NKeg8r3Q";
    private static final String TOLLGURU_API_KEY = "28497DRrD7nJTtd7hb4rDM4QRgnQ4n9g";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toll_estimator);

        // Initialize Places API
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), GOOGLE_API_KEY);
        }
        placesClient = Places.createClient(this);

        startDestination = findViewById(R.id.startDestination);
        endDestination = findViewById(R.id.endDestination);
        estimateButton = findViewById(R.id.estimateButton);
        tollCostTextView = findViewById(R.id.tollCostTextView);
        tollCountTextView = findViewById(R.id.tollCountTextView);

        setupAutocomplete(startDestination);
        setupAutocomplete(endDestination);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(map -> googleMap = map);
        }

        estimateButton.setOnClickListener(v -> {
            String start = startDestination.getText().toString();
            String end = endDestination.getText().toString();
            if (!start.isEmpty() && !end.isEmpty()) {
                estimateRouteAndTolls(start, end);
            } else {
                Toast.makeText(this, "Please enter both start and end destinations", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupAutocomplete(AutoCompleteTextView textView) {
        textView.setThreshold(1);
        textView.setOnKeyListener((v, keyCode, event) -> {
            String query = textView.getText().toString();
            if (!query.isEmpty()) {
                fetchPredictions(query, predictions -> {
                    List<String> predictionTexts = new ArrayList<>();
                    for (AutocompletePrediction prediction : predictions) {
                        predictionTexts.add(prediction.getFullText(null).toString());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, predictionTexts);
                    textView.setAdapter(adapter);
                });
            }
            return false;
        });
    }

    private void fetchPredictions(String query, PredictionsCallback callback) {
        AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();
        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                .setQuery(query)
                .setSessionToken(token)
                .build();

        placesClient.findAutocompletePredictions(request)
                .addOnSuccessListener(response -> callback.onPredictionsFetched(response.getAutocompletePredictions()))
                .addOnFailureListener(Throwable::printStackTrace);
    }

    private void estimateRouteAndTolls(String start, String end) {
        new Thread(() -> {
            try {
                // Build Google Directions API URL
                String directionsUrl = "https://maps.googleapis.com/maps/api/directions/json?origin="
                        + start + "&destination=" + end + "&key=" + GOOGLE_API_KEY;

                // Make HTTP request to Google Directions API
                HttpURLConnection connection = (HttpURLConnection) new URL(directionsUrl).openConnection();
                connection.connect();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder responseBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseBuilder.append(line);
                }

                JSONObject jsonResponse = new JSONObject(responseBuilder.toString());
                JSONArray routes = jsonResponse.getJSONArray("routes");
                if (routes.length() > 0) {
                    // Get route points from Directions API
                    JSONArray legs = routes.getJSONObject(0).getJSONArray("legs");
                    JSONArray steps = legs.getJSONObject(0).getJSONArray("steps");

                    routePoints.clear();
                    for (int i = 0; i < steps.length(); i++) {
                        JSONObject step = steps.getJSONObject(i);
                        JSONObject startLocation = step.getJSONObject("start_location");
                        JSONObject endLocation = step.getJSONObject("end_location");

                        // Add start and end points of each step to routePoints
                        routePoints.add(new LatLng(startLocation.getDouble("lat"), startLocation.getDouble("lng")));
                        routePoints.add(new LatLng(endLocation.getDouble("lat"), endLocation.getDouble("lng")));
                    }

                    // Show the route on the map
                    runOnUiThread(() -> drawRouteOnMap());

                    // Fetch toll information
                    fetchTollGuruData(start, end, tollGuruResponse -> {
                        try {
                            double tollCost = tollGuruResponse.getDouble("totalTollCost");
                            JSONArray tolls = tollGuruResponse.getJSONArray("tolls");
                            int tollCount = tolls.length();

                            // Update UI with toll information
                            runOnUiThread(() -> {
                                tollCostTextView.setText(String.format("Total Toll Cost: $%.2f", tollCost));
                                tollCountTextView.setText(String.format("Number of Tolls: %d", tollCount));
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                } else {
                    runOnUiThread(() ->
                            Toast.makeText(this, "Unable to find a route. Check start and end locations.", Toast.LENGTH_SHORT).show()
                    );
                }
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() ->
                        Toast.makeText(this, "Failed to estimate route and tolls.", Toast.LENGTH_SHORT).show()
                );
            }
        }).start();
    }


    private void fetchTollGuruData(String origin, String destination, TollGuruCallback callback) {
        new Thread(() -> {
            try {
                // API Endpoint and Payload
                String tollGuruUrl = "https://apis.tollguru.com/toll/v2/origin-destination-waypoints";
                String payload = String.format("{\"source\":\"google\",\"origin\":\"%s\",\"destination\":\"%s\",\"vehicleType\":\"2AxlesAuto\"}", origin, destination);

                // HTTP POST Request
                HttpURLConnection connection = (HttpURLConnection) new URL(tollGuruUrl).openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("x-api-key", TOLLGURU_API_KEY);
                connection.setDoOutput(true);
                connection.getOutputStream().write(payload.getBytes());

                // Parse the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder responseBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseBuilder.append(line);
                }

                callback.onResponse(new JSONObject(responseBuilder.toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void drawRouteOnMap() {
        if (googleMap != null && !routePoints.isEmpty()) {
            googleMap.clear(); // Clear previous routes
            googleMap.addPolyline(new PolylineOptions().addAll(routePoints).width(8).color(0xFF4285F4)); // Blue polyline

            // Adjust camera to fit the route
            LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
            for (LatLng point : routePoints) {
                boundsBuilder.include(point);
            }
            googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 100));
        } else {
            Toast.makeText(this, "Route could not be displayed", Toast.LENGTH_SHORT).show();
        }
    }


    interface PredictionsCallback {
        void onPredictionsFetched(List<AutocompletePrediction> predictions);
    }

    interface TollGuruCallback {
        void onResponse(JSONObject response);
    }
}
