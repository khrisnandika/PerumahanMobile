package com.example.perumahan;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.perumahan.Config.URLs;
import com.example.perumahan.Model.Perumahan;
import com.github.drjacky.imagepicker.ImagePicker;
import com.github.drjacky.imagepicker.constant.ImageProvider;


import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;


public class UploadFragment extends Fragment {

    private String refreshFlag="0";
    private String action_flag="add";
    private static final int CAMERA_REQUEST = 1888;
    private EditText judul, tipeRumah, harga, alamat, jumlahKamar, jumlahKamarMandi, jumlahGarasi, luasBangunan, luasTanah, deskripsi;
    private ImageView imageView1, imageView2, imageView3;
    private Perumahan perumahan;
    private Button simpan;
    private ActivityResultLauncher<Intent> activityResultLauncher1, activityResultLauncher2, activityResultLauncher3;
    private Bitmap bitmap1, bitmap2, bitmap3;
    private ByteArrayOutputStream byteArrayOutputStream;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload, container, false);

        judul = view.findViewById(R.id.fieldJudul);
        tipeRumah = view.findViewById(R.id.fieldTipe);
        harga = view.findViewById(R.id.fieldHarga);
        alamat = view.findViewById(R.id.fieldAlamat);
        jumlahKamar = view.findViewById(R.id.fieldKamar);
        jumlahKamarMandi = view.findViewById(R.id.fieldKamarMandi);
        jumlahGarasi = view.findViewById(R.id.fieldGarasi);
        luasBangunan = view.findViewById(R.id.fieldLuasbangunan);
        luasTanah = view.findViewById(R.id.fieldLuastanah);
        deskripsi = view.findViewById(R.id.fieldDeskripsi);
        imageView1 = view.findViewById(R.id.imageview1);
        imageView2 = view.findViewById(R.id.imageview2);
        imageView3 = view.findViewById(R.id.imageview3);
        simpan = view.findViewById(R.id.btnSimpan);

        activityResultLauncher1 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    Uri uri = data.getData();
                    try {
                        bitmap1 = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                        imageView1.setImageBitmap(bitmap1);
                    } catch (IOException e) {
                        Log.e("gambar", e.toString());
                    }
                }
//                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
//                    Bundle bundle = result.getData().getExtras();
//                    Bitmap bitmap = (Bitmap) bundle.get("data");
//                    imageView1.setImageBitmap(bitmap);
//                }
            }
        });

        activityResultLauncher2 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    Uri uri = data.getData();
                    try {
                        bitmap2 = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                        imageView2.setImageBitmap(bitmap2);
                    } catch (IOException e) {
                        Log.e("gambar", e.toString());
                    }
                }
            }
        });

        activityResultLauncher3 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    Uri uri = data.getData();
                    try {
                        bitmap3 = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                        imageView3.setImageBitmap(bitmap3);
                    } catch (IOException e) {
                        Log.e("gambar", e.toString());
                    }
                }
            }
        });

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityResultLauncher1.launch(
                        ImagePicker.with(getActivity())
                                .crop()
                                .maxResultSize(512, 512, true)
                                .provider(ImageProvider.GALLERY)
                                .createIntent()
                );
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
//                    activityResultLauncher1.launch(intent);
//                } else {
//                    Toast.makeText(getActivity(), "gagal", Toast.LENGTH_SHORT).show();
//                }
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityResultLauncher2.launch(
                        ImagePicker.with(getActivity())
                                .crop()
                                .maxResultSize(512, 512, true)
                                .provider(ImageProvider.GALLERY)
                                .createIntent()
                );
//                ImagePicker.Companion.with(getActivity())
//                        .crop()
//                        .maxResultSize(512,512,true)
//                        .provider(ImageProvider.BOTH) //Or bothCameraGallery()
//                        .createIntentFromDialog((Function1)(new Function1(){
//                            public Object invoke(Object var1){
//                                this.invoke((Intent)var1);
//                                return Unit.INSTANCE;
//                            }
//
//                            public final void invoke(@NotNull Intent it){
//                                Intrinsics.checkNotNullParameter(it,"it");
//                                activityResultLauncher2.launch(it);
//                            }
//                        }));
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityResultLauncher3.launch(
                        ImagePicker.with(getActivity())
                                .crop()
                                .maxResultSize(512, 512, true)
                                .provider(ImageProvider.GALLERY)
                                .createIntent()
                );
            }
        });

//        simpan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                byteArrayOutputStream = new ByteArrayOutputStream();
//                if (bitmap1 != null) {
//                    bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//                    byte[] bytes = byteArrayOutputStream.toByteArray();
//                    final String baseImage1 = Base64.encodeToString(bytes, Base64.DEFAULT);
//                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//                    String url = "http://10.0.2.2:80/api/uploadfoto.php";
//
//                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            if (response.equals("success")) {
//                                Toast.makeText(getActivity().getApplicationContext(), "gambar terupload", Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(getActivity().getApplicationContext(), "gambar tidak terupload", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
////                            Log.e("Volley", error.toString());
//                            Toast.makeText(getActivity().getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                    ) {
//                        @Nullable
//                        @Override
//                        protected Map<String, String> getParams() throws AuthFailureError {
//                            Map<String, String> params = new HashMap<>();
//
//                            params.put("upload", baseImage1);
//
//                            return params;
//                        }
//                    };
//                    requestQueue.add(stringRequest);
//
//                } else {
//                    Toast.makeText(getActivity().getApplicationContext(), "isi gambar terlebih dahulu", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

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
        final String etAlamat = alamat.getText().toString().trim();
        final String etJumlahKamar = jumlahKamar.getText().toString().trim();
        final String etJumlahKamarMandi = jumlahKamarMandi.getText().toString().trim();
        final String etJumlahGarasi = jumlahGarasi.getText().toString().trim();
        final String etLuasBangunan = luasBangunan.getText().toString().trim();
        final String etLuasTanah = luasTanah.getText().toString().trim();
        final String etDeskripsi = deskripsi.getText().toString().trim();

        String url = URLs.SIMPAN_URL;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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
                params.put("alamat_rumah", etAlamat);
                params.put("total_kamar", etJumlahKamar);
                params.put("total_kamar_mandi", etJumlahKamarMandi);
                params.put("total_garasi", etJumlahGarasi);
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

    private void uploadRumah(){
        byteArrayOutputStream = new ByteArrayOutputStream();
        final String etTipeRumah = tipeRumah.getText().toString().trim();
        final String etHarga = harga.getText().toString().trim();
        final String etAlamat = alamat.getText().toString().trim();
        final String etJumlahKamar = jumlahKamar.getText().toString().trim();
        final String etJumlahKamarMandi = jumlahKamarMandi.getText().toString().trim();
        final String etJumlahGarasi = jumlahGarasi.getText().toString().trim();
        final String etLuasBangunan = luasBangunan.getText().toString().trim();
        final String etLuasTanah = luasTanah.getText().toString().trim();
        final String etDeskripsi = deskripsi.getText().toString().trim();

        if (bitmap1 != null || bitmap2 != null || bitmap3 != null) {
            bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            final String baseImage1 = Base64.encodeToString(bytes, Base64.DEFAULT);
            final String baseImage2 = Base64.encodeToString(bytes, Base64.DEFAULT);
            final String baseImage3 = Base64.encodeToString(bytes, Base64.DEFAULT);

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            String url = URLs.SIMPAN_URL;

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("success")) {
                        Toast.makeText(getActivity().getApplicationContext(), "gambar terupload", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("error", response);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Volley", error.toString());
                    Toast.makeText(getActivity().getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            ) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();

                    params.put("foto_rumah1", baseImage1);
                    params.put("foto_rumah2", baseImage2);
                    params.put("foto_rumah3", baseImage3);
                    params.put("tipe_rumah", etTipeRumah);
                    params.put("harga", etHarga);
                    params.put("alamat_rumah", etAlamat);
                    params.put("total_kamar", etJumlahKamar);
                    params.put("total_kamar_mandi", etJumlahKamarMandi);
                    params.put("total_garasi", etJumlahGarasi);
                    params.put("luas_bangunan", etLuasBangunan);
                    params.put("luas_tanah", etLuasTanah);
                    params.put("deskripsi", etDeskripsi);

                    return params;
                }
            };
            requestQueue.add(stringRequest);

        } else {
            Toast.makeText(getActivity().getApplicationContext(), "isi gambar terlebih dahulu", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadImage(){
        String url = "http://10.0.2.2:80/api/uploadfoto.php";
        final String etJudul = judul.getText().toString().trim();
        final String etTipeRumah = tipeRumah.getText().toString().trim();
        final String etHarga = harga.getText().toString().trim();
        final String etAlamat = alamat.getText().toString().trim();
        final String etJumlahKamar = jumlahKamar.getText().toString().trim();
        final String etJumlahKamarMandi = jumlahKamarMandi.getText().toString().trim();
        final String etJumlahGarasi = jumlahGarasi.getText().toString().trim();
        final String etLuasBangunan = luasBangunan.getText().toString().trim();
        final String etLuasTanah = luasTanah.getText().toString().trim();
        final String etDeskripsi = deskripsi.getText().toString().trim();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getActivity().getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                System.out.println(error);
            }
        }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("foto_rumah1", imageToString(bitmap1));
                params.put("foto_rumah2", imageToString(bitmap2));
                params.put("foto_rumah3", imageToString(bitmap3));
                params.put("judul", etJudul);
                params.put("tipe_rumah", etTipeRumah);
                params.put("harga_rumah", etHarga);
                params.put("alamat_rumah", etAlamat);
                params.put("total_kamar", etJumlahKamar);
                params.put("total_kamar_mandi", etJumlahKamarMandi);
                params.put("total_garasi", etJumlahGarasi);
                params.put("luas_bangunan", etLuasBangunan);
                params.put("luas_tanah", etLuasTanah);
                params.put("deskripsi", etDeskripsi);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }

}