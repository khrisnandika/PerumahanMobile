package com.example.perumahan;

import static com.example.perumahan.DijualFragment.EXTRA_ALAMAT;
import static com.example.perumahan.DijualFragment.EXTRA_FOTO;
import static com.example.perumahan.DijualFragment.EXTRA_STATUS;
import static com.example.perumahan.DijualFragment.EXTRA_TIPE;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.perumahan.Adapter.AdapterDisewakan;
import com.example.perumahan.Config.SharedPrefManager;
import com.example.perumahan.Model.ModelDisewakan;
import com.example.perumahan.Model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailDashboard extends AppCompatActivity {
    ImageButton belakang,maju;
    ImageSwitcher action_image;
    TextView tvTipe, tvAlamat, tvStatus;
    int id;


    int index=0;
    int fongto[]={R.drawable.fotodash,R.drawable.bg_image};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_dashboard);

        belakang=findViewById(R.id.belakang);
        maju=findViewById(R.id.maju);
        action_image=findViewById(R.id.action_image);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        tvTipe = findViewById(R.id.ukuran);
        tvAlamat = findViewById(R.id.garasi);
        tvStatus = findViewById(R.id.luasBangunan);


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

    }

    private void tampilData(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:80/api/tampil_lutfi.php?id="+id;
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


                    tvTipe.setText(data.getString("id"));
                    tvStatus.setText(data.getString("status"));


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