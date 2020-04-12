package com.example.kontakcrud;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GambarActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listView;
    private static final String newurl = "http://192.168.43.138/kontak/getandroidosnames.php";
    private String json;
    private JSONArray urls;
    public  Getjson getjsonobj;
    Customadapter Customadapter;
    public static final String BITMAP_ID = "BITMAP_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gambar);
        listView = (ListView) findViewById(R.id.lst);
        listView.setOnItemClickListener(this);
        getURLs();
    }

    private void getImages() {
        class GetImages extends AsyncTask<Void, Void, Void> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(GambarActivity.this, "Memuat Data", "Mohon tunggu...", false, false);
            }
            @Override
            protected void onPostExecute(Void v) {
                super.onPostExecute(v);
                loading.dismiss();

                Customadapter = new Customadapter(GambarActivity.this, getjsonobj.Android_Name ,getjsonobj.bitmaps);
                listView.setAdapter(Customadapter);
            }
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    getjsonobj.getAllImages();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }
        GetImages getImages = new GetImages();
        getImages.execute();
    }
    private void getURLs() {
        class GetURLs extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(GambarActivity.this, "Loading...", "Please Wait...", true, true);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                getjsonobj = new Getjson(s);
                getImages();
            }
            @Override
            protected String doInBackground(String... strings) {
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetURLs gu = new GetURLs();
        gu.execute(newurl);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, ViewFullImage.class);
        intent.putExtra(BITMAP_ID,i);
        startActivity(intent);
    }


}
