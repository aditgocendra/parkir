package com.trex.parkirBDL.user;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.trex.parkirbandarlampung.R;
import com.trex.parkirBDL.fragment.BlankFragment;

public class mitraActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToogle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mitra);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();



        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        mDrawerLayout = findViewById(R.id.drawer_navigasi2);
        mDrawerToogle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mDrawerToogle);
        mDrawerToogle.syncState();
        BlankFragment fragment = new BlankFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment, "Home");
        fragmentTransaction.commit();

    }
}
