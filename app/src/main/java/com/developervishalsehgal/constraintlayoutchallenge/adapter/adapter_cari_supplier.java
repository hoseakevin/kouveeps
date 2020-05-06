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

public class adapter_cari_supplier extends RecyclerView.Adapter<adapter_cari_supplier.Holder> {
    private static final String TAG = adapter_cari_produk.class.getSimpleName();
    private List<Item> mtr = new ArrayList<>();
    private final data_kirim mListen;
    apidata mApiService;
    private RestManager restManager;
    ProgressDialog loading;
    private Context context;

    public adapter_cari_supplier(data_kirim listener) {
        mtr = new ArrayList<>();
        mListen = listener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_supplier, parent, false);
        context = parent.getContext();
        return new Holder(row);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        final Item daftar = mtr.get(position);
        holder.id_supplier.setText(daftar.getID_SUPPLIER());
        holder.nama_supplier.setText(daftar.getNAMA_SUPPLIER());
        holder.alamat_supplier.setText(daftar.getALAMAT_SUPPLIER());
        holder.phone_supplier.setText(daftar.getPHONE_SUPPLIER());
        holder.tanggal.setText(daftar.getTanggal());
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
        TextView id_supplier,nama_supplier,alamat_supplier,phone_supplier,tanggal;

        public Holder(View itemView) {
            super(itemView);
            id_supplier = (TextView) itemView.findViewById(R.id.id_supplier);
            nama_supplier = (TextView) itemView.findViewById(R.id.nama_supplier);
            alamat_supplier = (TextView) itemView.findViewById(R.id.alamat_supplier);
            phone_supplier = (TextView) itemView.findViewById(R.id.phone_supplier);
            tanggal = (TextView) itemView.findViewById(R.id.tanggals);
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
