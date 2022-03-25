package com.example.gariliapllication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class profilefragement extends Fragment {
    boolean admin = false;
    String NOM ,PRENOM,NUMERO,EMAIL;
    GoogleSignInClient mGoogleSignInClient;
    Button sign_out , modifier;
    ImageView imageView;
    TextView nom,prnom,numero,email;
    BDHelper db;
    ArrayList<String> nom_liste,prenom_liste,numer_liste,email_liste;
    Bitmap image;
    public String data;

    CardView cardView;
    @SuppressLint("WrongThread")
    @Override
    public View onCreateView( LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_profilefragement, container, false);

        cardView = root.findViewById(R.id.card_view_image);

        sign_out = root.findViewById(R.id.sign_out);
        imageView = root.findViewById(R.id.image_profile);
        nom = root.findViewById(R.id.nom_profil);
        prnom = root.findViewById(R.id.prenom_profil);
        numero = root.findViewById(R.id.numero_profil);
        email = root.findViewById(R.id.email_profil);
        modifier = root.findViewById(R.id.update_profil);



        //afficher les information

        Bundle intent = getActivity().getIntent().getExtras();

            Bundle bundle = this.getArguments();
            data = bundle.getString("id");

            nom_liste = new ArrayList<>();
            prenom_liste = new ArrayList<>();
            numer_liste = new ArrayList<>();
            email_liste = new ArrayList<>();


            db = new BDHelper(getActivity());

            image = db.getimage_db(data);



            AjouterData(data);


            nom.setText(nom_liste.get(0).toString());
            prnom.setText(prenom_liste.get(0).toString());
            numero.setText(numer_liste.get(0).toString());
            email.setText(email_liste.get(0).toString());
            imageView.setImageBitmap(image);


        //les information google


        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            Uri personPhoto = acct.getPhotoUrl();

            //Glide.with(this).load(String.valueOf(personPhoto)).into(imageView);
            nom.setText(personName);
            email.setText(personEmail);
            //insret image
            try {
                final InputStream imagestream = getActivity().getContentResolver().openInputStream(personPhoto);
                final Bitmap imagebitmap = BitmapFactory.decodeStream(imagestream);
                imageView.setImageBitmap(imagebitmap);

                //save to database

                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                imagebitmap.compress(Bitmap.CompressFormat.PNG,100,byteArray);
                byte[] image = byteArray.toByteArray();

                db = new BDHelper(getActivity());
                boolean insretImage = db.Updateimage(data,image);
                if (insretImage == true){

                    Toast.makeText(getActivity(),"Image de profile modifier",Toast.LENGTH_LONG).show();

                }else {

                    Toast.makeText(getActivity(),"Echec de modification",Toast.LENGTH_LONG).show();

                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }}



            modifier.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NOM= nom.getText().toString().trim();
                    PRENOM= prnom.getText().toString().trim();
                    NUMERO= numero.getText().toString().trim();
                    EMAIL= email.getText().toString().trim();


                    boolean validationemail = validationEmailadress(EMAIL);
                    if (validationemail == true){

                        if (admin == false){

                            db = new BDHelper(getActivity());
                            boolean update_admin = db.Updateclient(data,NOM,PRENOM,NUMERO,EMAIL);

                            //interface admin
                            if (update_admin == true){

                                Toast.makeText(getActivity(),"Modification succes ",Toast.LENGTH_LONG).show();

                            }else {

                                Toast.makeText(getActivity(),"problem de modification ",Toast.LENGTH_LONG).show();
                            }

                        }
                    }else {

                        Toast.makeText(getActivity(),"Email est non valide", Toast.LENGTH_SHORT).show();

                    }
                }
            });



        //signout google
        // Configure sign-in to request the user's ID, email address, and basic
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EMAIL= email.getText().toString().trim();
                switch (v.getId()) {
                   case R.id.sign_out:
                        signOut(data,EMAIL);
                        break;
                }

            }
        });



        //chager l'image

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.setType("image/*");
                intent1.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent1,"Image"),1);
            }
        });

        return root;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
           Uri uri = data.getData();
           // Glide.with(this).load(String.valueOf(uri)).into(imageView);
            //convert url to bitmap
            try {
                final InputStream imagestream = getActivity().getContentResolver().openInputStream(uri);
                final Bitmap imagebitmap = BitmapFactory.decodeStream(imagestream);
                imageView.setImageBitmap(imagebitmap);

                //save to database

                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                imagebitmap.compress(Bitmap.CompressFormat.PNG,100,byteArray);
                byte[] image = byteArray.toByteArray();
                Bundle bundle = this.getArguments();
                String data1 = bundle.getString("id");

                db = new BDHelper(getActivity());
                boolean insretImage = db.Updateimage(data1,image);
                if (insretImage == true){

                    Toast.makeText(getActivity(),"Image de profile modifier",Toast.LENGTH_LONG).show();

                }else {

                    Toast.makeText(getActivity(),"Echec de modification",Toast.LENGTH_LONG).show();

                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    private void signOut(String id,String email) {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //ajouter delate of database

                        db = new BDHelper(getActivity());
                        boolean delate = db.delate_user_google(id,email);
                        if (delate == true){
                        Toast.makeText(getActivity(),"Log out est bien réussie \n Merci d'avoir utuliser notre application",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity(),loginActivity.class);
                        startActivity(intent);}
                        else {
                            Toast.makeText(getActivity(),"Echec Log out",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    void AjouterData(String id){
        Cursor cursor = db.readprofil(id);
        if (cursor.getCount() == 0 ){

            Toast.makeText(getActivity(),"pas de donné", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){
                nom_liste.add(cursor.getString(0));
                prenom_liste.add(cursor.getString(1));
                numer_liste.add(cursor.getString(2));
                email_liste.add(cursor.getString(3));
            }
        }
    }
    private  boolean validationEmailadress(String email){

        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return true;

        }else {
            return  false;
        }}



    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}