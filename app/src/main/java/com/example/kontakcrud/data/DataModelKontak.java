package com.example.kontakcrud.data;

public class DataModelKontak {
    private String id_kontak, nama_depan, nama_belakang;

    public DataModelKontak(){}

    public DataModelKontak(String id_kontak, String nama_depan, String nama_belakang) {
        this.id_kontak = id_kontak;
        this.nama_depan = nama_depan;
        this.nama_belakang = nama_belakang;
    }

    public String getId_kontak() {
        return id_kontak;
    }

    public void setId_kontak(String id_kontak) {
        this.id_kontak = id_kontak;
    }

    public String getNama_depan() {
        return nama_depan;
    }

    public void setNama_depan(String nama_depan) {
        this.nama_depan = nama_depan;
    }

    public String getNama_belakang() {
        return nama_belakang;
    }

    public void setNama_belakang(String nama_belakang) {
        this.nama_belakang = nama_belakang;
    }
}
