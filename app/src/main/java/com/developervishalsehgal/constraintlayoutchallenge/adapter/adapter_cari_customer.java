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

public class adapter_cari_customer extends RecyclerView.Adapter<adapter_cari_customer.Holder> {
    private static final String TAG = adapter_cari_customer.class.getSimpleName();
    private List<Item> mtr = new ArrayList<>();
    private final data_kirim mListen;
    apidata mApiService;
    private RestManager restManager;
    ProgressDialog loading;
    private Context context;

    public adapter_cari_customer(data_kirim listener) {
        mtr = new ArrayList<>();
        mListen = listener;
    }

    @Override
    public adapter_cari_customer.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_customer, parent, false);
        context = parent.getContext();
        return new adapter_cari_customer.Holder(row);
    }

    @Override
    public void onBindViewHolder(adapter_cari_customer.Holder holder, int position) {
        final Item daftar = mtr.get(position);
        holder.id_customer.setText(daftar.getID_CUSTOMER());
        holder.nama_customer.setText(daftar.getNAMA_CUSTOMER());
        holder.tgl_lahir.setText(daftar.getTGL_LAHIR_CUSTOMER());
        holder.phone_customer.setText(daftar.getPHONE_CUSTOMER());
        holder.alamat_customer.setText(daftar.getALAMAT_CUSTOMER());
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
        TextView id_customer,nama_customer,alamat_customer,phone_customer,tgl_lahir,tanggal;

        public Holder(View itemView) {
            super(itemView);
            id_customer = (TextView) itemView.findViewById(R.id.id_customer);
            nama_customer = (TextView) itemView.findViewById(R.id.nama_customer);
            tgl_lahir = (TextView) itemView.findViewById(R.id.tanggal_lahir_customer);
            phone_customer = (TextView) itemView.findViewById(R.id.phone_customer);
            alamat_customer = (TextView) itemView.findViewById(R.id.alamat_customer);
            tanggal = (TextView) itemView.findViewById(R.id.tanggalc);
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
