package com.example.perumahan;

import java.io.Serializable;

public class Perumahan implements Serializable {
    private String id="";
    private String tipe_rumah="";
    private String harga="";
    private String luas_bangunan="";
    private String luas_tanah="";
    private String deskripsi="";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipe_rumah() {
        return tipe_rumah;
    }

    public void setTipe_rumah(String tipe_rumah) {
        this.tipe_rumah = tipe_rumah;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getLuas_bangunan() {
        return luas_bangunan;
    }

    public void setLuas_bangunan(String luas_bangunan) {
        this.luas_bangunan = luas_bangunan;
    }

    public String getLuas_tanah() {
        return luas_tanah;
    }

    public void setLuas_tanah(String luas_tanah) {
        this.luas_tanah = luas_tanah;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
