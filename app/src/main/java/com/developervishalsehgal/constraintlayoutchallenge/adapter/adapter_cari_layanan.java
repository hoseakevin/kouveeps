package com.developervishalsehgal.constraintlayoutchallenge.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.developervishalsehgal.constraintlayoutchallenge.R;
import com.developervishalsehgal.constraintlayoutchallenge.helper.RestManager;
import com.developervishalsehgal.constraintlayoutchallenge.helper.apidata;
import com.developervishalsehgal.constraintlayoutchallenge.oop.Item;

import java.util.ArrayList;
import java.util.List;

public class adapter_cari_layanan extends RecyclerView.Adapter<adapter_cari_layanan.Holder> {
    private static final String TAG = adapter_cari_produk.class.getSimpleName();
    private List<Item> mtr = new ArrayList<>();
    private final data_kirim mListen;
    apidata mApiService;
    private RestManager restManager;
    ProgressDialog loading;
    private Context context;

    public adapter_cari_layanan(data_kirim listener) {
        mtr = new ArrayList<>();
        mListen = listener;
    }

    @Override
    public adapter_cari_layanan.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layanan, parent, false);
        context = parent.getContext();
        return new adapter_cari_layanan.Holder(row);
    }

    @Override
    public void onBindViewHolder(adapter_cari_layanan.Holder holder, int position) {
        final Item daftar = mtr.get(position);
        holder.id_layanan.setText(daftar.getID_LAYANAN());
        holder.nama_layanan.setText(daftar.getNAMA_LAYANAN());
        holder.harga.setText(daftar.getHARGA());
        holder.tanggal.setText(daftar.getTANGGAL_DIBUAT());
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
        TextView id_layanan,nama_layanan,harga,tanggal;

        public Holder(View itemView) {
            super(itemView);
            id_layanan = (TextView) itemView.findViewById(R.id.id_layanan);
            nama_layanan = (TextView) itemView.findViewById(R.id.nama_layanan);
            harga = (TextView) itemView.findViewById(R.id.hargal);
            tanggal = (TextView) itemView.findViewById(R.id.tanggall);
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
