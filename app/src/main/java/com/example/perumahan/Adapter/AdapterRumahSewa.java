package com.example.perumahan.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.perumahan.Model.ModelRumahSewa;
import com.example.perumahan.R;

import java.util.ArrayList;

public class AdapterRumahSewa extends RecyclerView.Adapter<AdapterRumahSewa.ViewHolder> {

    private ArrayList<ModelRumahSewa> modelRumahSewa;

    public AdapterRumahSewa(ArrayList<ModelRumahSewa> modelRumahSewa) {
        this.modelRumahSewa = modelRumahSewa;
    }


    @NonNull
    @Override
    public AdapterRumahSewa.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_disewakan, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRumahSewa.ViewHolder holder, int position) {
        holder.namaRumah.setText(modelRumahSewa.get(position).getNamaRumah());
        holder.statusRumah.setText(modelRumahSewa.get(position).getStatusRumah());
        holder.alamatRumah.setText(modelRumahSewa.get(position).getAlamatRumah());
        holder.imgRumah.setImageResource(modelRumahSewa.get(position).getGambarRumah());
    }

    @Override
    public int getItemCount() {
        return modelRumahSewa.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView namaRumah, statusRumah, alamatRumah;
        ImageView imgRumah;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            namaRumah = itemView.findViewById(R.id.txtRumah);
            statusRumah = itemView.findViewById(R.id.txtStatus);
            alamatRumah = itemView.findViewById(R.id.txtHarga);
            imgRumah = itemView.findViewById(R.id.imgHome);
        }
    }
}
