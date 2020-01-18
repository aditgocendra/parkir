package com.trex.parkirBDL.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.trex.parkirBDL.AboutActivity;
import com.trex.parkirBDL.MainActivity;
import com.trex.parkirBDL.PengaturanActivity;
import com.trex.parkirbandarlampung.R;
import com.trex.parkirBDL.fragment.BookingFragment;
import com.trex.parkirBDL.fragment.FavoriteFragment;
import com.trex.parkirBDL.fragment.HomeFragment;
import com.trex.parkirBDL.fragment.NotificationFragment;
import com.trex.parkirBDL.fragment.ProfileFragment;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;

    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToogle;
    GoogleSignInClient mGoogleSignInClient;

    CircleImageView navImageView;
    TextView navNamaUser;
    TextView navEmailUser;

    Fragment homefragment;
    Fragment favoritefragment;
    Fragment bookingfragment;
    Fragment profilefragment;
    Fragment notifikasifragment;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homefragment = new HomeFragment();
        favoritefragment = new FavoriteFragment();
        bookingfragment = new BookingFragment();
        profilefragment = new ProfileFragment();
        notifikasifragment = new NotificationFragment();


        //Google Sign In------------------------------------------------
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        //--------------------------------------------------------------

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);



        mDrawerLayout = findViewById(R.id.drawer_navigasi);
        mDrawerToogle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mDrawerToogle);
        mDrawerToogle.syncState();


        // inisialisasi Home -----------------------------------------------------------------------
        HomeFragment fragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment, "Home");
        fragmentTransaction.commit();

        // Read Navigasi ---------------------------------------------------------------------------
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
         navEmailUser = (TextView) headerView.findViewById(R.id.email_user);
         navNamaUser = (TextView) headerView.findViewById(R.id.nama_user);
         navImageView = (CircleImageView) headerView.findViewById(R.id.profile_image);

         loadUserInformation();


        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){

                    case R.id.action_home:
                        setFragment(homefragment);
                        return true;

                    case R.id.action_favorite:
                        setFragment(favoritefragment);
                        return true;

                    case R.id.action_booking:
                        setFragment(bookingfragment);
                        return true;

                    case R.id.action_notif:
                        setFragment(notifikasifragment);
                        return true;

                    case R.id.action_profile:
                        setFragment(profilefragment);
                        return true;

                        default:
                            return true;
                }
            }
        });

        setFragment(homefragment);
        }




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
//
      if (id == R.id.home){
////
////
            HomeFragment fragment = new HomeFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment, "Home");
            fragmentTransaction.commit();
////
//        }else if (id == R.id.pengaturan){
//
//            Intent intent = new Intent(HomeActivity.this, PengaturanActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
////
//        }else if (id == R.id.alat_musik){
//
//            Intent intent = new Intent(HomeActivity.this, LaporRaziaActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//
        }else if (id == R.id.about){
//
            Intent intent = new Intent(HomeActivity.this, AboutActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
//
        }else if (id == R.id.keluar){
              firebaseAuth.signOut();
             LoginManager.getInstance().logOut();
              mGoogleSignInClient.signOut();
//
//
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }

        mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    boolean ClickDOuble = false;
    @Override
    public void onBackPressed() {

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
        {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }else
        {
            super.onBackPressed();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.action_setting, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.pengaturan:
                Intent intent = new Intent(HomeActivity.this, PengaturanActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }


    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment, "blank");
        fragmentTransaction.commit();
    }

    public void loadUserInformation(){

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null) {

            if (firebaseUser.getPhotoUrl() == null){
                navImageView.setEnabled(false);
            }else{
                Glide.with(this).load(firebaseUser.getPhotoUrl().toString()).into(navImageView);

            }

            navNamaUser.setText(firebaseUser.getDisplayName());
            navEmailUser.setText(firebaseUser.getEmail());



            if (firebaseUser.getPhotoUrl() == null) {

                for (UserInfo profile : firebaseUser.getProviderData()) {
                    if (profile.getPhotoUrl() == null){
                        navEmailUser.setEnabled(false);
                    }else{

                        Glide.with(this).load(profile.getPhotoUrl().toString()).into(navImageView);

                    }

                }
            }
            if (firebaseUser.getDisplayName() == null) {

                for (UserInfo profile : firebaseUser.getProviderData()) {

                    navNamaUser.setText(profile.getDisplayName());

                }
            }
            if (firebaseUser.getEmail() == null) {

                for (UserInfo profile : firebaseUser.getProviderData()) {

                    navEmailUser.setText(profile.getEmail());

                }

            }
        }
    }

}
