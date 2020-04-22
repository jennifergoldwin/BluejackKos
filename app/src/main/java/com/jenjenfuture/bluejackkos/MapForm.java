package com.jenjenfuture.bluejackkos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapForm extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;
    SupportMapFragment supportMapFragment;
    String name;
    double lat;
    double lng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_form);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapAPI);
        supportMapFragment.getMapAsync(this);

        Intent intent = getIntent();
        name = intent.getStringExtra(AdapterKosList.KEY_NAME);
        lat = Double.parseDouble(intent.getStringExtra(AdapterKosList.KEY_LAT));
        lng = Double.parseDouble(intent.getStringExtra(AdapterKosList.KEY_LNG));

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;
        float zoomLvl = 16.0f;
        LatLng latLng = new LatLng(lat,lng);
        map.addMarker(new MarkerOptions().position(latLng).title(name));
        map.setMinZoomPreference(zoomLvl);
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));



    }


}
