package com.example.kontakcrud.data;

/**
 * Created by KUNCORO on 23/04/2017.
 */

public class Data {
    private String id_jabatan, nama_jab;

    public Data() {
    }

    public Data(String id_jabatan, String nama_jab) {
        this.id_jabatan= id_jabatan;
        this.nama_jab = nama_jab;
    }

    public String getId_jabatan() {
        return id_jabatan;
    }

    public void setId_jabatan(String id_jabatan) {
        this.id_jabatan= id_jabatan;
    }

    public String getNama_jab() {
        return nama_jab;
    }

    public void setNama_jab(String nama_jab) {
        this.nama_jab = nama_jab;
    }
}
