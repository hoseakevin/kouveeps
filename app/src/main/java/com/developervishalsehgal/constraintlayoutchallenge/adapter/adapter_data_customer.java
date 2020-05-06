package com.developervishalsehgal.constraintlayoutchallenge.adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.developervishalsehgal.constraintlayoutchallenge.R;
import com.developervishalsehgal.constraintlayoutchallenge.helper.RestManager;
import com.developervishalsehgal.constraintlayoutchallenge.helper.apidata;
import com.developervishalsehgal.constraintlayoutchallenge.oop.Item;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class adapter_data_customer extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public final int TYPE_DATA = 0;
    public final int TYPE_LOAD = 1;

    static Context context;
    List<Item> kampus;
    adapter_data_customer.OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;

    /*
     * isLoading - to set the remote loading and complete status to fix back to back load more call
     * isMoreDataAvailable - to set whether more data from server available or not.
     * It will prevent useless load more request even after all the server data loaded
     * */

    public adapter_data_customer(Context context, List<Item> movies) {
        this.context = context;
        this.kampus = movies;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if(viewType==TYPE_DATA){
            return new adapter_data_customer.Data_customer_Holder(inflater.inflate(R.layout.list_customer,parent,false));
        }else{
            return new adapter_data_customer.LoadHolder(inflater.inflate(R.layout.list_load,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(position>=getItemCount()-1 && isMoreDataAvailable && !isLoading && loadMoreListener!=null){
            isLoading = true;
            loadMoreListener.onLoadMore();
        }

        if(getItemViewType(position)==TYPE_DATA){
            ((adapter_data_customer.Data_customer_Holder)holder).bindData(kampus.get(position));
        }
        //No else part needed as load holder doesn't bind any data
    }

    @Override
    public int getItemViewType(int position) {
        if(kampus.get(position).getKoneksi().equals("200")){
            return TYPE_DATA;
        }else{
            return TYPE_LOAD;
        }
    }

    @Override
    public int getItemCount() {
        return kampus.size();
    }

    /* VIEW HOLDERS */

    static class Data_customer_Holder extends RecyclerView.ViewHolder {
        TextView id_customer,nama_customer,alamat,phone,tanggal, tgl_lahir;
        ProgressDialog loading;
        apidata mApiService;
        private RestManager restManager;

        public Data_customer_Holder(View itemView) {
            super(itemView);
            id_customer = (TextView) itemView.findViewById(R.id.id_customer);
            nama_customer = (TextView) itemView.findViewById(R.id.nama_customer);
            tgl_lahir = (TextView) itemView.findViewById(R.id.tanggal_lahir_customer);
            phone = (TextView) itemView.findViewById(R.id.phone_customer) ;
            alamat = (TextView) itemView.findViewById(R.id.alamat_customer);

            tanggal = itemView.findViewById(R.id.tanggalc);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "hallo",Toast.LENGTH_LONG).show();
                }
            });

            restManager = new RestManager();
            mApiService = restManager.ambil_data();
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setMessage("Apakah anda yakin menghapus data ?");
                    alertDialogBuilder.setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    loading = ProgressDialog.show(context, null, "Harap Tunggu...", true, false);

                                    mApiService.delete_customer(id_customer.getText().toString())
                                            .enqueue(new Callback<ResponseBody>() {
                                                @Override
                                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                    if (response.isSuccessful()){
                                                        Log.d("hasilRetro", response.message().toString());
                                                        loading.dismiss();
                                                        Toast.makeText(context, "Berhasil delete data", Toast.LENGTH_SHORT).show();

                                                    } else {
                                                        loading.dismiss();
                                                        Toast.makeText(context, "Gagal delete data", Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                    loading.dismiss();
                                                    Toast.makeText(context, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            });

                    alertDialogBuilder.setNegativeButton("No",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {

                                }
                            });

                    //Showing the alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    return false;
                }
            });
        }

        void bindData(Item movieModel){
            id_customer.setText(movieModel.getID_CUSTOMER());
            nama_customer.setText(movieModel.getNAMA_CUSTOMER());
            tgl_lahir.setText(movieModel.getTGL_LAHIR_CUSTOMER());
            phone.setText(movieModel.getPHONE_CUSTOMER());

            alamat.setText(movieModel.getALAMAT_CUSTOMER());
            tanggal.setText(movieModel.getTANGGAL_DIBUAT());
        }
    }

    static class LoadHolder extends RecyclerView.ViewHolder{
        public LoadHolder(View itemView) {
            super(itemView);
        }
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }
//     adapter.notifyDataChanged();

    /* notifyDataSetChanged is final method so we can't override it
         call adapter.notifyDataChanged(); after update the list
*/    public void notifyDataChanged(){
        notifyDataSetChanged();
        isLoading = false;
    }


    public void clear() {
        int size = kampus.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                kampus.remove(0);
            }

            notifyItemRangeRemoved(0, size);
        }
    }

    public Item getAmbildata(int position) {
        return kampus.get(position);
    }

    public interface OnLoadMoreListener{
        void onLoadMore();
    }

    public void setLoadMoreListener(adapter_data_customer.OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    public interface data_kirim {
        void onClick(int position);
    }
}
