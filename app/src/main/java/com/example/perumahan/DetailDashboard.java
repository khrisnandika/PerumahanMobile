package com.example.perumahan;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailDashboard extends AppCompatActivity {
    ImageButton belakang,maju;
    ImageSwitcher action_image;
    TextView tvJudul, tvTipe, tvAlamat, tvGarasi, tvLuasBangunan, tvLuasTanah, tvKamar, tvKamarMandi, tvDeskripsi, tvHarga;
    int id;
    Button btnWhatsapp;


    int index=0;
    int fongto[]={R.drawable.home2,R.drawable.home3, R.drawable.home4, R.drawable.home5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_dashboard);

        belakang=findViewById(R.id.belakang);
        maju=findViewById(R.id.maju);
        action_image=findViewById(R.id.action_image);
        btnWhatsapp = findViewById(R.id.btnWhatsapp);

        Intent intent = getIntent();
        id = intent.getIntExtra("id_rumah", 0);

        tvJudul = findViewById(R.id.judul);
        tvTipe = findViewById(R.id.ukuran);
        tvAlamat = findViewById(R.id.alamat);
        tvGarasi = findViewById(R.id.garasi);
        tvLuasBangunan = findViewById(R.id.luasBangunan);
        tvLuasTanah = findViewById(R.id.luasTanah);
        tvKamar = findViewById(R.id.kamar);
        tvKamarMandi = findViewById(R.id.kamarMandi);
        tvDeskripsi = findViewById(R.id.deskripsi);
        tvHarga = findViewById(R.id.harga);


        tampilData();

        belakang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                -- index;
                if(index<0){
                    index=fongto.length-1;
                }
                action_image.setImageResource(fongto[index]);
            }
        });
        maju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index++;
                if(index==fongto.length){
                    index=0;
                }
                action_image.setImageResource(fongto[index]);
            }
        });

        action_image.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView=new ImageView(getApplicationContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setMaxWidth(300);
                imageView.setMaxHeight(300);

                return imageView;
            }
        });

        action_image.setImageResource(fongto[index]);

        btnWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://wa.me/6282332388893?text=Halo+Admin,+Saya+ingin+bertanya+seputar+info+rumah+yang+sedang+dijual";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

    }

    private void tampilData(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:80/api/tampil_lutfi.php?id_rumah="+id;
        JSONObject jsonObject = new JSONObject();
        final String requestBody = jsonObject.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jo = new JSONObject(response.toString());
                    JSONArray result = jo.getJSONArray("results");
                    JSONObject data =  result.getJSONObject(0);
                    System.out.println(data);


                    tvTipe.setText(data.getString("tipe_rumah"));
                    tvGarasi.setText(data.getString("total_garasi"));
                    tvLuasBangunan.setText(data.getString("luas_bangunan"));
                    tvLuasTanah.setText(data.getString("luas_tanah"));
                    tvKamar.setText(data.getString("total_kamar"));
                    tvKamarMandi.setText(data.getString("total_kamar_mandi"));
                    tvJudul.setText(data.getString("judul"));
                    tvAlamat.setText(data.getString("alamat_rumah"));
                    tvHarga.setText(data.getString("harga_rumah"));
                    tvDeskripsi.setText(data.getString("deskripsi"));



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetailDashboard.this, "Gagal", Toast.LENGTH_SHORT).show();

            }
        }
        );
        requestQueue.add(stringRequest);
    }

}