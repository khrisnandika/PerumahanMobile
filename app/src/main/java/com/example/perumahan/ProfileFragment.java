package com.example.perumahan;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
<<<<<<< HEAD
import android.widget.TextView;
=======
import android.widget.RelativeLayout;
>>>>>>> ffe4a73ee3ba04045daba129160a9610aa0e97f8

public class ProfileFragment extends Fragment {

    TextView logout,userName,email;
    Button editBtn;
    RelativeLayout btnKataSandi, btnInformasi, btnLogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        logout = view.findViewById(R.id.txtLogout);
        if(SharedPrefManager.getInstance(getActivity()).isLoggedIn()){
            userName = view.findViewById(R.id.txtName);
            email = view.findViewById(R.id.txtEmail);
            User user = SharedPrefManager.getInstance(getActivity()).getUser();
            userName.setText(user.getName());
            email.setText(user.getEmail());

            logout.setOnClickListener(this::onClick);
        }
        else{
            Intent intent = new Intent(getActivity(),Login.class);
            startActivity(intent);
            getActivity().finish();
        }

        editBtn = view.findViewById(R.id.btnEdit);
        btnKataSandi = view.findViewById(R.id.btnKataSandi);
        btnInformasi = view.findViewById(R.id.btnInformasiAplikasi);
        btnLogout = view.findViewById(R.id.btnLogout);

        btnKataSandi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), KataSandi.class);
                startActivity(intent);
            }
        });

        btnInformasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Informasi.class);
                startActivity(intent);
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfile.class);
                startActivity(intent);
//                if(view.equals(logout)){
//                    SharedPrefManager.getInstance(getActivity().getApplicationContext()).logout();
//                }
            }
        });
<<<<<<< HEAD
=======

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
            }
        });

>>>>>>> ffe4a73ee3ba04045daba129160a9610aa0e97f8
        return view;
    }
    public void onClick(View view){
        if(view.equals(logout)){
            SharedPrefManager.getInstance(getActivity().getApplicationContext()).logout();
        }
    }
}
