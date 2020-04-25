package com.jenjenfuture.bluejackkos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class HistoryTransaction extends AppCompatActivity {

    private String title = "Booking Transaction";

    private RecyclerView recyclerView;

    private TextView nobooking;
    private String userId;
    private SharedPreferences sharedPreferences;
    private AdapterBookingList adapterBookingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(title);

        sharedPreferences = this.getSharedPreferences("login",MODE_PRIVATE);
        userId= sharedPreferences.getString("userId","");

        adapterBookingList = new AdapterBookingList(this,userId);

        setContentView(R.layout.activity_history_transaction);

        nobooking = findViewById(R.id.nobook);

        recyclerView = findViewById(R.id.recylerview1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        recyclerView.setAdapter(adapterBookingList);
        adapterBookingList.notifyDataSetChanged();

        if (adapterBookingList.getItemCount()>0){
            nobooking.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) finish();

        return super.onOptionsItemSelected(item);
    }

    public void refresh() {
        onStart();
    }
}
