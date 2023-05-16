package com.example.perumahan.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.perumahan.DetailDashboard;
import com.example.perumahan.Model.ModelDisewakan;
import com.example.perumahan.R;

import java.util.List;

public class AdapterDisewakan extends RecyclerView.Adapter<AdapterDisewakan.ViewHolder> {

    List<ModelDisewakan> listDataDisewakan;
    LayoutInflater layoutInflater;
    Context context;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public AdapterDisewakan(Context context, List<ModelDisewakan> listDataDisewakan) {
        this.listDataDisewakan = listDataDisewakan;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.list_disewakan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(listDataDisewakan.get(position).getGambarRumah()).into(holder.image);
        holder.txtRumah.setText(listDataDisewakan.get(position).getTipeRumah());
        holder.txtStatus.setText(listDataDisewakan.get(position).getStatusRumah());
        holder.txtAlamat.setText(listDataDisewakan.get(position).getAlamatRumah());
        holder.modelDisewakan = listDataDisewakan.get(position);



    }

    @Override
    public int getItemCount() {
        return listDataDisewakan.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ModelDisewakan modelDisewakan;

        ImageView image;
        TextView txtRumah, txtStatus, txtAlamat;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            image = itemView.findViewById(R.id.imgHome);
            txtRumah = itemView.findViewById(R.id.txtRumah);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtAlamat = itemView.findViewById(R.id.txtAlamat);
            itemView.setOnClickListener(this);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mListener != null) {
//                        int position = getBindingAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION) {
//                            mListener.onItemClick(position);
//                        }
//                    }
//                }
//            });
        }

        @Override
        public void onClick(View v) {
            int position = getBindingAdapterPosition();
            Toast.makeText(context, "position"+position, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, DetailDashboard.class);
            intent.putExtra("id", modelDisewakan.getId());
            context.startActivity(intent);
        }

    }
}
