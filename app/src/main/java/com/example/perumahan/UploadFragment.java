package com.example.perumahan;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.perumahan.Config.URLs;
import com.example.perumahan.Model.Perumahan;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class UploadFragment extends Fragment {

    private String refreshFlag="0";
    private String action_flag="add";
    private EditText tipeRumah, harga, luasBangunan, luasTanah, deskripsi;
    private ProgressDialog pDialog;
    private Perumahan perumahan;
    private Button simpan;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload, container, false);

        tipeRumah = view.findViewById(R.id.fieldTipe);
        harga = view.findViewById(R.id.fieldHarga);
        luasBangunan = view.findViewById(R.id.fieldLuasbangunan);
        luasTanah = view.findViewById(R.id.fieldLuastanah);
        deskripsi = view.findViewById(R.id.fieldDeskripsi);
        simpan = view.findViewById(R.id.btnSimpan);

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataRumah();
            }
        });

//        initUI();

        return view;
    }
    //    set atau inisiasi variabel untuk inisiasi form
//    private void initUI() {
//        pDialog = new ProgressDialog(getActivity());
//
//        tipeRumah = getActivity().findViewById(R.id.fieldTipe);
//        harga = getActivity().findViewById(R.id.fieldHarga);
//        luasBangunan = getActivity().findViewById(R.id.fieldLuasbangunan);
//        luasTanah = getActivity().findViewById(R.id.fieldLuastanah);
//        deskripsi = getActivity().findViewById(R.id.fieldDeskripsi);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.btnSimpan) {
            saveDataRumah();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void finish(){
        System.gc();
        Intent data = new Intent();
        data.putExtra("refreshflag", refreshFlag);
        data.putExtra("perumahan", perumahan);
        getActivity().setResult(Activity.RESULT_OK, data);
        super.getActivity().finish();
        startActivity(new Intent(getActivity(), MainActivity.class));
    }

    private void saveDataRumah(){
        refreshFlag="1";
        final String etTipeRumah = tipeRumah.getText().toString().trim();
        final String etHarga = harga.getText().toString().trim();
        final String etLuasBangunan = luasBangunan.getText().toString().trim();
        final String etLuasTanah = luasTanah.getText().toString().trim();
        final String etDeskripsi = deskripsi.getText().toString().trim();

        String url = URLs.SIMPAN_URL;
//        pDialog.setMessage("Save Data Perumahan...");
//        showDialog();

        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                hideDialog();
                Log.d("UploadFragment", "response :" + response);
                processResponse("Save Data", response);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//               hideDialog();
               error.printStackTrace();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("tipe_rumah", etTipeRumah);
                params.put("harga", etHarga);
                params.put("luas_bangunan", etLuasBangunan);
                params.put("luas_tanah", etLuasTanah);
                params.put("deskripsi", etDeskripsi);
                if (action_flag.equals("add")) {
                    params.put("id", "0");
                } else {
                    params.put("id", perumahan.getId());
                }
                return params;
            }
        };
        Volley.newRequestQueue(getActivity()).add(postRequest);

    }

    private void processResponse(String paction, String response){

        try {
            JSONObject jsonObj = new JSONObject(response);
            String errormsg = jsonObj.getString("errormsg");
            Toast.makeText(getActivity().getBaseContext(),paction+" "+errormsg,Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            Log.d("InputKamarActivity", "errorJSON");
        }

    }

//    private void showDialog() {
//        if (!pDialog.isShowing())
//            pDialog.show();
//    }
//
//    private void hideDialog() {
//        if (pDialog.isShowing())
//            pDialog.dismiss();
//    }
}