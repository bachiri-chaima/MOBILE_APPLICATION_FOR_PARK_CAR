package com.example.gariliapllication;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.TextView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

public class home_garili extends AppCompatActivity  implements DrawerAdapter.OnItemSelectedListener{

    MeowBottomNavigation meowBottomNavigation;
    private final int ID_HOME = 2;
    private final int ID_GPS = 3;
    private final int ID_PROFILE = 4;
    private final int ID_EXIT = 5;


    private static final int POS_CLOSE=0;
    private static final int POS_HOME=1;
    private static final int POS_LOCALISATION=2;
    private static final int POS_HISTORIQUE=3;
    private static final int POS_LOGOUT=4;

    private String[] screenTitles;
    private Drawable[] screenIcons;

    private SlidingRootNav slidingRootNav;

    String data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_garili);

        Intent intent = getIntent();
        data = intent.getStringExtra("id");

        //Toast.makeText(home_garili.this,data,Toast.LENGTH_SHORT).show();




        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        slidingRootNav = new SlidingRootNavBuilder(this)
                .withDragDistance(180)
                .withRootViewScale(0.75f)
                .withRootViewElevation(25)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.fragment_menufragement).inject();

        meowBottomNavigation = findViewById(R.id.meowBottomNavigation);


        meowBottomNavigation.add(new MeowBottomNavigation.Model(ID_HOME,R.drawable.ic_baseline_home_24));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(ID_GPS,R.drawable.ic_baseline_location_on_24));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(ID_PROFILE,R.drawable.ic_baseline_account_circle_24));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(ID_EXIT,R.drawable.ic_baseline_exit_to_app_24));


        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_CLOSE),
                createItemFor(POS_HOME).setcheked(true),
                createItemFor(POS_LOCALISATION),
                createItemFor(POS_HISTORIQUE),
                new Spaceitem(430),
                createItemFor(POS_LOGOUT)
        ));
        adapter.setListener(this);
        RecyclerView list = findViewById(R.id.drawer_list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(POS_HOME);



       //getSupportFragmentManager().beginTransaction().replace(R.id.fragementhome,new Homefragement()).commit();
        //meowBottomNavigation.setSelectedIconColor(R.drawable.ic_baseline_home_24);


        meowBottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

                Fragment fragment=null;
                Bundle bundle = new Bundle();
                switch (item.getId()){


                        case ID_HOME : fragment = new Homefragement();
                            bundle.putString("id",data.toString());
                            fragment.setArguments(bundle);
                            break;
                        case ID_GPS : fragment = new gpsfragement();
                            break;
                        case ID_PROFILE : fragment = new profilefragement();
                            bundle.putString("id",data.toString());
                            fragment.setArguments(bundle);
                            break;
                        case ID_EXIT : fragment = new exitapk();

                            AlertDialog.Builder builder = new AlertDialog.Builder(home_garili.this);
                            builder.setMessage("Vous voulez vraiment sortir?");
                            builder.setCancelable(true);
                            builder.setNegativeButton("Oui", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                            builder.setPositiveButton("Non", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();

                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment,fragment).commit();

            }
        });
        meowBottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                switch (item.getId()){

            }
            }
        });



        meowBottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override

            public void onShowItem(MeowBottomNavigation.Model item) {

            }
            });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
     }

    private Draweritem createItemFor(int position){
        return  new SimpleItem(screenIcons[position],screenTitles[position]).withIconTint(color(R.color.pink2))
                .withTextTint(color(R.color.black))
                .withSelectedIconTint(color(R.color.pink2)).
                        withSelectedTextTint(color(R.color.pink2));
    }
    @ColorInt
    private int color(@ColorRes int res){
        return ContextCompat.getColor(this,res);
    }
    private String[] loadScreenTitles() {
        return  getResources().getStringArray(R.array.id_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.id_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i=0; i<ta.length();i++){
            int id = ta.getResourceId(i,0);
            if (id!=0){
                icons[i]= ContextCompat.getDrawable(this,id);
            }
        }
        ta.recycle();
        return icons;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onItemSelected(int position) {

        //for the menu items
        Bundle bundle = new Bundle();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (position == POS_HOME){
            Homefragement homefragement = new Homefragement();
            transaction.replace(R.id.frame_fragment,homefragement);

        }
        if (position == POS_LOCALISATION){
            gpsfragement gpsfragement = new gpsfragement();
            transaction.replace(R.id.frame_fragment,gpsfragement);
            meowBottomNavigation.show(ID_GPS,true);
        }
        if (position == POS_HISTORIQUE){
            Histpriquefragement histpriquefragement = new Histpriquefragement();
            bundle.putString("id2",data.toString());
            histpriquefragement.setArguments(bundle);
            transaction.replace(R.id.frame_fragment,histpriquefragement);
           /* meowBottomNavigation.show(ID_HOME,false);
            meowBottomNavigation.show(ID_GPS,false);
            meowBottomNavigation.show(ID_PROFILE,false);*/

        }
        if (position == POS_LOGOUT){
            /*exitapk exitapk = new exitapk();
            transaction.replace(R.id.frame_fragment,exitapk);*/
            /*Intent intent = new Intent(home_garili.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("Exit",true);
            startActivity(intent);*/


        }
        slidingRootNav.closeMenu();
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}