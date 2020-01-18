package com.trex.parkirBDL.model;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trex.parkirbandarlampung.R;

public class FirebaseViewHolder extends RecyclerView.ViewHolder{

    public TextView namab,catb,dateb,name;

    public ImageView image;

    public ImageView image1;

    public FirebaseViewHolder(@NonNull View itemView) {
        super(itemView);
        namab = itemView.findViewById(R.id.nama_area);

        catb = itemView.findViewById(R.id.alamat_area);

        dateb = itemView.findViewById(R.id.datebarang);

        image = (ImageView) itemView.findViewById(R.id.imageViewbarang);

    }
}
