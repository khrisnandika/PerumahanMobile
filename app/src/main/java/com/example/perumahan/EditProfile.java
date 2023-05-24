package com.example.perumahan;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.example.perumahan.Config.SharedPrefManager;
import com.example.perumahan.Config.URLs;
import com.example.perumahan.Model.User;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {

    private static final String SHARED_PREF_NAME = "volleyregisterlogin";
    private static final String KEY_ID = "keyid";
    ImageView backImg;
    EditText etNamaPengguna, etEmail, etDetailAlamat, etJenisKelamin;
    Button btnSimpan, btnHapusAkun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        backImg = findViewById(R.id.imgBack);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnHapusAkun = findViewById(R.id.btnHapusAkun);


        if(SharedPrefManager.getInstance(EditProfile.this).isLoggedIn()){
            etNamaPengguna = findViewById(R.id.etNamaPengguna);
            etEmail = findViewById(R.id.etEmail);
            etDetailAlamat = findViewById(R.id.etDetailAlamat);
            etJenisKelamin = findViewById(R.id.etJenisKelamin);
            User user = SharedPrefManager.getInstance(EditProfile.this).getUser();
            etNamaPengguna.setText(user.getName());
            etEmail.setText(user.getEmail());
            etDetailAlamat.setText(user.getDetailAlamat());
            etJenisKelamin.setText(user.getGender());
        }

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                onBackPressed();
            }
        });
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editData();
            }
        });

        btnHapusAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile.this);
                builder.setTitle("Konfirmasi");
                builder.setMessage("Apakah anda yakin ingin menghapus akun?");

                builder.setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        hapusData();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        onBackPressed();
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }
    private void hapusData() {
        int id = getLoggedInUserId();

        final String username = etNamaPengguna.getText().toString().trim();
        final String email = etEmail.getText().toString().trim();
        final String gender = etJenisKelamin.getText().toString().trim();
        final String alamat = etDetailAlamat.getText().toString().trim();

        RequestQueue requestQueue = Volley.newRequestQueue(EditProfile.this);

        if (id == -1) {
            Toast.makeText(EditProfile.this, "ID akun tidak ditemukan", Toast.LENGTH_SHORT).show();
            return;
        }
//        String url = URLs.UPDATE_URL;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.HAPUS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Volley", response);
                logout();

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                startActivity(new Intent(EditProfile.this, Login.class));
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
            }
        }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(id));
                params.put("username", username);
                params.put("email", email);
                params.put("gender", gender);
                params.put("alamat", alamat);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private int getLoggedInUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_ID, -1); // Mengembalikan nilai default -1 jika tidak ada ID tersimpan
    }
    public void logout() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Intent intent = new Intent(EditProfile.this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void editData() {
        final String username = etNamaPengguna.getText().toString().trim();
        final String email = etEmail.getText().toString().trim();
        final String gender = etJenisKelamin.getText().toString().trim();
        final String alamat = etDetailAlamat.getText().toString().trim();

        RequestQueue requestQueue = Volley.newRequestQueue(EditProfile.this);
        String url = URLs.UPDATE_URL;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Volley", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
            }
        }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("email", email);
                params.put("gender", gender);
                params.put("alamat", alamat);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}