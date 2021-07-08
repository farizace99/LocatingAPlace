package com.myapplicationdev.android.locatingaplace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity  {

    Spinner spinner1;
    GoogleMap map;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner1 = findViewById(R.id.spinner);
        tv = findViewById(R.id.textView);

        final LatLng poi_north = new LatLng(1.465390, 103.817140);
        final LatLng poi_east = new LatLng(1.289440 , 103.849983);
        final LatLng poi_central = new LatLng(1.332700, 103.799179);

        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment)
                fm.findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION);

                if (permissionCheck == PermissionChecker.PERMISSION_GRANTED) {
                    map.setMyLocationEnabled(true);
                } else {
                    Log.e("GMap - Permission", "GPS access has not been granted");
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                }

                UiSettings ui = map.getUiSettings();
                ui.setCompassEnabled(true);
                ui.setZoomControlsEnabled(true);

                final Marker north = map.addMarker(new
                        MarkerOptions()
                        .position(poi_north)
                        .title("HQ - North")
                        .snippet("Block 333, Admiralty Ave 3, 765654 Operating hours: 10am-5pm" +
                                "Tel:65433456")
                        .icon(BitmapDescriptorFactory.fromResource(android.R.drawable.btn_star_big_on)));

                final Marker east = map.addMarker(new
                        MarkerOptions()
                        .position(poi_east)
                        .title("HQ - East")
                        .snippet("Block 555, Tampines Ave 3, 287788 \n" +
                                "Operating hours: 9am-5pm\n" +
                                "Tel:66776677\"\n")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

                final Marker central = map.addMarker(new
                        MarkerOptions()
                        .position(poi_central)
                        .title("HQ - Central")
                        .snippet("Block 333, Admiralty Ave 3, 765654 Operating hours: 10am-5pm\n" +
                                "Tel:65433456")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));


                //(called when a marker has been clicked)
                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        if(marker.equals(north)){
                            north.showInfoWindow(); //to show title
                            Toast.makeText(getApplicationContext(), "Block 333, Admiralty Ave 3, 765654 \nOperating hours: 10am-5pm \nTel:65433456", Toast.LENGTH_LONG).show();
                            return true;
                        }else if(marker.equals(central)){
                            central.showInfoWindow();
                            Toast.makeText(getApplicationContext(), "Block 3A, Orchard Ave 3, 134542 \nOperating hours: 11am-8pm \nTel:67788652", Toast.LENGTH_LONG).show();
                            return true;
                        }else if(marker.equals(east)){
                            east.showInfoWindow();
                            Toast.makeText(getApplicationContext(), "Block 555, Tampines Ave 3, 287788 \nOperating hours: 9am-5pm \nTel:66776677", Toast.LENGTH_LONG).show();
                            return true;
                        }else{
                            return false;
                        }
                    }
                });

            }


        });

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position){
                    case 0:
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_north,15));
                        break;
                    case 1:
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_central,15));
                        break;
                    case 2:
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(poi_east,15));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}