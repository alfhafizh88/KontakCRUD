package com.example.kontakcrud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.kontakcrud.adapter.AdapterKontak;
import com.example.kontakcrud.app.AppController;
import com.example.kontakcrud.data.DataModelKontak;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class TampilSemuaKontak extends AppCompatActivity implements ListView.OnItemClickListener, View.OnClickListener{

    /*private ImageView imageView;*/
    private ListView listView;
    private String JSON_STRING;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_semua_kontak);
        listView = (ListView) findViewById(R.id.listView);
        /*imageView=(ImageView)findViewById(R.id.imgClose);*/
        listView.setOnItemClickListener(this);
        /*imageView.setOnClickListener(this);*/
        getJSON();
    }

    private void showKontak() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id_kontak = jo.getString(konfigurasi.TAG_ID_KONTAK);
                String nama_depan = jo.getString(konfigurasi.TAG_NAMA_KONTAK);
                String nama_belakang = jo.getString(konfigurasi.TAG_NAMA_BELAKANG);

                HashMap<String,String> kontak = new HashMap<>();
                kontak.put(konfigurasi.TAG_ID_KONTAK,id_kontak);
                kontak.put(konfigurasi.TAG_NAMA_KONTAK,nama_depan);
                kontak.put(konfigurasi.TAG_NAMA_BELAKANG,nama_belakang);
                list.add(kontak);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                TampilSemuaKontak.this, list, R.layout.list_item1,
                new String[]{konfigurasi.TAG_ID_KONTAK,konfigurasi.TAG_NAMA_KONTAK, konfigurasi.TAG_NAMA_BELAKANG},
                new int[]{R.id.id, R.id.name, R.id.name2});

        listView.setAdapter(adapter);
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(TampilSemuaKontak.this,"Mengambil Data","Mohon Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showKontak();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(konfigurasi.URL_GET_ALL_KONTAK);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }


    /*private void callData() {
        listData.clear();
        adapter2.notifyDataSetChanged();
        swipe.setRefreshing(true);


        // Creating volley request obj
        JsonArrayRequest jArr = new JsonArrayRequest(url_data, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e(TAG, response.toString());

                // Parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        DataModelKontak item = new DataModelKontak();

                        item.setId_kontak(obj.getString(TAG_ID_KONTAK));
                        item.setNama_depan(obj.getString(TAG_NAMA_KONTAK));


                        listData.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                // notifying list adapter about data changes
                // so that it renders the list view with updated data
                adapter2.notifyDataSetChanged();
                swipe.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(TampilSemuaKontak.this, error.getMessage(), Toast.LENGTH_LONG).show();
                swipe.setRefreshing(false);
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jArr);
    }*/

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, TampilKontak.class);
        HashMap<String,String> map =(HashMap)adapterView.getItemAtPosition(i);
        String kontakId = map.get(konfigurasi.TAG_ID_KONTAK).toString();
        intent.putExtra(konfigurasi.KONTAK_ID,kontakId);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {

    }
}
