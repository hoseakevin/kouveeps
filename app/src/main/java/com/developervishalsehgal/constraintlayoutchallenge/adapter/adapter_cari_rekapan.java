package com.developervishalsehgal.constraintlayoutchallenge.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.developervishalsehgal.constraintlayoutchallenge.R;
import com.developervishalsehgal.constraintlayoutchallenge.helper.RestManager;
import com.developervishalsehgal.constraintlayoutchallenge.helper.apidata;
import com.developervishalsehgal.constraintlayoutchallenge.oop.Item;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dafidzeko on 5/11/2016.
 */
public class adapter_cari_rekapan extends RecyclerView.Adapter<adapter_cari_rekapan.Holder> {

    private static final String TAG = adapter_cari_rekapan.class.getSimpleName();
    private List<Item> mtr = new ArrayList<>();
    private final data_kirim mListen;
    apidata mApiService;
    private RestManager restManager;
    ProgressDialog loading;
    private Context context;

    public adapter_cari_rekapan(data_kirim listener) {
        mtr = new ArrayList<>();
        mListen = listener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_rekap_penjualan, parent, false);
        context = parent.getContext();
        return new Holder(row);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        final Item daftar = mtr.get(position);
        holder.id_transaksi.setText(daftar.getNO_TRANSAKSI());
        holder.nama_pembeli.setText(daftar.getNAMA_CUSTOMER());
        holder.total.setText(daftar.getTOTAL_TRANSAKSI());
        holder.tanggal.setText(daftar.getTANGGAL_TRANSAKSI());
         }

    @Override
    public int getItemCount() {
        return mtr.size();
    }


    public void adddata(Item daftar_guru) {
        //Log.d(TAG,bunga.getFoto());
        //   Log.d(TAG,bunga.getFoto());
        mtr.add(daftar_guru);
        notifyDataSetChanged();
    }

    public void clear() {
        mtr.clear();
        notifyDataSetChanged();
    }

    public Item getAmbildata(int position) {
        return mtr.get(position);
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nama_pembeli,total,tanggal,id_transaksi;
        ImageView image_view;

        public Holder(View itemView) {
            super(itemView);
            nama_pembeli = (TextView) itemView.findViewById(R.id.nama_pembeli);
            total = (TextView) itemView.findViewById(R.id.total);
            tanggal = itemView.findViewById(R.id.tanggal);
            id_transaksi = itemView.findViewById(R.id.id_transaksi);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            mListen.onClick(getLayoutPosition());
        }
    }

    public interface data_kirim {
        void onClick(int position);
    }

}
