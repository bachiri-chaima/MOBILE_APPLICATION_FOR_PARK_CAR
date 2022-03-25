package com.example.gariliapllication;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {
    LottieAnimationView lottieAnimationView;
    ImageView imageView;
    ImageView logo;
    Animation anim;

    private static final int NUM_PAGE =3;
    private ViewPager viewPager;
    private ScreenSlidePagerAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.garilipage1);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        logo = findViewById(R.id.logo);
        imageView = findViewById(R.id.imagebackground);
        lottieAnimationView=findViewById(R.id.progrees);

        lottieAnimationView.animate().translationY(-6000).setStartDelay(1700);
        logo.animate().translationY(-2000).setDuration(2000).setStartDelay(1100);
        imageView.animate().translationY(-2000).setDuration(1000).setStartDelay(1500);

        viewPager=findViewById(R.id.pager);
        pagerAdapter=new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        anim= AnimationUtils.loadAnimation(this,R.anim.liquidscreen_buton_text);
        viewPager.startAnimation(anim);



        if (getIntent().getBooleanExtra("Exit",false)){
            finish();
        }
    }
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter( FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0: Bordurefragement1 tab1 = new Bordurefragement1();
                    return tab1;
                case 1: Bordurefragement2 tab2= new Bordurefragement2();
                System.out.println(position);
                    return tab2;
                case 2: Bordurefragement3 tab3 = new Bordurefragement3();
                    return tab3;
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGE;
        }
    }



    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        }
    @Override
    protected void onStop() {
        super.onStop();
        }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        }

}