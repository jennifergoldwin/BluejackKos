package com.jenjenfuture.bluejackkos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterBookingList extends RecyclerView.Adapter<AdapterBookingList.ViewHolder> {

    private HistoryTransaction historyTransaction;
    private Context context;
    private List<BookingTransaction> bookingTransactionList = new ArrayList<>();
    private DBTransaction dbTransaction ;
    private String bookId;

    public AdapterBookingList(HistoryTransaction historyTransaction,String userId){
        this.historyTransaction = historyTransaction;
        this.context = historyTransaction.getApplicationContext();
        dbTransaction = DBTransaction.getInstance(context);
        bookingTransactionList.addAll(dbTransaction.filterListBooking(userId));

    }
    @NonNull
    @Override
    public AdapterBookingList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_transaction_template,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBookingList.ViewHolder holder, int position) {

        final BookingTransaction bookingTransaction = bookingTransactionList.get(position);

        holder.bookId.setText(bookingTransaction.getBookingId());
        holder.bookDate.setText(bookingTransaction.getBookingDate());
        holder.fasilitas.setText(bookingTransaction.getKosFacility());
        holder.namaKos.setText(bookingTransaction.getKosName());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(v.getRootView().getContext());

                alert.setTitle("Want to cancel this transaction?")
                        .setIcon(R.drawable.alert)
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                bookId = bookingTransaction.getBookingId();
                                dbTransaction.deleteBooking(bookId);
                                historyTransaction.refresh();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                alert.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return bookingTransactionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView bookDate;
        TextView namaKos;
        TextView bookId;
        TextView fasilitas;

        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookDate = itemView.findViewById(R.id.bookdate);
            namaKos = itemView.findViewById(R.id.booknama);
            bookId = itemView.findViewById(R.id.bookid);
            fasilitas = itemView.findViewById(R.id.bookfasilitas);
            relativeLayout = itemView.findViewById(R.id.relativelayout1);
        }
    }
}
