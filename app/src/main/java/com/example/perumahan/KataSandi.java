package com.example.perumahan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DownloadManager;
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
import com.example.perumahan.Config.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class KataSandi extends AppCompatActivity {

    ImageView btnBack;
    Button btnSimpan;
    EditText etPasswordLama, etPasswordBaru, etKonfirmasiPasswordBaru;

    private static final String SHARED_PREF_NAME = "volleyregisterlogin";
    private static final String KEY_ID = "keyid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kata_sandi);

        btnBack = findViewById(R.id.btnBack);
        btnSimpan = findViewById(R.id.btnSimpan);

        etPasswordLama = findViewById(R.id.etPasswordLama);
        etPasswordBaru = findViewById(R.id.etPasswordBaru);
        etKonfirmasiPasswordBaru = findViewById(R.id.etKonfirmasiPasswordBaru);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                onBackPressed();
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(KataSandi.this);
                builder.setTitle("Konfirmasi");
                builder.setMessage("Apakah anda yakin ingin mengubah password?");

                builder.setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String passwordBaru = etPasswordBaru.getText().toString().trim();
                        final String konfirmasiPasswordBaru = etKonfirmasiPasswordBaru.getText().toString().trim();

                        if (passwordBaru.equals(konfirmasiPasswordBaru)) {
                            ubahPassword();
                        } else {
                            showDialogKonfirmasi();
                        }
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
    private void ubahPassword(){

        final String passwordLama = etPasswordLama.getText().toString().trim();
        final String passwordBaru = etPasswordBaru.getText().toString().trim();

        int id = getLoggedInUserId();

        RequestQueue requestQueue = Volley.newRequestQueue(KataSandi.this);

        if (id == -1) {
            Toast.makeText(KataSandi.this, "ID akun tidak ditemukan", Toast.LENGTH_SHORT).show();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.UBAHSANDI_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Volley", response);
                // Parsing tanggapan sebagai objek JSON
                if (response.equalsIgnoreCase("Password berhasil diubah")) {
                    // Kata sandi berhasil diubah
                    showDialogSukses();
//                    Toast.makeText(getApplicationContext(), "Kata Sandi Berhasil Diubah", Toast.LENGTH_SHORT).show();
                } else {
                    // Gagal mengubah kata sandi
                        AlertDialog.Builder builder = new AlertDialog.Builder(KataSandi.this);
                        builder.setTitle("Gagal");
                        builder.setMessage(response);

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                // Tanggapan error dari API
                Toast.makeText(getApplicationContext(), "Gagal mengubah password", Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(id));
                params.put("password_lama", passwordLama);
                params.put("password_baru", passwordBaru);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private int getLoggedInUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_ID, -1); // Mengembalikan nilai default -1 jika tidak ada ID tersimpan
    }

    private void showDialogSukses() {
        AlertDialog.Builder builder = new AlertDialog.Builder(KataSandi.this);
        builder.setTitle("Sukses");
        builder.setMessage("Password berhasil diubah.");

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
    private void showDialogKonfirmasi() {
        AlertDialog.Builder builder = new AlertDialog.Builder(KataSandi.this);
        builder.setTitle("Gagal");
        builder.setMessage("Konfirmasi password salah");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


}