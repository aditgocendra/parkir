package com.trex.parkirBDL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.trex.parkirbandarlampung.R;
import com.trex.parkirBDL.user.HomeActivity;
import com.trex.parkirBDL.user.mitraActivity;

public class LoginActivity extends AppCompatActivity {

    private ImageView back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LinearLayout mitra_click = findViewById(R.id.mitra_ly);
        LinearLayout pengguna_click = findViewById(R.id.pengguna_ly);

        back_btn =  findViewById(R.id.back);

        mitra_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        pengguna_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentBack = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intentBack);
            }
        });
        ImageView mitra = findViewById(R.id.mitra);
        mitra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentBack1 = new Intent(LoginActivity.this, mitraActivity.class);
                startActivity(intentBack1);
            }
        });

    }
}
