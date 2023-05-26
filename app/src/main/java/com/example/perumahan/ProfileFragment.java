package com.example.perumahan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.perumahan.Config.SharedPrefManager;
import com.example.perumahan.Model.User;

public class ProfileFragment extends Fragment {

    TextView logout,userName,email;
    Button editBtn;
    RelativeLayout btnKataSandi, btnInformasi, btnLogout;

    private Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        logout = view.findViewById(R.id.txtLogout);
        editBtn = view.findViewById(R.id.btnEdit);
        btnKataSandi = view.findViewById(R.id.btnKataSandi);
        btnInformasi = view.findViewById(R.id.btnInformasiAplikasi);
        btnLogout = view.findViewById(R.id.btnLogout);

        if(SharedPrefManager.getInstance(getActivity()).isLoggedIn()){
            userName = view.findViewById(R.id.txtName);
            email = view.findViewById(R.id.txtEmail);
            User user = SharedPrefManager.getInstance(getActivity()).getUser();
            userName.setText(user.getName());
            email.setText(user.getEmail());

//            btnLogout.setOnClickListener(this::onClick);
        }
//        else{
//            Intent intent = new Intent(getActivity(),Login.class);
//            startActivity(intent);
//            getActivity().finish();
//        }

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
                startActivity(new Intent(getActivity(), Informasi.class));
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


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogLogout();
            }
        });

        return view;
    }

//    public void onClick(View view){
//        if(view.equals(logout)){
//            SharedPrefManager.getInstance(getActivity().getApplicationContext()).logout();
//            getActivity().finish();
//        }
//    }
    public void logout() {
        SharedPrefManager.getInstance(getActivity()).logout();
        Intent intent = new Intent(getActivity(), Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }

    private void showDialogLogout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Konfirmasi");
        builder.setMessage("Apakah anda yakin ingin keluar?");

        builder.setPositiveButton("YA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
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

}
