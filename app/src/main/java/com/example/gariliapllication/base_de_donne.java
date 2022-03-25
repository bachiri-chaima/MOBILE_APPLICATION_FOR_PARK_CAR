package com.example.gariliapllication;

import android.widget.ImageView;

public class base_de_donne {

    public static final int DATABASE_VERSION =10;
    public static final String DATABASE_NAME="GARILI.db";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOM = "name";
    public static final String COLUMN_PRENOM = "prenom";
    public static final String COLUMN_NUMERO = "numero";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_MOT_DE_PASSE = "mot_de_passe";
    public static final String COLUMN_EST_ADMIN = "est_admin ";
    public static final String COLUMN_IMAGE = "image";


    public static final String COLUMN_ID_PLACE = "id_place";
    public static final String COLUMN_NOM_PLACE = "name";
    public static final String COLUMN_EST_LIBRE_PLACE = "est_libre ";

    public static final String COLUMN_ID_VEHICULE = "id_vehicule";
    public static final String COLUMN_MATRICULE_VEHICULE = "matricule";


    public static final String COLUMN_ID_RESERVATION = "id_reservation";
    public static final String COLUMN_DATE_ENT_RESERVATION = "date_ent";
    public static final String COLUMN_HEURE_ENT_RESERVATION = "heure_ent";
    public static final String COLUMN_HEURE_SRT_RESERVATION = "heure_srt";
    public static final String COLUMN_PRIX_RESERVATION = "prix";
    public static final String COLUMN_CONFIRMATION_RESERVATION = "confirmation";
    public static final String COLUMN_ID_USR_RESERVATION = "id_user";
    public static final String COLUMN_ID_PLACE_RESERVATION = "id_place";
    public static final String COLUMN_ID_VEHICULE_RESERVATION = "id_vehicule";

}
