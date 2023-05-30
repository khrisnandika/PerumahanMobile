package com.example.perumahan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.perumahan.Config.SharedPrefManager;
import com.example.perumahan.Config.URLs;
import com.example.perumahan.Config.VolleySingleton;
import com.example.perumahan.Model.User;

import org.json.JSONException;
import org.json.JSONObject;


public class HomeFragment extends Fragment {
    TextView userName;
    private TextView tab1, tab2;
    private int selectedNumber = 1;

    private static final String SHARED_PREF_NAME = "volleyregisterlogin";
    private static final String KEY_ID = "keyid";

    DijualFragment dijualFragment = new DijualFragment();
    DisewakanFragment disewakanFragment = new DisewakanFragment();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        userName = view.findViewById(R.id.txt1);
//        if(SharedPrefManager.getInstance(getActivity()).isLoggedIn()){
//            userName = view.findViewById(R.id.txt1);
//            User user = SharedPrefManager.getInstance(getActivity()).getUser();
//            userName.setText(user.getName());
//        }
//        else{
//            Intent intent = new Intent(getActivity(),Login.class);
//            startActivity(intent);
//            getActivity().finish();
//        }

        loadDataUser();

        tab1 = view.findViewById(R.id.tabItem1);
        tab2 = view.findViewById(R.id.tabItem2);

        getChildFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.container, dijualFragment, null)
                .commit();

        tab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTab(1);
            }
        });

        tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTab(2);
            }
        });

        return view;
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

                            // Menampilkan data pengguna ke dalam EditText
                            userName.setText(username);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity().getApplicationContext(), "Gagal memuat data pengguna", Toast.LENGTH_SHORT).show();
                    }
                });

        // Menambahkan request ke antrian request Volley
        VolleySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    private int getLoggedInUserId() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_ID, -1); // Mengembalikan nilai default -1 jika tidak ada ID tersimpan
    }

    private void selectTab(int tabNumber){

        TextView selectedView, nonSelectedView;

        if (tabNumber == 1) {
            //pengguna telah memilih tab pertama sehingga textView pertama dipilih
            selectedView = tab1;
            nonSelectedView = tab2;

            getChildFragmentManager().beginTransaction().setReorderingAllowed(true)
                    .replace(R.id.container, dijualFragment, null)
                    .commit();

        } else {
            //textView kedua yg tidak dipilih
            selectedView = tab2;
            nonSelectedView = tab1;

            getChildFragmentManager().beginTransaction().setReorderingAllowed(true)
                    .replace(R.id.container, disewakanFragment, null)
                    .commit();
        }

        float slideTo = (tabNumber - selectedNumber) * selectedView.getWidth();

        TranslateAnimation translateAnimation = new TranslateAnimation(0, slideTo, 0, 0);
        translateAnimation.setDuration(100);

        if (selectedNumber == 1) {
            tab1.startAnimation(translateAnimation);
        } else {
            tab2.startAnimation(translateAnimation);
        }

        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                //mengubah tampilan ketika tabView diklik
                selectedView.setBackgroundResource(R.drawable.rounded_btn);
                selectedView.setTypeface(null, Typeface.BOLD);
                selectedView.setTextColor(Color.WHITE);

                //mengubah tampilan ketika tabView tidak di klik
                nonSelectedView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                nonSelectedView.setTextColor(Color.parseColor("#AAAAAA"));
                nonSelectedView.setTypeface(null, Typeface.NORMAL);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        selectedNumber = tabNumber;

    }
}