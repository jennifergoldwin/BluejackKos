package com.jenjenfuture.bluejackkos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class KosDetail extends AppCompatActivity {

    ImageView imageView;
    TextView namaKos;
    TextView fasilitasKos;
    TextView hargaKos;
    TextView descKos;
    TextView latKos;
    TextView lngKos;

    Button viewLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kos_detail);

        imageView = findViewById(R.id.imgdetail);
        namaKos = findViewById(R.id.namakosdetail);
        fasilitasKos = findViewById(R.id.fasilitaskosdetail);
        hargaKos = findViewById(R.id.hrgkosdetail);
        descKos = findViewById(R.id.desckosdetail);
        latKos = findViewById(R.id.latitudekosdetail);
        lngKos = findViewById(R.id.longtitudekosdetail);

        viewLocation = findViewById(R.id.locationbutton);

        final Intent intent = getIntent();

        String image = intent.getStringExtra(AdapterKosList.KEY_IMAGE);
        final String name = intent.getStringExtra(AdapterKosList.KEY_NAME);
        String address = intent.getStringExtra(AdapterKosList.KEY_DESC);
        String facility = intent.getStringExtra(AdapterKosList.KEY_FAC);
        String price = "Rp. " + intent.getStringExtra(AdapterKosList.KEY_PRICE);
        final String lat = intent.getStringExtra(AdapterKosList.KEY_LAT);
        final String lng = intent.getStringExtra(AdapterKosList.KEY_LNG);

        Picasso.with(this).load(image).into(imageView);
        namaKos.setText(name);
        fasilitasKos.setText(facility);
        hargaKos.setText(price);
        descKos.setText(address);
        latKos.setText(lat);
        lngKos.setText(lng);

        viewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(KosDetail.this,MapForm.class);
                intent1.putExtra(AdapterKosList.KEY_LAT,lat);
                intent1.putExtra(AdapterKosList.KEY_LNG,lng);
                intent1.putExtra(AdapterKosList.KEY_NAME,name);
                startActivity(intent1);
            }
        });

    }
}
