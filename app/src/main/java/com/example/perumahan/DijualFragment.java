package com.example.perumahan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class DijualFragment extends Fragment {

    private RecyclerView recyclerView;
    private AdapterRumahSewa adapterRumahSewa;
    private ArrayList<ModelRumahSewa> modelRumahSewa;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dijual, container, false);

        getData();

        recyclerView = view.findViewById(R.id.rumahsewaRecycler);
        adapterRumahSewa = new AdapterRumahSewa(modelRumahSewa);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterRumahSewa);

        return view;
    }
    private void getData(){
        modelRumahSewa = new ArrayList<>();
        modelRumahSewa.add(new ModelRumahSewa("Rumah Berseri","Status : Tersedia","Jalan Jendral Sudirman", R.drawable.rumah1));
        modelRumahSewa.add(new ModelRumahSewa("Rumah Enjoy","Status : Tersedia","Jalan Jendral Sudirman", R.drawable.rumah1));
        modelRumahSewa.add(new ModelRumahSewa("Rumah Slepek","Status : Tersedia","Jalan Jendral Sudirman", R.drawable.rumah1));
        modelRumahSewa.add(new ModelRumahSewa("Rumah Pcc","Status : Tersedia","Jalan Jendral Sudirman", R.drawable.rumah1));
        modelRumahSewa.add(new ModelRumahSewa("Rumah Sibaw","Status : Tersedia","Jalan Jendral Sudirman", R.drawable.rumah1));
        modelRumahSewa.add(new ModelRumahSewa("Rumah Cuak","Status : Tersedia","Jalan Jendral Sudirman", R.drawable.rumah1));
        modelRumahSewa.add(new ModelRumahSewa("Rumah YOYOYO","Status : Tersedia","Jalan Jendral Sudirman", R.drawable.rumah1));
        modelRumahSewa.add(new ModelRumahSewa("Rumah Tess","Status : Tersedia","Jalan Jendral Sudirman", R.drawable.rumah1));
        modelRumahSewa.add(new ModelRumahSewa("Rumah Berseri","Status : Tersedia","Jalan Jendral Sudirman", R.drawable.rumah1));
        modelRumahSewa.add(new ModelRumahSewa("Rumah Berseri","Status : Tersedia","Jalan Jendral Sudirman", R.drawable.rumah1));
    }
}