package com.example.gariliapllication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapterrecucle_admin extends RecyclerView.Adapter<Adapterrecucle_admin.MyviewHolder>{

    private Context context;
    private ArrayList utilisateur_id,utilisateur_nom,utilisateur_prenom,utilisateur_email,utilisateur_numero;
    Activity activity;

    Animation anim;
    Adapterrecucle_admin(Activity activity,Context context, ArrayList utilisateur_id, ArrayList utilisateur_nom, ArrayList utilisateur_prenom, ArrayList utilisateur_numero, ArrayList utilisateur_email) {

        //adaptateur pour l'affichage admin
        this.activity = activity;
        this.context = context ;
        this.utilisateur_id = utilisateur_id;
        this.utilisateur_nom = utilisateur_nom;
        this.utilisateur_prenom = utilisateur_prenom;
        this.utilisateur_numero = utilisateur_numero;
        this.utilisateur_email = utilisateur_email;

    }



    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_raw_admin,parent,false);
        return  new Adapterrecucle_admin.MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        //viewHolder pour avoir les donne de base de donne par collone
        holder.id_text.setText(String.valueOf(utilisateur_id.get(position)));
        holder.nom_text.setText(String.valueOf(utilisateur_nom.get(position)));
        holder.prenom_text.setText(String.valueOf(utilisateur_prenom.get(position)));
        holder.numero_text.setText(String.valueOf(utilisateur_numero.get(position)));
        holder.email_text.setText(String.valueOf(utilisateur_email.get(position)));

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Update_admin.class);
                intent.putExtra("id",String.valueOf(utilisateur_id.get(position)));
                intent.putExtra("nom",String.valueOf(utilisateur_nom.get(position)));
                intent.putExtra("prenom",String.valueOf(utilisateur_prenom.get(position)));
                intent.putExtra("numero",String.valueOf(utilisateur_numero.get(position)));
                intent.putExtra("email",String.valueOf(utilisateur_email.get(position)));

                activity.startActivityForResult(intent,1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return utilisateur_id.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {

        TextView id_text,nom_text,prenom_text,numero_text,email_text;
        LinearLayout linearLayout;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            id_text = itemView.findViewById(R.id.affiche_id_admin);
            nom_text = itemView.findViewById(R.id.nom_admin);
            prenom_text = itemView.findViewById(R.id.prenom_admin);
            numero_text = itemView.findViewById(R.id.numero_admin);
            email_text = itemView.findViewById(R.id.email_admin);
            linearLayout = itemView.findViewById(R.id.layout_update_admin);
            //animation
            anim = AnimationUtils.loadAnimation(context,R.anim.translate_anim_liste);
            linearLayout.setAnimation(anim);
        }
    }
}
