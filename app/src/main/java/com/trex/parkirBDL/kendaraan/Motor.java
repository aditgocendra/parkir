package com.trex.parkirBDL.kendaraan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.trex.parkirBDL.user.HomeActivity;
import com.trex.parkirbandarlampung.R;
import com.trex.parkirBDL.view.kedaton;

public class Motor extends AppCompatActivity {
    private ImageView back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motor);

        back_btn = findViewById(R.id.back);

        Button kec = findViewById(R.id.pilih_kecamatan);
        kec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kecc = new Intent (Motor.this, kedaton.class);
                startActivity(kecc);
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentBack = new Intent(Motor.this, HomeActivity.class);
                intentBack.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentBack);
            }
        });


    }
}
