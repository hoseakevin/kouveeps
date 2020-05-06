package com.developervishalsehgal.constraintlayoutchallenge.helper;

//import android.telecom.Call;

import com.developervishalsehgal.constraintlayoutchallenge.oop.Item;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by dafidzeko on 5/11/2016.
 */
public interface apidata {


    @FormUrlEncoded
    @POST("login_cs.php")
    Call<ResponseBody> loginpegawai(@Field("ID_PEGAWAI") String ID_PEGAWAI,
                                    @Field("PASSWORD") String PASSWORD);

    @GET("list_produk.php")
    Call<List<Item>> getdata_produk(@Query("index") int index);

    @GET("spinner_produk.php")
    Call<List<Item>> getspiner_produk();

    @GET("spin_id_trans.php")
    Call<List<Item>> getspiner_id_trans();

    @GET("spinner_customer.php")
    Call<List<Item>> getspiner_customer();

    @GET("list_rekap_penjualan.php")
    Call<List<Item>> getdata_rekap(@Query("ID_PEGAWAI") String ID_PEGAWAI, @Query("index") int index);

    @GET("list_keranjang.php")
    Call<List<Item>> getkeranjang(@Query("NO_TRANSAKSI") String NO_TRANSAKSI, @Query("index") int index);

    @FormUrlEncoded
    @POST("delete_produk.php/{ID_PRODUK}")
    Call<ResponseBody> delete_produk(@Field("ID_PRODUK") String ID_PRODUK);

    @FormUrlEncoded
    @POST("delete_detil_transaksi.php/{ID_DETIL_TRANSAKSI_PRODUK}")
    Call<ResponseBody> delete_transaksi(@Field("ID_DETIL_TRANSAKSI_PRODUK") String ID_DETIL_TRANSAKSI_PRODUK);

    @FormUrlEncoded
    @POST("delete_rekapan.php/{id_transaksi}")
    Call<ResponseBody> delete_rekapan(@Field("id_transaksi") String id_transaksi);

    @FormUrlEncoded
    @POST("add_produk.php")
    Call<ResponseBody> add_barang(@Field("NAMA_PRODUK") String NAMA_PRODUK, @Field("STOCK") String STOCK,
                                @Field("HARGA") String HARGA,
                                @Field("GAMBAR") String GAMBAR);
    @FormUrlEncoded
    @POST("simpan_rekap_transaksi.php")
    Call<ResponseBody> add_transaksi(@Field("NO_TRANSAKSI") String NO_TRANSAKSI,
                                     @Field("TOTAL_TRANSAKSI") String TOTAL_TRANSAKSI,
                             @Field("ID_CUSTOMER") String ID_CUSTOMER,
                             @Field("ID_PEGAWAI") String ID_PEGAWAI);

    @FormUrlEncoded
    @POST("transaksi.php")
    Call<ResponseBody> add_dtl_transaksi(@Field("ID_PRODUK") String ID_PRODUK,
                                     @Field("JUMLAH_PRODUK") String JUMLAH_PRODUK,
                                         @Field("SUB_TOTAL_PRODUK") String SUB_TOTAL_PRODUK);

    @FormUrlEncoded
    @POST("transaksi.php")
    Call<ResponseBody> add_dtl_transaksi1(@Field("NO_TRANSAKSI") String NO_TRANSAKSI,
                                          @Field("ID_PRODUK") String ID_PRODUK,
                                         @Field("JUMLAH_PRODUK") String JUMLAH_PRODUK,
                                         @Field("SUB_TOTAL_PRODUK") String SUB_TOTAL_PRODUK);

    @GET("list_cari_rekapan.php")
    Call<List<Item>> getcari_rekapan(@Query("ID_PEGAWAI") String ID_PEGAWAI,
                                     @Query("TANGGAL_TRANSAKSI") String TANGGAL_TRANSAKSI);

    @GET("total_bayar.php")
    Call<List<Item>> get_total_bayar(@Query("NO_TRANSAKSI") String NO_TRANSAKSI);




    @FormUrlEncoded
    @POST("update_produk.php")
    Call<ResponseBody> update_data_produk(@Field("ID_PRODUK") String ID_PRODUK,
                                     @Field("NAMA_PRODUK") String NAMA_PRODUK,
                                     @Field("STOCK") String STOCK,
                                     @Field("HARGA") String HARGA);

    @GET("list_cari_produk.php")
    Call<List<Item>> list_cari_produk(@Query("NAMA_PRODUK") String NAMA_PRODUK);

    @FormUrlEncoded
    @POST("update_produk.php")
    Call<ResponseBody> update_data_produk_gambar(@Field("ID_PRODUK") String ID_PRODUK,
                                          @Field("NAMA_PRODUK") String NAMA_PRODUK,
                                          @Field("STOCK") String STOCK,
                                          @Field("HARGA") String HARGA, @Field("GAMBAR") String GAMBAR);

    @FormUrlEncoded
    @POST("update_transaksi.php")
    Call<ResponseBody> update_rekap(@Field("ID_DETIL_TRANSAKSI_PRODUK") String ID_DETIL_TRANSAKSI_PRODUK,
                                    @Field("ID_PRODUK") String ID_PRODUK,
                                    @Field("JUMLAH_PRODUK") String JUMLAH_PRODUK,
                                    @Field("SUB_TOTAL_PRODUK") String SUB_TOTAL_PRODUK);


    //SUPPLIER
    @GET("list_supplier.php")
    Call<List<Item>> getdata_supplier(@Query("index") int index);

    @GET("list_cari_supplier.php")
    Call<List<Item>> list_cari_supplier(@Query("NAMA_SUPPLIER") String NAMA_SUPPLIER);

    @FormUrlEncoded
    @POST("delete_supplier.php/{ID_SUPPLIER}")
    Call<ResponseBody> delete_supplier(@Field("ID_SUPPLIER") String ID_SUPPLIER);

    @FormUrlEncoded
    @POST("add_supplier.php")
    Call<ResponseBody> add_supplier(@Field("NAMA_SUPPLIER") String NAMA_SUPPLIER,
                                  @Field("ALAMAT_SUPPLIER") String ALAMAT_SUPPLIER,
                                  @Field("PHONE_SUPPLIER") String PHONE_SUPPLIER);

    @FormUrlEncoded
    @POST("update_supplier.php")
    Call<ResponseBody> update_data_supplier(@Field("ID_SUPPLIER") String ID_SUPPLIER,
                                            @Field("NAMA_SUPPLIER") String NAMA_SUPPLIER,
                                            @Field("ALAMAT_SUPPLIER") String ALAMAT_SUPPLIER,
                                            @Field("PHONE_SUPPLIER") String PHONE_SUPPLIER);

    //LAYANAN
    @GET("list_layanan.php")
    Call<List<Item>> getdata_layanan(@Query("index") int index);

    @GET("list_cari_layanan.php")
    Call<List<Item>> list_cari_layanan(@Query("NAMA_LAYANAN") String NAMA_LAYANAN);

    @FormUrlEncoded
    @POST("delete_layanan.php/{ID_LAYANAN}")
    Call<ResponseBody> delete_layanan(@Field("ID_LAYANAN") String ID_LAYANAN);

    @FormUrlEncoded
    @POST("add_layanan.php")
    Call<ResponseBody> add_layanan(@Field("NAMA_LAYANAN") String NAMA_LAYANAN,
                                    @Field("HARGA") String HARGA,
                                    @Field("ID_UKURAN") String ID_UKURAN,
                                    @Field("ID_JENISHEWAN") String ID_JENISHEWAN);

    @FormUrlEncoded
    @POST("update_layanan.php")
    Call<ResponseBody> update_data_layanan(@Field("ID_LAYANAN") String ID_LAYANAN,
                                            @Field("NAMA_LAYANAN") String NAMA_LAYANAN,
                                            @Field("HARGA") String HARGA,
                                           @Field("ID_UKURAN") String ID_UKURAN,
                                          @Field("ID_JENISHEWAN") String ID_JENISHEWAN);

    //CUSTOMER
    @GET("list_customer.php")
    Call<List<Item>> getdata_customer(@Query("index") int index);

    @GET("list_cari_customer.php")
    Call<List<Item>> list_cari_customer(@Query("NAMA_CUSTOMER") String NAMA_CUSTOMER);

    @FormUrlEncoded
    @POST("delete_customer.php/{ID_CUSTOMER}")
    Call<ResponseBody> delete_customer(@Field("ID_CUSTOMER") String ID_CUSTOMER);

    @FormUrlEncoded
    @POST("add_customer.php")
    Call<ResponseBody> add_customer(@Field("NAMA_CUSTOMER") String NAMA_CUSTOMER,
                                   @Field("TGL_LAHIR_CUSTOMER") String TGL_LAHIR_CUSTOMER,
                                   @Field("PHONE_CUSTOMER") String PHONE_CUSTOMER,
                                    @Field("ALAMAT_CUSTOMER") String ALAMAT_CUSTOMER,
                                    @Field("ID_PEGAWAI") String ID_PEGAWAI);

    @FormUrlEncoded
    @POST("update_customer.php")
    Call<ResponseBody> update_data_customer(@Field("ID_CUSTOMER") String ID_CUSTOMER,
                                            @Field("NAMA_CUSTOMER") String NAMA_CUSTOMER,
                                            @Field("TGL_LAHIR_CUSTOMER") String TGL_LAHIR_CUSTOMER,
                                            @Field("PHONE_CUSTOMER") String PHONE_CUSTOMER,
                                            @Field("ALAMAT_CUSTOMER") String ALAMAT_CUSTOMER,
                                            @Field("ID_PEGAWAI") String ID_PEGAWAI);
}
