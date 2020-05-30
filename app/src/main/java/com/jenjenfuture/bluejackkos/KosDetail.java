package com.jenjenfuture.bluejackkos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class KosDetail extends AppCompatActivity {

    private String title = "Detail Kos";

    private ImageView imageView;
    private TextView namaKos;
    private TextView fasilitasKos;
    private TextView hargaKos;
    private TextView descKos;
    private TextView latKos;
    private TextView lngKos;

    private Button viewLocation;
    private Button bookKos;

    private Calendar calendar;
    private SimpleDateFormat dateFormatter;

    private int year;
    private int month;
    private int day;
    private String userId;
    private String name;
    private String facility;
    private String address;
    private String price;
    private String lat;
    private String lng;
    private String image;

    private SharedPreferences mySharedPreferences;
    private DBTransaction dbTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kos_detail);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(title);

        dbTransaction = DBTransaction.getInstance(this);

        imageView = findViewById(R.id.imgdetail);
        namaKos = findViewById(R.id.namakosdetail);
        fasilitasKos = findViewById(R.id.fasilitaskosdetail);
        hargaKos = findViewById(R.id.hrgkosdetail);
        descKos = findViewById(R.id.desckosdetail);
        latKos = findViewById(R.id.latitudekosdetail);
        lngKos = findViewById(R.id.longtitudekosdetail);

        viewLocation = findViewById(R.id.locationbutton);
        bookKos = findViewById(R.id.btnbookingkos);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        final Intent intent = getIntent();

        image = intent.getStringExtra(AdapterKosList.KEY_IMAGE);
        name = intent.getStringExtra(AdapterKosList.KEY_NAME);
        address = intent.getStringExtra(AdapterKosList.KEY_DESC);
        facility = intent.getStringExtra(AdapterKosList.KEY_FAC);
        price = "Rp. " + intent.getStringExtra(AdapterKosList.KEY_PRICE);
        lat = intent.getStringExtra(AdapterKosList.KEY_LAT);
        lng = intent.getStringExtra(AdapterKosList.KEY_LNG);

        mySharedPreferences = this.getSharedPreferences("login", Context.MODE_PRIVATE);
        userId = mySharedPreferences.getString("userId", "");

        Picasso.get().load(image).into(imageView);
        namaKos.setText(name);
        fasilitasKos.setText(facility);
        hargaKos.setText(price);
        descKos.setText(address);
        latKos.setText(lat);
        lngKos.setText(lng);

        viewLocation.setOnClickListener(v -> {
            Intent intent1 = new Intent(KosDetail.this,MapForm.class);
            intent1.putExtra(AdapterKosList.KEY_LAT,lat);
            intent1.putExtra(AdapterKosList.KEY_LNG,lng);
            intent1.putExtra(AdapterKosList.KEY_NAME,name);
            startActivity(intent1);
        });

        bookKos.setOnClickListener(v -> {
            calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(KosDetail.this, (view, year, month, dayOfMonth) -> {

                boolean canBook = true;
                Calendar newDate = Calendar.getInstance();
                newDate.set(year,month,dayOfMonth);
                String bookDate = dateFormatter.format(newDate.getTime());

                if (dbTransaction.checkBook(userId,name,bookDate)){
                    Toast toast = Toast.makeText(getApplicationContext(),"This kost has been booked",Toast.LENGTH_SHORT);
                    toast.show();
                    canBook = false;
                }

                if (canBook){

                    String bookId;
                    int sz = mySharedPreferences.getInt("currentidx",0);

                    if (sz<10){
                        bookId = "BK00" + sz;
                    }
                    else if (sz<=99){
                        bookId = "BK0" + sz;
                    }
                    else{
                        bookId = "BK" + sz;
                    }
                    mySharedPreferences.edit().putInt("currentidx",sz+1).apply();

                    BookingTransaction booking = new BookingTransaction();
                    booking.setBookingId(bookId);
                    booking.setUserId(userId);
                    booking.setKosName(name);
                    booking.setKosFacility(facility);
                    booking.setKosPrice(price);
                    booking.setKosDesc(address);
                    booking.setKosLatitude(lat);
                    booking.setKosLongtitude(lng);
                    booking.setBookingDate(bookDate);

                    dbTransaction.insertBooking(booking);

                    Toast toast = Toast.makeText(getApplicationContext(),"Booked Successfully",Toast.LENGTH_SHORT);
                    toast.show();
                }
            },year,month,day);

            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            datePickerDialog.show();
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }
}
