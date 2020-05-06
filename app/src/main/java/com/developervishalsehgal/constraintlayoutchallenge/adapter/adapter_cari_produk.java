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
public class adapter_cari_produk extends RecyclerView.Adapter<adapter_cari_produk.Holder> {

    private static final String TAG = adapter_cari_produk.class.getSimpleName();
    private List<Item> mtr = new ArrayList<>();
    private final data_kirim mListen;
    apidata mApiService;
    private RestManager restManager;
    ProgressDialog loading;
    private Context context;

    public adapter_cari_produk(data_kirim listener) {
        mtr = new ArrayList<>();
        mListen = listener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_produk, parent, false);
        context = parent.getContext();
        return new Holder(row);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        final Item daftar = mtr.get(position);
        holder.id_produk.setText(daftar.getID_PRODUK());
        holder.nama_produk.setText(daftar.getNAMA_PRODUK());
        holder.harga.setText(daftar.getHARGA());
        holder.stok.setText(daftar.getSTOCK());
        Picasso.with(context).load(daftar.getGAMBAR()).into(holder.image_view);
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
        TextView id_produk,nama_produk,harga,stok;
        ImageView image_view;

        public Holder(View itemView) {
            super(itemView);
            id_produk = (TextView) itemView.findViewById(R.id.id_produk);
            nama_produk = (TextView) itemView.findViewById(R.id.nama_produk);
            harga = (TextView) itemView.findViewById(R.id.harga);
            stok = itemView.findViewById(R.id.stok);
            image_view = itemView.findViewById(R.id.image_view);
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
