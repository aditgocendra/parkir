package com.trex.parkirBDL.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.trex.parkirbandarlampung.R;
import com.trex.parkirBDL.kendaraan.Mobil;
import com.trex.parkirBDL.kendaraan.Motor;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private ImageView klik_motor;
    private ImageView klik_mobil;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view =  inflater.inflate(R.layout.fragment_home, container, false);
        klik_motor = view.findViewById(R.id.motor);
        klik_motor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent motor = new Intent(getContext(), Motor.class);
                startActivity(motor);
            }
        });

        klik_mobil = view.findViewById(R.id.mobil);
        klik_mobil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent motor = new Intent(getContext(), Mobil.class);
                startActivity(motor);
            }
        });


        return  view;
    }

}
