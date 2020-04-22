package com.jenjenfuture.bluejackkos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.List;

public class KosList extends AppCompatActivity {

    private static final String URL_KOST = "https://bit.ly/2zd4uhX";

    RecyclerView recyclerView;
    AdapterKosList adapterKosList;
    List<KosData> kosDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kos_list);

        recyclerView = findViewById(R.id.recylerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        kosDataList = new ArrayList<>();

        loadUrlData();

    }

    private void loadUrlData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_KOST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {

                    JSONArray array = new JSONArray(response);

                    for (int i=0;i<array.length();i++){
                        JSONObject jsonObject1 = array.getJSONObject(i);
                        KosData kosData= new KosData();
                        kosData.setId(jsonObject1.getInt("id"));
                        kosData.setKosName(jsonObject1.getString("name"));
                        kosData.setKosDesc(jsonObject1.getString("address"));
                        kosData.setKosFacility(jsonObject1.getString("facilities"));
                        kosData.setPhoto_url(jsonObject1.getString("image"));
                        kosData.setKosLatitude(jsonObject1.getString("LAT"));
                        kosData.setKosLongitude(jsonObject1.getString("LNG"));
                        kosData.setKosPrice(jsonObject1.getString("price"));

                        kosDataList.add(kosData);
                    }
                    adapterKosList = new AdapterKosList(kosDataList,getApplicationContext());
                    recyclerView.setAdapter(adapterKosList);


                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        setMode(item.getItemId());
        return super.onOptionsItemSelected(item);
    }
}
