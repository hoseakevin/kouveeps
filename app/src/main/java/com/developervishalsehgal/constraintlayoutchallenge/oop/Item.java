package com.developervishalsehgal.constraintlayoutchallenge.oop;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class Item implements Serializable {
    @Expose
    String ID_PRODUK,NAMA_PRODUK,STOCK,GAMBAR, MIN_STOCK,HARGA,koneksi,id_transaksi,tanggal,ID_CUSTOMER,NAMA_CUSTOMER, NO_TRANSAKSI, total_bayar, TANGGAL_TRANSAKSI,
            TOTAL_TRANSAKSI, JUMLAH_PRODUK, SUB_TOTAL_PRODUK, ID_DETIL_TRANSAKSI_PRODUK, ID_SUPPLIER, NAMA_SUPPLIER, ALAMAT_SUPPLIER, PHONE_SUPPLIER, TANGGAL_DIBUAT,
            ID_LAYANAN, NAMA_LAYANAN, ID_UKURAN, ID_JENISHEWAN, TGL_LAHIR_CUSTOMER, PHONE_CUSTOMER, ALAMAT_CUSTOMER, ID_PEGAWAI;

    public String getID_PEGAWAI() {
        return ID_PEGAWAI;
    }

    public void setID_PEGAWAI(String ID_PEGAWAI) {
        this.ID_PEGAWAI = ID_PEGAWAI;
    }

    public String getJUMLAH_PRODUK() {
        return JUMLAH_PRODUK;
    }

    public void setJUMLAH_PRODUK(String JUMLAH_PRODUK) {
        this.JUMLAH_PRODUK = JUMLAH_PRODUK;
    }

    public String getSUB_TOTAL_PRODUK() {
        return SUB_TOTAL_PRODUK;
    }

    public void setSUB_TOTAL_PRODUK(String SUB_TOTAL_PRODUK) {
        this.SUB_TOTAL_PRODUK = SUB_TOTAL_PRODUK;
    }

    public String getID_DETIL_TRANSAKSI_PRODUK() {
        return ID_DETIL_TRANSAKSI_PRODUK;
    }

    public void setID_DETIL_TRANSAKSI_PRODUK(String ID_DETIL_TRANSAKSI_PRODUK) {
        this.ID_DETIL_TRANSAKSI_PRODUK = ID_DETIL_TRANSAKSI_PRODUK;
    }

    public String getTOTAL_TRANSAKSI() {
        return TOTAL_TRANSAKSI;
    }

    public void setTOTAL_TRANSAKSI(String TOTAL_TRANSAKSI) {
        this.TOTAL_TRANSAKSI = TOTAL_TRANSAKSI;
    }

    public String getTANGGAL_TRANSAKSI() {
        return TANGGAL_TRANSAKSI;
    }

    public void setTANGGAL_TRANSAKSI(String TANGGAL_TRANSAKSI) {
        this.TANGGAL_TRANSAKSI = TANGGAL_TRANSAKSI;
    }

    public String getTotal_bayar() {
        return total_bayar;
    }

    public void setTotal_bayar(String total_bayar) {
        this.total_bayar = total_bayar;
    }

    public String getNO_TRANSAKSI() {
        return NO_TRANSAKSI;
    }

    public void setNO_TRANSAKSI(String NO_TRANSAKSI) {
        this.NO_TRANSAKSI = NO_TRANSAKSI;
    }


    public String getID_CUSTOMER() {
        return ID_CUSTOMER;
    }

    public void setID_CUSTOMER(String ID_CUSTOMER) {
        this.ID_CUSTOMER = ID_CUSTOMER;
    }

    public String getNAMA_CUSTOMER() {
        return NAMA_CUSTOMER;
    }

    public void setNAMA_CUSTOMER(String NAMA_CUSTOMER) {
        this.NAMA_CUSTOMER = NAMA_CUSTOMER;
    }

    public String getTGL_LAHIR_CUSTOMER() {
        return TGL_LAHIR_CUSTOMER;
    }

    public void setTGL_LAHIR_CUSTOMER(String TGL_LAHIR_CUSTOMER) {
        this.TGL_LAHIR_CUSTOMER = TGL_LAHIR_CUSTOMER;
    }

    public String getPHONE_CUSTOMER() {
        return PHONE_CUSTOMER;
    }

    public void setPHONE_CUSTOMER(String PHONE_CUSTOMER) {
        this.PHONE_CUSTOMER = PHONE_CUSTOMER;
    }

    public String getALAMAT_CUSTOMER() {
        return ALAMAT_CUSTOMER;
    }

    public void setALAMAT_CUSTOMER(String ALAMAT_CUSTOMER) {
        this.ALAMAT_CUSTOMER = ALAMAT_CUSTOMER;
    }


    public String getId_transaksi() {
        return id_transaksi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public Item (String koneksi){
        this.koneksi = koneksi;
    }

    public String getKoneksi() {
        return koneksi;
    }

    public void setKoneksi(String koneksi) {
        this.koneksi = koneksi;
    }

    public String getHARGA() {
        return HARGA;
    }

    public void setHARGA(String HARGA) {
        this.HARGA = HARGA;
    }

    public String getID_PRODUK() {
        return ID_PRODUK;
    }

    public void setID_PRODUK(String ID_PRODUK) {
        this.ID_PRODUK = ID_PRODUK;
    }

    public String getNAMA_PRODUK() {
        return NAMA_PRODUK;
    }

    public void setNAMA_PRODUK(String NAMA_PRODUK) {
        this.NAMA_PRODUK = NAMA_PRODUK;
    }

    public String getSTOCK() {
        return STOCK;
    }

    public void setSTOCK(String STOCK) {
        this.STOCK = STOCK;
    }

    public String getGAMBAR() {
        return GAMBAR;
    }

    public void setGAMBAR(String GAMBAR) {
        this.GAMBAR = GAMBAR;
    }

    public String getMIN_STOCK() {
        return MIN_STOCK;
    }

    public void setMIN_STOCK(String MIN_STOCK) {
        this.MIN_STOCK = MIN_STOCK;
    }

    public String getID_SUPPLIER() {
        return ID_SUPPLIER;
    }

    public void setID_SUPPLIER(String ID_SUPPLIER) {
        this.ID_SUPPLIER = ID_SUPPLIER;
    }

    public String getNAMA_SUPPLIER() {
        return NAMA_SUPPLIER;
    }

    public void setNAMA_SUPPLIER(String NAMA_SUPPLIER) {
        this.NAMA_SUPPLIER = NAMA_SUPPLIER;
    }

    public String getALAMAT_SUPPLIER() {
        return ALAMAT_SUPPLIER;
    }

    public void setALAMAT_SUPPLIER(String ALAMAT_SUPPLIER) { this.ALAMAT_SUPPLIER = ALAMAT_SUPPLIER; }

    public String getPHONE_SUPPLIER() {
        return PHONE_SUPPLIER;
    }

    public void setPHONE_SUPPLIER(String PHONE_SUPPLIER) {
        this.PHONE_SUPPLIER = PHONE_SUPPLIER;
    }

    public String getTANGGAL_DIBUAT() {
        return TANGGAL_DIBUAT;
    }

    public void setTANGGAL_DIBUAT(String TANGGAL_DIBUAT) { this.TANGGAL_DIBUAT = TANGGAL_DIBUAT; }

    public String getID_LAYANAN() {
        return ID_LAYANAN;
    }

    public void setID_LAYANAN(String ID_LAYANAN) {
        this.ID_LAYANAN = ID_LAYANAN;
    }

    public String getNAMA_LAYANAN() {
        return NAMA_LAYANAN;
    }

    public void setNAMA_LAYANAN(String NAMA_LAYANAN) {
        this.NAMA_LAYANAN = NAMA_LAYANAN;
    }

    public String getID_UKURAN() {
        return ID_UKURAN;
    }

    public void setID_UKURAN(String ID_UKURAN) {
        this.ID_UKURAN = ID_UKURAN;
    }

    public String getID_JENISHEWAN() {
        return ID_JENISHEWAN;
    }

    public void setID_JENISHEWAN(String ID_JENISHEWAN) {
        this.ID_JENISHEWAN = ID_JENISHEWAN;
    }

}
