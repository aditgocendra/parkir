package com.trex.parkirBDL.model;

public class datasetfire {

    private String nama_area,alamat,date,time,harga,profilename,ruang_lingkup,image,pid;

    public datasetfire() {



    }

    public datasetfire(String nama_area, String alamat, String date, String time, String harga, String profilename, String ruang_lingkup, String image, String pid) {
        this.nama_area = nama_area;
        this.alamat = alamat;
        this.date = date;
        this.time = time;
        this.harga = harga;
        this.profilename = profilename;
        this.ruang_lingkup = ruang_lingkup;
        this.image = image;
        this.pid = pid;
    }

    public String getNama_area() {
        return nama_area;
    }

    public void setNama_area(String nama_area) {
        this.nama_area = nama_area;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getProfilename() {
        return profilename;
    }

    public void setProfilename(String profilename) {
        this.profilename = profilename;
    }

    public String getRuang_lingkup() {
        return ruang_lingkup;
    }

    public void setRuang_lingkup(String ruang_lingkup) {
        this.ruang_lingkup = ruang_lingkup;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
