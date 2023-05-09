package com.example.perumahan;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.perumahan.Config.URLs;
import com.example.perumahan.Model.Perumahan;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;


public class UploadFragment extends Fragment {
    private static final String url="http://10.0.2.2/api/simpan.php";
    ImageView imageview3,imageview2,imageview1;

    private String refreshFlag="0";
    private String action_flag="add";
    private EditText tipeRumah, harga, luasBangunan, luasTanah, deskripsi;
    private ProgressDialog pDialog;
    private Perumahan perumahan;
    private Button simpan;
    ActivityResultLauncher<Intent> activityResultLauncher;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload, container, false);

        imageview1=(ImageView)view.findViewById(R.id.imageview1);
        imageview2=(ImageView)view.findViewById(R.id.imageview2);
        imageview3=(ImageView)view.findViewById(R.id.imageview3);
        tipeRumah = view.findViewById(R.id.fieldTipe);
        harga = view.findViewById(R.id.fieldHarga);
        luasBangunan = view.findViewById(R.id.fieldLuasbangunan);
        luasTanah = view.findViewById(R.id.fieldLuastanah);
        deskripsi = view.findViewById(R.id.fieldDeskripsi);
        simpan = view.findViewById(R.id.btnSimpan);

        activityResultLauncher =registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result, Intent data) {
                if (result.getResultCode() == 1 && result.getResultCode()==RESULT_OK) {

                    Uri filepath=data.getData();
//                    try {
//                        InputStream_
//                    }catch (Exception ex)
//                    {
//
//                    }
                }
            }
        });



        imageview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(getActivity())
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                                Intent intent=new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Cari Gambar"),1);

                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                            permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });

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
        getActivity().setResult(RESULT_OK, data);
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