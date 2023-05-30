package com.example.perumahan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.perumahan.Adapter.AdapterDisewakan;
import com.example.perumahan.Model.ModelDisewakan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RumahDiUploadUser extends AppCompatActivity {


    private String url = "http://10.0.2.2:80/api/tampil_data.php";

    ImageView btnBack;
    RecyclerView recyclerView;
    AdapterDisewakan adapterDisewakan;
    LinearLayoutManager linearLayoutManager;

    List<ModelDisewakan> listDataDisewakan;
    ModelDisewakan modelDisewakan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rumah_di_upload_user);

        recyclerView = findViewById(R.id.rumahsewaRecycler);
        getData();

        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                onBackPressed();
            }
        });


    }
    private void getData(){
        RequestQueue requestQueue = Volley.newRequestQueue(RumahDiUploadUser.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                ArrayList<ModelDisewakan> listDataDisewakan = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    for (int i = 0; i <jsonArray.length(); i++) {
                        modelDisewakan = new ModelDisewakan();
                        JSONObject data = jsonArray.getJSONObject(i);
                        modelDisewakan.setId(data.getInt("id_rumah"));
                        modelDisewakan.setGambarRumah(data.getString("foto_rumah1"));
                        modelDisewakan.setJudulRumah(data.getString("judul"));
                        modelDisewakan.setAlamatRumah(data.getString("alamat_rumah"));
//                        modelDisewakan.setStatusRumah(data.getString("status"));
                        modelDisewakan.setHargaRumah(data.getString("harga_rumah"));
                        modelDisewakan.setUkuranRumah(data.getString("tipe_rumah"));
                        modelDisewakan.setTotalKamar(data.getString("total_kamar"));
                        modelDisewakan.setTotalKamarMandi(data.getString("total_kamar_mandi"));
                        modelDisewakan.setTotalGarasi(data.getString("total_garasi"));

                        listDataDisewakan.add(modelDisewakan);
                    }

                    linearLayoutManager = new LinearLayoutManager(RumahDiUploadUser.this, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(linearLayoutManager);

                    adapterDisewakan = new AdapterDisewakan(RumahDiUploadUser.this, listDataDisewakan);
                    recyclerView.setAdapter(adapterDisewakan);
                    adapterDisewakan.notifyDataSetChanged();
//                    adapterDisewakan.setOnItemClickListener(DijualFragment.this);

                } catch (JSONException e) {
                    Log.e("Volley", e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
        requestQueue.add(stringRequest);
    }
}