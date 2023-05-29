package com.example.perumahan;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.perumahan.Adapter.AdapterDisewakan;
import com.example.perumahan.Adapter.AdapterTerjual;
import com.example.perumahan.Model.ModelDisewakan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class DisewakanFragment extends Fragment {

    private String url = "http://10.0.2.2:80/api/tampil_terjual.php";
    RecyclerView recyclerView;
    AdapterTerjual adapterTerjual;
    LinearLayoutManager linearLayoutManager;

    List<ModelDisewakan> listDataDisewakan;
    ModelDisewakan modelDisewakan;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dijual, container, false);

        recyclerView = view.findViewById(R.id.rumahsewaRecycler);
        getData();

        return view;
    }


    //proses respon json dari server untuk menambahkan kedalam list yang ada di android
    private void getData(){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
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

                    linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(linearLayoutManager);

                    adapterTerjual = new AdapterTerjual(getActivity(), listDataDisewakan);
                    recyclerView.setAdapter(adapterTerjual);
                    adapterTerjual.notifyDataSetChanged();
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