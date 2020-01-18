package com.trex.parkirBDL;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.trex.parkirbandarlampung.R;

public class AkunActivity extends AppCompatActivity {

    private ImageView back_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akun);

        back_btn = findViewById(R.id.back);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AkunActivity.this, PengaturanActivity.class);
                startActivity(intent);
            }
        });
    }
}
