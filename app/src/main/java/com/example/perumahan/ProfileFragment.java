package com.example.perumahan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
    RelativeLayout btnKataSandi, btnInformasi, btnLogout, btnHapusAkun;

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
        btnHapusAkun = view.findViewById(R.id.btnHapusAkun);

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
                DatabaseHelper dbHelper = new DatabaseHelper(context);
                boolean deleted = dbHelper.deleteAccount(1); // Menghapus akun dengan ID 1

                if (deleted) {
                    Toast.makeText(context, "Akun berhasil dihapus", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Gagal menghapus akun", Toast.LENGTH_SHORT).show();
                }
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

        btnHapusAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] pilihAksi= {"YA", "TIDAK"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setItems(pilihAksi, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                //jika dipilih ya

                            case  1:
                                //jika dipilih tidak
                        }
                    }
                });
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }
    public void onClick(View view){
        if(view.equals(logout)){
            SharedPrefManager.getInstance(getActivity().getApplicationContext()).logout();
        }
    }
    void signOut() {
        Intent intent = new Intent(getActivity(), Login.class);
        intent.putExtra("finish", true);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
        startActivity(intent);
        getActivity().finish();
    }

    public void hapusData(){

    }
}
