/**
 *
 * Nama : Rudiansyah Adi Prasetyo
 * Nim : 10120001
 * Kelas : IF 1
 * Email : rudiansyahadi21@gmail.com
 *
 */

package com.rudiansyah10120001.tugasmaps.ui.dashboard;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.Manifest;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.rudiansyah10120001.tugasmaps.R;
import com.rudiansyah10120001.tugasmaps.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap gMap;
    private FusedLocationProviderClient client;
    private String baseUrl = "https://api.tomtom.com/search/2/nearbySearch/.json";
    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        client = LocationServices.getFusedLocationProviderClient(getActivity());
        mapView = (MapView) root.findViewById(R.id.google_map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        getCurrentLocation(client);

        return root;
    }

    private void getCurrentLocation(FusedLocationProviderClient client) {
        // Get Permission
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

        // Show my location on map
        client.requestLocationUpdates(new LocationRequest(), new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                LatLng lokasi = new LatLng(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude());
                MarkerOptions options = new MarkerOptions().position(lokasi).title("Lokasi Saat Ini");
                gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lokasi,17));
                gMap.addMarker(options);

                // Show Restaurant
                getRestaurant(lokasi);
            }
        }, Looper.getMainLooper());
    }

    private void getRestaurant(LatLng lokasi) {
        String lat = String.valueOf(lokasi.latitude);
        String lon = String.valueOf(lokasi.longitude);
        baseUrl = baseUrl + "?lat=" + lat + "&lon=" + lon
                + "&limit=5" + "&radius=10000" + "&categorySet=7315" + "&view=Unified"
                + "&key=Pmv2pAZVuqVjX3haWJrJHCb5RJxgURBG";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(baseUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("API Request", response.toString());
                try {
                    JSONArray results = response.getJSONArray("results");

                    // Add marker
                    for(int i = 0; i < results.length(); i++) {
                        // Restaurant Position
                        JSONObject place = results.getJSONObject(i).getJSONObject("position");
                        JSONObject placeLabel = results.getJSONObject(i).getJSONObject("poi");

                        LatLng placeLocation = new LatLng(place.getDouble("lat"), place.getDouble("lon"));
                        MarkerOptions options = new MarkerOptions().position(placeLocation).title(placeLabel.getString("name"));
                        gMap.addMarker(options);
                    }
                } catch (JSONException e) {
                    Log.e("MYAPP", "unexpected JSON exception", e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Get restaurant error", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;
    }
}