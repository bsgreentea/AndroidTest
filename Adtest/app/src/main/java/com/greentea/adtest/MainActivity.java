package com.greentea.adtest;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        mAdView = findViewById(R.id.adView);

//        AdRequest adRequest = new AdRequest.Builder()
//                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//                .build();

//        mAdView.setAdListener(new AdListener(){
//            @Override
//            public void onAdClicked() {
//                // Code to be executed when the user clicks on an ad.
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/bsgreentea/AndroidTest"));
//                startActivity(intent);
//            }
//        });

        AdRequest adRequest =  new AdRequest.Builder().build();

        mAdView.loadAd(adRequest);
    }
}
