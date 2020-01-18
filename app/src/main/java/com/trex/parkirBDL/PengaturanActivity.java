package com.trex.parkirBDL;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.trex.parkirbandarlampung.R;
import com.trex.parkirBDL.user.HomeActivity;

public class PengaturanActivity extends AppCompatActivity {
private TextView pengkun;
    private ImageView back_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan);

        pengkun=findViewById(R.id.akun);

        pengkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            Intent intent  = new Intent(PengaturanActivity.this, AkunActivity.class);
            startActivity(intent);

            }
        });

        back_btn = findViewById(R.id.back);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PengaturanActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
