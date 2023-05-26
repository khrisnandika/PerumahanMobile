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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.perumahan.Config.SharedPrefManager;
import com.example.perumahan.Config.URLs;
import com.example.perumahan.Config.VolleySingleton;
import com.example.perumahan.Model.User;

import org.json.JSONException;
import org.json.JSONObject;

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

        etNamaPengguna = findViewById(R.id.etNamaPengguna);
        etEmail = findViewById(R.id.etEmail);
        etDetailAlamat = findViewById(R.id.etDetailAlamat);
        etJenisKelamin = findViewById(R.id.etJenisKelamin);

        loadDataUser();

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
                showDialogUpdateUser();
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

    private void loadDataUser() {
        // Mendapatkan ID pengguna yang sedang login
        int userId = getLoggedInUserId();

        // Membuat URL API dengan ID pengguna
        String url = URLs.GETUSER_URL + "?id=" + userId;

        // Membuat objek request GET menggunakan Volley
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Mendapatkan data pengguna dari respons JSON
                            String username = response.getString("username");
                            String email = response.getString("email");
                            String alamat = response.getString("alamat");
                            String gender = response.getString("gender");

                            // Menampilkan data pengguna ke dalam EditText
                            etNamaPengguna.setText(username);
                            etEmail.setText(email);
                            etDetailAlamat.setText(alamat);
                            etJenisKelamin.setText(gender);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Gagal memuat data pengguna", Toast.LENGTH_SHORT).show();
                    }
                });

        // Menambahkan request ke antrian request Volley
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }
    public void updateDataUser() {
        int userId = getLoggedInUserId();
        String username = etNamaPengguna.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String alamat = etDetailAlamat.getText().toString().trim();
        String gender = etJenisKelamin.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.UPDATE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showDialogSukses();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Gagal mengubah data user", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(userId));
                params.put("username", username);
                params.put("email", email);
                params.put("alamat", alamat);
                params.put("gender", gender);

                return params;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void showDialogUpdateUser(){
        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile.this);
        builder.setTitle("Konfirmasi");
        builder.setMessage("Apakah anda yakin ingin mengubah data?");

        builder.setPositiveButton("YA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateDataUser();
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
    private void showDialogSukses() {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile.this);
        builder.setTitle("Sukses");
        builder.setMessage("Data berhasil diubah.");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                onBackPressed();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


}