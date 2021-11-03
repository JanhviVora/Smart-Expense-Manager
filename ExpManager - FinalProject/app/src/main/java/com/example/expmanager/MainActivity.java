package com.example.expmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //Var for Anim
    Animation top, bottom;
    String Tag = "Main Activity";

    ImageView image;
    TextView header, tag;

    private static int SPLASH_SCREEN = 3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Animations
        top = AnimationUtils.loadAnimation(this,R.anim.top_anim_main);
        bottom = AnimationUtils.loadAnimation(this,R.anim.bottom_anim_main);

        //hooks
        image = findViewById(R.id.imageView);
        header = findViewById(R.id.textHeader);
        tag = findViewById(R.id.textTag);

        image.setAnimation(top);
        header.setAnimation(bottom);
        tag.setAnimation(bottom);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, StartActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);

        Log.d(Tag,"Main Activity Splash");
    }
}