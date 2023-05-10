package com.example.perumahan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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

    ImageView backImg;
    EditText etNama, etNamaPengguna, etEmail, etKodePos, etRt, etRw, etDetailAlamat, etJenisKelamin;
    Button btnSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        backImg = findViewById(R.id.imgBack);
        btnSimpan = findViewById(R.id.btnSimpan);


        if(SharedPrefManager.getInstance(EditProfile.this).isLoggedIn()){
            etNama = findViewById(R.id.etNama);
            etNamaPengguna = findViewById(R.id.etNamaPengguna);
            etEmail = findViewById(R.id.etEmail);
            etKodePos = findViewById(R.id.etKodePos);
            etRt = findViewById(R.id.etRt);
            etRw = findViewById(R.id.etRw);
            etDetailAlamat = findViewById(R.id.etDetailAlamat);
            etJenisKelamin = findViewById(R.id.etJenisKelamin);
            User user = SharedPrefManager.getInstance(EditProfile.this).getUser();
            etNamaPengguna.setText(user.getName());
            etEmail.setText(user.getEmail());
            etKodePos.setText(user.getKodePos());
            etRt.setText(user.getRt());
            etRw.setText(user.getRw());
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

    }
    private void editData(){
        final String username = etNamaPengguna.getText().toString().trim();
        final String email = etEmail.getText().toString().trim();
        final String gender = etJenisKelamin.getText().toString().trim();

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
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("email", email);
                    params.put("gender", gender);
                    return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(EditProfile.this);
        queue.add(stringRequest);
    }
}