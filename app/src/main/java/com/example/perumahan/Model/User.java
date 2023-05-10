package com.example.perumahan.Model;

public class User {
    private int id;
    private String name, email, gender, namaPengguna, kodePos, rt, rw, detailAlamat;

    public User(int id, String name, String email, String gender) {
        this.id = id;
        this.email = email;
        this.gender = gender;
        this.name = name;
        this.namaPengguna = namaPengguna;
        this.kodePos = kodePos;
        this.rt = rt;
        this.rw = rw;
        this.detailAlamat = detailAlamat;
    }

    public String getNamaPengguna() {
        return namaPengguna;
    }

    public void setNamaPengguna(String namaPengguna) {
        this.namaPengguna = namaPengguna;
    }

    public String getKodePos() {
        return kodePos;
    }

    public void setKodePos(String kodePos) {
        this.kodePos = kodePos;
    }

    public String getRt() {
        return rt;
    }

    public void setRt(String rt) {
        this.rt = rt;
    }

    public String getRw() {
        return rw;
    }

    public void setRw(String rw) {
        this.rw = rw;
    }

    public String getDetailAlamat() {
        return detailAlamat;
    }

    public void setDetailAlamat(String detailAlamat) {
        this.detailAlamat = detailAlamat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
