package com.example.perumahan;

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
import com.example.perumahan.Model.ModelDisewakan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class DijualFragment extends Fragment {

    private String url = "http://10.0.2.2:80/api/tampil_data.php";
    RecyclerView recyclerView;
    AdapterDisewakan adapterDisewakan;
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
                        modelDisewakan.setGambarRumah(data.getString("foto_rumah"));
                        modelDisewakan.setTipeRumah(data.getString("tipe_rumah"));
                        modelDisewakan.setAlamatRumah(data.getString("alamat_rumah"));
                        modelDisewakan.setStatusRumah(data.getString("status"));
//                        dataModel.setGambar(data.getString("img"));
                        listDataDisewakan.add(modelDisewakan);
                    }

                    linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(linearLayoutManager);

                    adapterDisewakan = new AdapterDisewakan(getActivity(), listDataDisewakan);
                    recyclerView.setAdapter(adapterDisewakan);
                    adapterDisewakan.notifyDataSetChanged();

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
//        private void getData(){
//        modelRumahSewa = new ArrayList<>();
//        modelRumahSewa.add(new ModelRumahSewa("Rumah Berseri","Status : Tersedia","Jalan Jendral Sudirman", R.drawable.rumah1));
//        modelRumahSewa.add(new ModelRumahSewa("Rumah Enjoy","Status : Tersedia","Jalan Jendral Sudirman", R.drawable.rumah1));
//        modelRumahSewa.add(new ModelRumahSewa("Rumah Slepek","Status : Tersedia","Jalan Jendral Sudirman", R.drawable.rumah1));
//        modelRumahSewa.add(new ModelRumahSewa("Rumah Pcc","Status : Tersedia","Jalan Jendral Sudirman", R.drawable.rumah1));
//        modelRumahSewa.add(new ModelRumahSewa("Rumah Sibaw","Status : Tersedia","Jalan Jendral Sudirman", R.drawable.rumah1));
//        modelRumahSewa.add(new ModelRumahSewa("Rumah Cuak","Status : Tersedia","Jalan Jendral Sudirman", R.drawable.rumah1));
//        modelRumahSewa.add(new ModelRumahSewa("Rumah YOYOYO","Status : Tersedia","Jalan Jendral Sudirman", R.drawable.rumah1));
//        modelRumahSewa.add(new ModelRumahSewa("Rumah Tess","Status : Tersedia","Jalan Jendral Sudirman", R.drawable.rumah1));
//        modelRumahSewa.add(new ModelRumahSewa("Rumah Berseri","Status : Tersedia","Jalan Jendral Sudirman", R.drawable.rumah1));
//        modelRumahSewa.add(new ModelRumahSewa("Rumah Berseri","Status : Tersedia","Jalan Jendral Sudirman", R.drawable.rumah1));
//    }
}