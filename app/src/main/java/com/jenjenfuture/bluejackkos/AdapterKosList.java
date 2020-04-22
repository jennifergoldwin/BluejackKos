package com.jenjenfuture.bluejackkos;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterKosList extends RecyclerView.Adapter<AdapterKosList.ViewHolder> {

    private Context context;
    private List<KosData> kosDataList;

    public static final String KEY_IMAGE = "image";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_PRICE = "price";
    public static final String KEY_FAC = "facility";
    public static final String KEY_DESC = "address";
    public static final String KEY_LAT = "lat";
    public static final String KEY_LNG = "lng";

    public  AdapterKosList (List<KosData> kosDataList, Context context){
        this.kosDataList = kosDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterKosList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.kost_list_template,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterKosList.ViewHolder holder, final int position) {

        Log.d("iniada","jenjen");
        final KosData kosData = kosDataList.get(position);

        holder.namaKos.setText(kosData.getKosName());
        holder.hargaKos.setText(kosData.getKosPrice());
        holder.fasilitasKos.setText(kosData.getKosFacility());

        Picasso.with(context).load(kosData.getPhoto_url()).into(holder.imageView);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KosData kosData1 = kosDataList.get(position);
                Intent intent = new Intent(v.getContext(),KosDetail.class);
                intent.putExtra(KEY_ID,kosData1.getId());
                intent.putExtra(KEY_NAME,kosData1.getKosName());
                intent.putExtra(KEY_PRICE,kosData1.getKosPrice());
                intent.putExtra(KEY_FAC,kosData1.getKosFacility());
                intent.putExtra(KEY_DESC,kosData1.getKosDesc());
                intent.putExtra(KEY_LAT,kosData1.getKosLatitude());
                intent.putExtra(KEY_LNG,kosData1.getKosLongitude());
                intent.putExtra(KEY_IMAGE,kosData1.getPhoto_url());
                v.getContext().startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return kosDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView namaKos;
        TextView fasilitasKos;
        TextView hargaKos;
        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img_kos);
            namaKos = itemView.findViewById(R.id.namakos);
            fasilitasKos = itemView.findViewById(R.id.fasilitas);
            hargaKos = itemView.findViewById(R.id.hargakos);
            relativeLayout = itemView.findViewById(R.id.relativelayout);

        }
    }
}
