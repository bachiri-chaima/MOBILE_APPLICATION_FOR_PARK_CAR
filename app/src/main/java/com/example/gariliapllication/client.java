package com.example.gariliapllication;

import android.text.Editable;
import android.widget.ImageView;

import java.util.ArrayList;

public class client {
    int id_client;
    String nom_client;
    String prenom_client;
    String telephone_client;
    String Email_client;
    String mot_de_pase_client;
    ImageView image_client;
    static String email;
    boolean estadmin;
    public client(){

    }

    public client(int id_client, String nom_client, String prenom_client, String telephone_client, String email_client, String mot_de_pase_client,ImageView image_client,boolean estadmin) {
        this.id_client = id_client;
        this.nom_client = nom_client;
        this.prenom_client = prenom_client;
        this.telephone_client = telephone_client;
        this.Email_client = email_client;
        this.mot_de_pase_client = mot_de_pase_client;
        this.image_client = image_client;
        this.estadmin=estadmin;
    }
    public client(String nom_client,String prenom_client ,String telephone_client , String email_client, String mot_de_pase_client,boolean estadmin) {
        this.nom_client = nom_client;
        this.prenom_client = prenom_client;
        this.telephone_client = telephone_client;
        this.Email_client = email_client;
        this.mot_de_pase_client = mot_de_pase_client;
        this.estadmin=estadmin;
    }
    public client( String email_client, String mot_de_pase_client,boolean estadmin) {
        this.Email_client = email_client;
        this.mot_de_pase_client = mot_de_pase_client;
        this.estadmin=estadmin;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public String getNom_client() {
        return nom_client;
    }

    public void setNom_client(String nom_client) {
        this.nom_client = nom_client;
    }

    public String getPrenom_client() {
        return prenom_client;
    }

    public void setPrenom_client(String prenom_client) {
        this.prenom_client = prenom_client;
    }

    public String getTelephone_client() {
        return telephone_client;
    }

    public void setTelephone_client(String telephone_client) {
        this.telephone_client = telephone_client;
    }

    public String getEmail_client() {
        return Email_client;
    }

    public void setEmail_client(String email_client) {
        Email_client = email_client;
    }

    public String getMot_de_pase_client() {
        return mot_de_pase_client;
    }

    public void setMot_de_pase_client(String mot_de_pase_client) {
        this.mot_de_pase_client = mot_de_pase_client;
    }

    public ImageView getImage_client() {
        return image_client;
    }

    public void setImage_client(ImageView image_client) {
        this.image_client = image_client;
    }

    public boolean isEstadmin() {
        return estadmin;
    }

    public void setEstadmin(boolean estadmin) {
        this.estadmin = estadmin;
    }
}
