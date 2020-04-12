package com.example.kontakcrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class Beranda extends AppCompatActivity implements View.OnClickListener {
    private EditText edtNama, edtNamaBlk, edtAlamat, edtJk, edtNomor;

    private Button buttonAdd, buttonView, buttonChoose, buttonDoc;

    private ImageView imageView, logout;
    private Bitmap bitmap;
    private Uri filePath;
    private  int PICK_IMAGE_REQUEST=1;
    public static final String UPLOAD_KEY = "foto";
    private static final int STORAGE_PERMISSION_CODE = 123;

    private SessionHandler session;
    TextView namauser, walletuser;
    String id, username;
    SharedPreferences sharedpreferences;
    public static final String TAG_ID = "id";
    public static final String TAG_USERNAME = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);

        requestStoragePermission();
        /*txt_id = (TextView) findViewById(R.id.txt_id);
        txt_username = (TextView) findViewById(R.id.txt_username);*/
        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        id = getIntent().getStringExtra(TAG_ID);
        username = getIntent().getStringExtra(TAG_USERNAME);

        namauser=findViewById(R.id.namauser);
        walletuser=findViewById(R.id.walletuser);
        walletuser.setText(id);
        namauser.setText(username);

        edtNama = (EditText) findViewById(R.id.edtNama);
        edtNamaBlk = (EditText) findViewById(R.id.edtNamaBlk);
        edtAlamat = (EditText) findViewById(R.id.edtAlamat);
        edtJk = (EditText) findViewById(R.id.edtJk);
        edtNomor = (EditText) findViewById(R.id.edtNomor);

        imageView = (ImageView) findViewById(R.id.imageView);
        logout = (ImageView) findViewById(R.id.logout);

        buttonDoc = (Button) findViewById(R.id.buttonDoc);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonView = (Button) findViewById(R.id.buttonView);
        buttonChoose=(Button)findViewById(R.id.btn_choose);

        buttonAdd.setOnClickListener(this);
        buttonDoc.setOnClickListener(this);
        buttonView.setOnClickListener(this);
        buttonChoose.setOnClickListener(this);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(LoginActivity.session_status, false);
                editor.putString(TAG_ID, null);
                editor.putString(TAG_USERNAME, null);
                editor.commit();

                Intent intent = new Intent(Beranda.this, LoginActivity.class);
                finish();
                startActivity(intent);
            }
        });


    }

    private void showFileChooser(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage(){
        class UploadImage extends AsyncTask<Bitmap,Void,String> {
            final String nama_depan = edtNama.getText().toString().trim();
            final String nama_belakang = edtNamaBlk.getText().toString().trim();
            final String alamat = edtAlamat.getText().toString().trim();
            final String jenis_kelamin = edtJk.getText().toString().trim();
            final String no_handphone = edtNomor.getText().toString().trim();
            
            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Beranda.this, "Uploading Image", "Please wait...",true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String,String> data = new HashMap<>();
                data.put(UPLOAD_KEY, uploadImage);
                data.put("name",getFileName(filePath));
                data.put(konfigurasi.KEY_NAMA_DEPAN,nama_depan);
                data.put(konfigurasi.KEY_NAMA_BELAKANG,nama_belakang);
                data.put(konfigurasi.KEY_ALAMAT,alamat);
                data.put(konfigurasi.KEY_JENIS_KELAMIN,jenis_kelamin);
                data.put(konfigurasi.KEY_NOMOR,no_handphone);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(konfigurasi.UPLOAD_URL, data);

                return  res;
            }
        }
        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
    }

    String getFileName(Uri uri){
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    //method to get the file path from uri
    /*public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }*/

    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {
            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "\n" +
                        "Izin diberikan. Sekarang Anda dapat mengakses penyimpanan", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops Anda baru saja menolak izin", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v == buttonAdd){
            if(filePath!=null) {
                if (edtNama.getText().toString().length()==0){
                    edtNama.setError("Nama depan diperlukan!");
                }else if (edtNamaBlk.getText().toString().length()==0){
                    edtNamaBlk.setError("Nama Belangkan diperlukan!");
                }else if (edtAlamat.getText().toString().length()==0){
                    edtAlamat.setError("Alamat Lengkap diperlukan!");
                }else if (edtJk.getText().toString().length()==0){
                    edtJk.setError("Jenis kelamin diperlukan!");
                }else if (edtNomor.getText().toString().length()==0){
                    edtNomor.setError("Nomor diperlukan!");
                }else {
                    uploadImage();
                }
            } else {
                Toast.makeText(Beranda.this,"Silakan Pilih Foto",Toast.LENGTH_LONG).show();
            }
            /*uploadMultipart();*/
        }if(v == buttonDoc){
            startActivity(new Intent(this, GambarActivity.class));
        }if(v == buttonView){
            startActivity(new Intent(this, TampilSemuaKontak.class));
        }if (v == buttonChoose){
            showFileChooser();
        }
    }
}
