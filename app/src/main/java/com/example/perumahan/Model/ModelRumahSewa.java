package com.example.perumahan.Model;

public class ModelRumahSewa {

    private String namaRumah, statusRumah, alamatRumah;
    private int gambarRumah;

    public ModelRumahSewa(String namaRumah, String statusRumah, String alamatRumah, int gambarRumah) {
        this.namaRumah = namaRumah;
        this.statusRumah = statusRumah;
        this.alamatRumah = alamatRumah;
        this.gambarRumah = gambarRumah;
    }

    public String getNamaRumah() {
        return namaRumah;
    }

    public void setNamaRumah(String namaRumah) {
        this.namaRumah = namaRumah;
    }

    public String getStatusRumah() {
        return statusRumah;
    }

    public void setStatusRumah(String statusRumah) {
        this.statusRumah = statusRumah;
    }

    public String getAlamatRumah() {
        return alamatRumah;
    }

    public void setAlamatRumah(String alamatRumah) {
        this.alamatRumah = alamatRumah;
    }

    public int getGambarRumah() {
        return gambarRumah;
    }

    public void setGambarRumah(int gambarRumah) {
        this.gambarRumah = gambarRumah;
    }
}
