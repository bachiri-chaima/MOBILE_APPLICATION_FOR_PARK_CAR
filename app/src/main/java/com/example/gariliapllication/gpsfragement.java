package com.example.gariliapllication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;


public class gpsfragement extends Fragment  implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks , GoogleApiClient.OnConnectionFailedListener  {
    boolean isPermissionGranted;
    GoogleMap mgoogleMap;
    FloatingActionButton bouton_map_localisation;
    FloatingActionButton bouton_map_localisation_parking;
    FloatingActionButton route;

    private FusedLocationProviderClient mlocationClient;
    private int GPS_REQUEST_CODE = 9001;
    MarkerOptions garili;
    Location loc;

    Polyline line;

    private Animation rotation ;
    private Animation rotation2;
    private Animation rotation3;
    private Animation rotation4;
    boolean click = false;

    @Override
    public View onCreateView( LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root =(ViewGroup)inflater.inflate(R.layout.fragment_gpsfragement,container,false);

        bouton_map_localisation = root.findViewById(R.id.button_map_location);
        bouton_map_localisation_parking = root.findViewById(R.id.button_map_location_parking);
        route = root.findViewById(R.id.route);

        rotation = AnimationUtils.loadAnimation(getActivity(),R.anim.open_floatingbutton);
        rotation2 = AnimationUtils.loadAnimation(getActivity(),R.anim.from_bouton_anim);
        rotation3 = AnimationUtils.loadAnimation(getActivity(), R.anim.to_bouton_anim);
        rotation4 = AnimationUtils.loadAnimation(getActivity(),R.anim.close_floatingbutton);

        garili = new MarkerOptions().position(new LatLng(35.2933710, -1.1180151)).title("Garili parking");

        CheckMypermission();
        //initialiser map
        initMap();

        mlocationClient = new FusedLocationProviderClient(getActivity());

        bouton_map_localisation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get current LOcation
                if (line == null){
                getCurrLoc();
                Addbutton();
                mgoogleMap.addMarker(garili).showInfoWindow();}
                else {
                    line.setVisible(false);
                    getCurrLoc();
                    Addbutton();
                    mgoogleMap.addMarker(garili).showInfoWindow();
                }

            }
        });
        bouton_map_localisation_parking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoLocation(garili.getPosition().latitude,garili.getPosition().longitude);


            };
        });
        route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Direction();

            }
        });


        return root;

    }

    private void Direction() {
        //if the client dosn't have a map instelled then direction to google play
        try {
            //si google map est installer
            Uri uri = Uri.parse("http://maps.google.com/maps?f=d&daddr=35.2933710,-1.1180151");
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            //si google maps non installer
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }


    private void Addbutton() {
        setVibility(click);
        setAnimation(click);
        click = !click;
    }

    private void setAnimation(boolean click) {
        if (!click){
        bouton_map_localisation_parking.startAnimation(rotation2);
        route.startAnimation(rotation2);
        bouton_map_localisation.startAnimation(rotation);}
        else {
            bouton_map_localisation_parking.startAnimation(rotation3);
            route.startAnimation(rotation3);
            bouton_map_localisation.startAnimation(rotation4);
        }
    }

    private void setVibility(boolean click) {
        if (!click){
            bouton_map_localisation_parking.setVisibility(View.VISIBLE);
            route.setVisibility(View.VISIBLE);
        }else {
            bouton_map_localisation_parking.setVisibility(View.INVISIBLE);
            route.setVisibility(View.INVISIBLE);
        }

    }

    private void initMap() {

        if(isPermissionGranted){

            if (isGPSenable()){
            SupportMapFragment supportMapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map_view);
            supportMapFragment.getMapAsync(this);}
        }
    }

    private boolean isGPSenable(){
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        boolean providerEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (providerEnable){
            return true;
        }
        else {
            AlertDialog alertDialog = new AlertDialog.Builder(getContext()).setTitle("Permission GPS")
                    .setMessage("Le GPS est requis pour que cette application fonctionne\n" +
                            "veuillez activer le GPS").setPositiveButton("Oui", (((dialogInterface, i) ->
                    {
                        Intent intent = new Intent((Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        startActivityForResult(intent,GPS_REQUEST_CODE);
                    })))
                    .setCancelable(false)
                    .show();
        }
        return false;
    }
    @SuppressLint("MissingPermission")
    private void getCurrLoc() {
        mlocationClient.getLastLocation().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                loc = task.getResult();
                gotoLocation(loc.getLatitude(), loc.getLongitude());
            }


             line = mgoogleMap.addPolyline(new PolylineOptions()
                    .add(new LatLng(loc.getLatitude(),loc.getLongitude()), new LatLng(35.2933710, -1.1180151))
                    .width(5)
                    .color(Color.RED));
        });
    }

    private void gotoLocation(double latitude, double longitude) {
        LatLng latLng = new LatLng(latitude,longitude);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,18);
        mgoogleMap.moveCamera(cameraUpdate);
        mgoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }


    private void CheckMypermission() {
        //cheking device permition of google maps
        Dexter.withContext(getActivity()).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                //si l'utilisateur accorde l'autorisation
                isPermissionGranted = true;

            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                //si nous voulons faire quelque chose si nous refusons la permission
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package",getActivity().getPackageName(),"");
                intent.setData(uri);
                startActivity(intent);

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                //si l'utilisateur n'accorde pas l'autorisation ni ne la refuse, nous devons continuer à lui montrer la boîte d'alerte
                permissionToken.continuePermissionRequest();

            }
        }).check();


    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady( GoogleMap googleMap) {
        mgoogleMap = googleMap;
        mgoogleMap.setMyLocationEnabled(true); //arcequ'il avoir des prbleme de localisation



    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GPS_REQUEST_CODE){
            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

            boolean prviderEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (prviderEnable){
                Toast.makeText(getActivity(),"le GPS est activé",Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getActivity(),"le GPS est non activé",Toast.LENGTH_LONG).show();
            }
        }
    }

}

