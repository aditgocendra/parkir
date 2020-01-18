package com.trex.parkirBDL;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.trex.parkirbandarlampung.R;

public class SplashScreen extends AppCompatActivity {

    ProgressBar progressBar;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_sreen);
        progressBar = findViewById(R.id.progress_bar);
        textView = findViewById(R.id.text_view);

        progressBar.setMax(100);
        progressBar.setScaleY(3f);

//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(
//                    "com.trex.parkirbandarlampung",
//                    PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//
//        } catch (NoSuchAlgorithmException e) {
//
//        }

        progressAnimation();
    }
    public void progressAnimation() {
        ProgressBarAnimation anim = new ProgressBarAnimation(this, progressBar, textView, 0f,
                100f);
        anim.setDuration(3000);
        progressBar.setAnimation(anim);

    }


}