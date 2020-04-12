package com.example.kontakcrud;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class TampilKontak extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextId, editTextNama, editTextNama2, editTextAlamat, editTextJK, editTextNomor;

    private Button buttonUpdate, buttonDelete;

    private String id_kontak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_kontak);

        Intent intent = getIntent();
        id_kontak = intent.getStringExtra(konfigurasi.KONTAK_ID);

        editTextId = (EditText) findViewById(R.id.editTextID);
        editTextNama = (EditText) findViewById(R.id.editTextNama);
        editTextNama2 = (EditText) findViewById(R.id.editTextNama2);
        editTextAlamat = (EditText) findViewById(R.id.editTextAlamat);
        editTextJK = (EditText) findViewById(R.id.editTextJK);
        editTextNomor = (EditText) findViewById(R.id.editTextNomor);

        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);

        buttonUpdate.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);

        editTextId.setText(id_kontak);

        getKontak();
    }
    private void getKontak() {
        class GetJenis extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilKontak.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showKontak(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(konfigurasi.URL_GET_KONTAK,id_kontak);
                return s;
            }
        }

        GetJenis ge = new GetJenis();
        ge.execute();
    }

    private void showKontak(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            String nama_depan = c.getString(konfigurasi.TAG_NAMA_KONTAK);
            String nama_belakang = c.getString(konfigurasi.TAG_NAMA_BELAKANG);
            String alamat = c.getString(konfigurasi.TAG_ALAMAT);
            String jenis_kelamin = c.getString(konfigurasi.TAG_JK);
            String no_handphone = c.getString(konfigurasi.TAG_NOMOR);

            editTextNama.setText(nama_depan);
            editTextNama2.setText(nama_belakang);
            editTextAlamat.setText(alamat);
            editTextJK.setText(jenis_kelamin);
            editTextNomor.setText(no_handphone);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void updateKontak(){
        final String nama_depan = editTextNama.getText().toString().trim();
        final String nama_belakang = editTextNama2.getText().toString().trim();
        final String alamat = editTextAlamat.getText().toString().trim();
        final String jenis_kelamin = editTextJK.getText().toString().trim();
        final String no_handphone = editTextNomor.getText().toString().trim();

        class UpdateBuku extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilKontak.this,"Updating...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(TampilKontak.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(konfigurasi.KEY_KONTAK_ID,id_kontak);
                hashMap.put(konfigurasi.KEY_NAMA_DEPAN,nama_depan);
                hashMap.put(konfigurasi.KEY_NAMA_BELAKANG,nama_belakang);
                hashMap.put(konfigurasi.KEY_ALAMAT,alamat);
                hashMap.put(konfigurasi.KEY_JENIS_KELAMIN,jenis_kelamin);
                hashMap.put(konfigurasi.KEY_NOMOR,no_handphone);

                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest(konfigurasi.URL_UPDATE_KONTAK,hashMap);
                return s;
            }
        }

        UpdateBuku updateJenis = new UpdateBuku();
        updateJenis.execute();
    }
    private void deleteKontak(){
        class DeleteKontak extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilKontak.this, "Menghapus...", "Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(TampilKontak.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(konfigurasi.URL_DELETE_KONTAK, id_kontak);
                return s;
            }
        }

        DeleteKontak de = new DeleteKontak();
        de.execute();
    }

    private void confirmDeleteKontak(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Kamu Yakin Ingin Menghapus Jenis ini?");

        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteKontak();
                        startActivity(new Intent(TampilKontak.this,TampilSemuaKontak.class));
                    }
                });

        alertDialogBuilder.setNegativeButton("Tidak",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        if(v == buttonUpdate){
            if (editTextNama.getText().toString().length()==0){
                editTextNama.setError("Nama Depan diperlukan!");
            }else if(editTextNama2.getText().toString().length()==0){
                editTextNama2.setError("Nama Belakang diperlukan!");
            }else if(editTextAlamat.getText().toString().length()==0){
                editTextAlamat.setError("Alamat diperlukan!");
            }else if(editTextJK.getText().toString().length()==0){
                editTextJK.setError("Jenis Kelamin diperlukan!");
            }else if(editTextNomor.getText().toString().length()==0){
                editTextNomor.setError("Nomor Handphone diperlukan!");
            }else{
                updateKontak();
            }
        }if(v == buttonDelete){
            confirmDeleteKontak();
        }
    }
}
