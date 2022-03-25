package com.example.gariliapllication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import static com.example.gariliapllication.base_de_donne.COLUMN_CONFIRMATION_RESERVATION;
import static com.example.gariliapllication.base_de_donne.COLUMN_DATE_ENT_RESERVATION;
import static com.example.gariliapllication.base_de_donne.COLUMN_EMAIL;
import static com.example.gariliapllication.base_de_donne.COLUMN_EST_ADMIN;
import static com.example.gariliapllication.base_de_donne.COLUMN_EST_LIBRE_PLACE;
import static com.example.gariliapllication.base_de_donne.COLUMN_HEURE_ENT_RESERVATION;
import static com.example.gariliapllication.base_de_donne.COLUMN_HEURE_SRT_RESERVATION;
import static com.example.gariliapllication.base_de_donne.COLUMN_ID;
import static com.example.gariliapllication.base_de_donne.COLUMN_ID_PLACE;
import static com.example.gariliapllication.base_de_donne.COLUMN_ID_PLACE_RESERVATION;
import static com.example.gariliapllication.base_de_donne.COLUMN_ID_RESERVATION;
import static com.example.gariliapllication.base_de_donne.COLUMN_ID_USR_RESERVATION;
import static com.example.gariliapllication.base_de_donne.COLUMN_ID_VEHICULE;
import static com.example.gariliapllication.base_de_donne.COLUMN_ID_VEHICULE_RESERVATION;
import static com.example.gariliapllication.base_de_donne.COLUMN_IMAGE;
import static com.example.gariliapllication.base_de_donne.COLUMN_MATRICULE_VEHICULE;
import static com.example.gariliapllication.base_de_donne.COLUMN_MOT_DE_PASSE;
import static com.example.gariliapllication.base_de_donne.COLUMN_NOM;
import static com.example.gariliapllication.base_de_donne.COLUMN_NOM_PLACE;
import static com.example.gariliapllication.base_de_donne.COLUMN_NUMERO;
import static com.example.gariliapllication.base_de_donne.COLUMN_PRENOM;
import static com.example.gariliapllication.base_de_donne.COLUMN_PRIX_RESERVATION;
import static com.example.gariliapllication.base_de_donne.DATABASE_NAME;
import static com.example.gariliapllication.base_de_donne.DATABASE_VERSION;


public class BDHelper extends SQLiteOpenHelper {


    public BDHelper( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = " CREATE TABLE IF NOT EXISTS Login ( " +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOM + " TEXT NOT NULL, " +
                COLUMN_PRENOM + " TEXT, " +
                COLUMN_NUMERO + " TEXT, " +
                COLUMN_EMAIL + " TEXT NOT NULL UNIQUE, " +
                COLUMN_MOT_DE_PASSE + " TEXT UNIQUE, " +
                COLUMN_EST_ADMIN + " INTEGER NOT NULL," +
                COLUMN_IMAGE + " BLOB " +
                " );";
        db.execSQL(CREATE_LOGIN_TABLE);

        String CREATE_PLACE_TABLE = " CREATE TABLE IF NOT EXISTS Place ( " +
                COLUMN_ID_PLACE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOM_PLACE + " TEXT NOT NULL, " +
                COLUMN_EST_LIBRE_PLACE + " INTEGER NOT NULL " +
                " );";
        db.execSQL(CREATE_PLACE_TABLE);

        String CREATE_VEHICULE_TABLE = " CREATE TABLE IF NOT EXISTS Vehicule ( " +
                COLUMN_ID_VEHICULE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MATRICULE_VEHICULE + " TEXT NOT NULL " +
                " );";

        db.execSQL(CREATE_VEHICULE_TABLE);

        String CREATE_RESERVATION_TABLE = " CREATE TABLE IF NOT EXISTS Reservation ( " +
                COLUMN_ID_RESERVATION + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DATE_ENT_RESERVATION + " TEXT, " +
                COLUMN_HEURE_ENT_RESERVATION + " TEXT, " +
                COLUMN_HEURE_SRT_RESERVATION + " TEXT, " +
                COLUMN_PRIX_RESERVATION + " TEXT, " +
                COLUMN_CONFIRMATION_RESERVATION + " INTEGER NOT NULL, " +
                COLUMN_ID_USR_RESERVATION + " INTEGER, " +
                COLUMN_ID_PLACE_RESERVATION + " INTEGER, " +
                COLUMN_ID_VEHICULE_RESERVATION + " INTEGER, " +
                " FOREIGN KEY(" + COLUMN_ID_USR_RESERVATION + ") REFERENCES Login (" + COLUMN_ID + "), "  +
                " FOREIGN KEY(" + COLUMN_ID_PLACE_RESERVATION + ") REFERENCES Place (" + COLUMN_ID_PLACE + "), "  +
                " FOREIGN KEY(" + COLUMN_ID_VEHICULE_RESERVATION + ") REFERENCES Vehicule (" + COLUMN_ID_VEHICULE + ") "  +
                " );";

        db.execSQL(CREATE_RESERVATION_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Login ;";
        db.execSQL(sql);
        onCreate(db);

        String sql_place = "DROP TABLE IF EXISTS Place ;";
        db.execSQL(sql_place);
        onCreate(db);


        String sql_vehicule = "DROP TABLE IF EXISTS Vehicule ;";
        db.execSQL(sql_vehicule);
        onCreate(db);

        String sql_RESERVATION = "DROP TABLE IF EXISTS Reservation ;";
        db.execSQL(sql_RESERVATION);
        onCreate(db);

    }

    public Bitmap getimage_db(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Login WHERE " + COLUMN_ID + " = ? ",new String[]{row_id});
        cursor.moveToFirst();
        byte[] bitmap = cursor.getBlob(7);
        Bitmap image = BitmapFactory.decodeByteArray(bitmap,0,bitmap.length);
        return image;
    }

    public boolean insertPlace(String nom,boolean est_libre){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NOM_PLACE,nom);
        contentValues.put(COLUMN_EST_LIBRE_PLACE,est_libre);

        Long resultas = db.insert("Place",null, contentValues);
        if ( resultas == -1){
            //echec
            return  false;
        }else {
            //success
            return true;
        }
    }

    public boolean insertmatricule(String matricule){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_MATRICULE_VEHICULE,matricule);

        Long resultas = db.insert("Vehicule",null, contentValues);
        if ( resultas == -1){
            //echec
            return  false;
        }else {
            //success
            return true;
        }
    }
    public boolean checkplace(String nom){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Place WHERE " + COLUMN_NOM_PLACE + " = ? ",new String[] {nom});
        if (cursor.getCount()>0){
            //il existe
            return true;
        }else {
            //il n'existe pas
            return false;
        }
    }


    public boolean insertData(String nom,String prenom, String telephone, String email, String mdp ,boolean est_admin ,byte[] image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NOM,nom);
        contentValues.put(COLUMN_PRENOM,prenom);
        contentValues.put(COLUMN_NUMERO,telephone);
        contentValues.put(COLUMN_EMAIL,email);
        contentValues.put(COLUMN_MOT_DE_PASSE,mdp);
        contentValues.put(COLUMN_EST_ADMIN,est_admin);
        contentValues.put(COLUMN_IMAGE, String.valueOf(image));

        Long resultas = db.insert("Login",null, contentValues);
        if ( resultas == -1){
            return  false;
        }else {
            return true;
        }
    }

    public boolean insertreservation(String date,String heure_ent,String heure_str,String prix,boolean confirmation,int user,int place,int vehicule){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DATE_ENT_RESERVATION,date);
        contentValues.put(COLUMN_HEURE_ENT_RESERVATION,heure_ent);
        contentValues.put(COLUMN_HEURE_SRT_RESERVATION,heure_str);
        contentValues.put(COLUMN_PRIX_RESERVATION,prix);
        contentValues.put(COLUMN_CONFIRMATION_RESERVATION,confirmation);
        contentValues.put(COLUMN_ID_USR_RESERVATION,user);
        contentValues.put(COLUMN_ID_PLACE_RESERVATION,place);
        contentValues.put(COLUMN_ID_VEHICULE_RESERVATION,vehicule);


        Long resultas = db.insert("Reservation",null, contentValues);
        if ( resultas == -1){
            return  false;
        }else {
            return true;
        }
    }


    public boolean checkUser(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Login WHERE " + COLUMN_EMAIL + " = ? ",new String[] {email});
        if (cursor.getCount()>0){
            return true;
        }else {
            return false;
        }
    }
    public boolean check_user_password(String email,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Login WHERE " + COLUMN_EMAIL + " = ?" + "AND " + COLUMN_MOT_DE_PASSE + " = ? ",new String[] {email,password});
        if (cursor.getCount()>0){
            return true;
        }else {
            return false;
        }
    }
    public boolean chekadmin(Boolean est_admin){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Login WHERE " + COLUMN_EST_ADMIN + " = ? ",new String[] {String.valueOf(est_admin.equals(true))});
        if (cursor.getCount()>0){
            return true;
        }else {
            return false;
        }
    }
    public boolean chekEmail_admin(Boolean est_admin,String email){
        SQLiteDatabase db = this.getWritableDatabase();
        est_admin = true;
        Cursor cursor = db.rawQuery("SELECT * FROM Login WHERE " + COLUMN_EMAIL + " = ?" + "AND " + COLUMN_EST_ADMIN  + " = ? ",new String[] {email,"1"});
        if (cursor.getCount()>0){
            return true;
        }else {
            return false;
        }
    }


    Cursor readallusers()
    {
        String query = "SELECT " + COLUMN_ID + " , " + COLUMN_NOM + " , " + COLUMN_PRENOM + " , " + COLUMN_NUMERO + " , " + COLUMN_EMAIL + " FROM Login";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            //si la base de donné n'est pas null
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    Cursor readallplace()
    {
        String query = "SELECT * FROM Place ";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            //si la base de donné n'est pas null
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }
    Cursor readallreservation()
    {
        String query = "SELECT * FROM Reservation WHERE " + COLUMN_CONFIRMATION_RESERVATION + " = 1";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            //si la base de donné n'est pas null
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    Cursor readallplacecharge()
    {
        String query = "SELECT * FROM Place WHERE " + COLUMN_EST_LIBRE_PLACE + " = 1";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            //si la base de donné n'est pas null
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    Cursor read_place_name_client_join(String id_usr)
    {
        String query = " SELECT Place.id_place,name,Reservation.id_place,confirmation FROM Place \n " +
                " INNER JOIN Reservation ON Place.id_place = Reservation.id_place and confirmation = 1 AND id_user = " + id_usr;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            //si la base de donné n'est pas null
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    Cursor read_vehicule_name_client_join(String id_usr)
    {
        String query = " SELECT Vehicule.id_vehicule,matricule,Reservation.id_vehicule,confirmation,id_reservation,id_user FROM Vehicule \n " +
                " INNER JOIN Reservation ON Vehicule.id_vehicule = Reservation.id_vehicule AND confirmation = 1 AND id_user = " + id_usr;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            //si la base de donné n'est pas null
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    Cursor read_all_email_admin_join()
    {
        String query = " SELECT Login.email FROM Login \n " +
                " INNER JOIN Reservation ON Login.id = Reservation.id_user AND confirmation = 1 ";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            //si la base de donné n'est pas null
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }
    Cursor read_all_matricule_admin_join()
    {
        String query = "SELECT matricule FROM Vehicule \n " +
                " INNER JOIN Reservation ON Vehicule.id_vehicule = Reservation.id_vehicule AND confirmation = 1 ";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            //si la base de donné n'est pas null
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }
    Cursor read_all_place_admin_join()
    {
        String query = "SELECT name FROM Place \n " +
                "INNER JOIN Reservation ON Place.id_place = Reservation.id_place and confirmation = 1 ";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            //si la base de donné n'est pas null
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }


    Cursor readallplacelibre()
    {
        String query = "SELECT * FROM Place WHERE " + COLUMN_EST_LIBRE_PLACE + " = 0";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            //si la base de donné n'est pas null
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }


    Cursor readalladmin()
    {
        String query = "SELECT " + COLUMN_ID + " , " + COLUMN_NOM + " , " + COLUMN_PRENOM + " , " + COLUMN_NUMERO + " , " + COLUMN_EMAIL + " FROM Login WHERE " + COLUMN_EST_ADMIN + " = 1 ";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            //si la base de donné n'est pas null
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    Cursor readprofil(String row_id)
    {
        String query = "SELECT " + COLUMN_NOM + " , " + COLUMN_PRENOM + " , " + COLUMN_NUMERO + " , " + COLUMN_EMAIL + " FROM Login WHERE " + COLUMN_EST_ADMIN + " = 0 " + " AND " + COLUMN_ID + " = " + row_id;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            //si la base de donné n'est pas null
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    Cursor readallclient()
    {
        String query = "SELECT " + COLUMN_ID + " , " + COLUMN_NOM + " , " + COLUMN_PRENOM + " , " + COLUMN_NUMERO + " , " + COLUMN_EMAIL + " FROM Login WHERE " + COLUMN_EST_ADMIN + " = 0";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            //si la base de donné n'est pas null
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    Cursor readalres_client(String id_usr)
    {
        String query = "SELECT * FROM Reservation WHERE " + COLUMN_ID_USR_RESERVATION + " = " + id_usr + " AND " + COLUMN_CONFIRMATION_RESERVATION + " = 1 ";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            //si la base de donné n'est pas null
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }




    Cursor getId(String email)
    {
        String query = "SELECT " + COLUMN_ID + " FROM Login WHERE " + COLUMN_EMAIL + " = ? ";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            //si la base de donné n'est pas null
            cursor = db.rawQuery(query,new String[]{email});
        }
        return cursor;
    }


    Cursor getId_vehicule(String matricule)
    {
        String query = "SELECT " + COLUMN_ID_VEHICULE + " FROM Vehicule WHERE " + COLUMN_MATRICULE_VEHICULE + " = ? ";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            //si la base de donné n'est pas null
            cursor = db.rawQuery(query,new String[]{matricule});
        }
        return cursor;
    }
    Cursor getId_reservation(String row_id_user,String date)
    {
        String query = "SELECT " + COLUMN_ID_RESERVATION + " FROM Reservation WHERE " + COLUMN_ID_USR_RESERVATION + " = ? AND " + COLUMN_DATE_ENT_RESERVATION + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            //si la base de donné n'est pas null
            cursor = db.rawQuery(query,new String[]{row_id_user,date});
        }
        return cursor;
    }

        public boolean Updatedata(String row_id,String nom,String prenom,String numero,String email,String mdp,boolean admin){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NOM,nom);
        contentValues.put(COLUMN_PRENOM,prenom);
        contentValues.put(COLUMN_NUMERO,numero);
        contentValues.put(COLUMN_EMAIL,email);
        contentValues.put(COLUMN_MOT_DE_PASSE,mdp);
        contentValues.put(COLUMN_EST_ADMIN,admin);

       long resultas = db.update("Login",contentValues," id = ? ",new String[] {row_id});
       if (resultas == -1){
           //failed
           return false;
       }else {
           //success
           return true;
       }

    }


    public boolean Updatreservation(String row_id,String date,String heure_ent,String heure_str,String prix,boolean confirmation,String user,int place){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_HEURE_ENT_RESERVATION,heure_ent);
        contentValues.put(COLUMN_HEURE_SRT_RESERVATION,heure_str);
        contentValues.put(COLUMN_PRIX_RESERVATION,prix);
        contentValues.put(COLUMN_CONFIRMATION_RESERVATION,confirmation);
        contentValues.put(COLUMN_ID_PLACE,place);

        long resultas = db.update("Reservation",contentValues," id_reservation = ? AND " + COLUMN_DATE_ENT_RESERVATION + " = ? AND " + COLUMN_ID_USR_RESERVATION + " = ? " ,new String[] {row_id,date,user});
        if (resultas == -1){
            //failed
            return false;
        }else {
            //success
            return true;
        }

    }

    public boolean Updatrecnf(String row_id,boolean confirmation){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CONFIRMATION_RESERVATION,confirmation);

        long resultas = db.update("Reservation",contentValues," id_reservation = ? " ,new String[] {row_id});
        if (resultas == -1){
            //failed
            return false;
        }else {
            //success
            return true;
        }}


    public boolean Updateimage(String row_id,byte[] img){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_IMAGE,img);
        long resultas = db.update("Login",contentValues," id = ? ",new String[] {row_id});
        if (resultas == -1){
            //failed
            return false;
        }else {
            //success
            return true;
        }

    }


    public boolean Updateadmin(String row_id,String nom,String prenom,String numero,String email,String mdp){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NOM,nom);
        contentValues.put(COLUMN_PRENOM,prenom);
        contentValues.put(COLUMN_NUMERO,numero);
        contentValues.put(COLUMN_EMAIL,email);
        contentValues.put(COLUMN_MOT_DE_PASSE,mdp);

        long resultas = db.update("Login",contentValues," id = ?  AND " + COLUMN_EST_ADMIN + " = 1 ",new String[] {row_id});
        if (resultas == -1){
            //failed
            return false;
        }else {
            //success
            return true;
        }

    }
    public boolean Updatemdp(String email,String mdp){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MOT_DE_PASSE,mdp);

        long resultas = db.update("Login",contentValues, COLUMN_EMAIL + " =  ? ",new String[] {email});
        if (resultas == -1){
            //failed
            return false;
        }else {
            //success
            return true;
        }

    }

    public boolean Updateclient(String row_id,String nom,String prenom,String numero,String email,String mdp){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NOM,nom);
        contentValues.put(COLUMN_PRENOM,prenom);
        contentValues.put(COLUMN_NUMERO,numero);
        contentValues.put(COLUMN_EMAIL,email);
        contentValues.put(COLUMN_MOT_DE_PASSE,mdp);

        long resultas = db.update("Login",contentValues," id = ?  AND " + COLUMN_EST_ADMIN + " = 0 ",new String[] {row_id});
        if (resultas == -1){
            //failed
            return false;
        }else {
            //success
            return true;
        }

    }
    public boolean Updateclient(String row_id,String nom,String prenom,String numero,String email){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NOM,nom);
        contentValues.put(COLUMN_PRENOM,prenom);
        contentValues.put(COLUMN_NUMERO,numero);
        contentValues.put(COLUMN_EMAIL,email);

        long resultas = db.update("Login",contentValues," id = ?  AND " + COLUMN_EST_ADMIN + " = 0 ",new String[] {row_id});
        if (resultas == -1){
            //failed
            return false;
        }else {
            //success
            return true;
        }

    }


    public boolean Updateplace(String row_id,String nom,boolean dispo){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NOM_PLACE,nom);
        contentValues.put(COLUMN_EST_LIBRE_PLACE,dispo);

        long resultas = db.update("Place",contentValues," id_place = ? ",new String[] {row_id});
        if (resultas == -1){
            //failed
            return false;
        }else {
            //success
            return true;
        }

    }
    public boolean Updateplace_client_reservation(String row_id,boolean dispo){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EST_LIBRE_PLACE,dispo);

        long resultas = db.update("Place",contentValues," id_place = ? ",new String[] {row_id});
        if (resultas == -1){
            //failed
            return false;
        }else {
            //success
            return true;
        }

    }
    public void Updateplacecharge(String row_id,boolean dispo){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EST_LIBRE_PLACE,dispo);

        long resultas = db.update("Place",contentValues," id_place = ? ",new String[] {row_id});

    }

    public boolean delate_user(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
         long resultas = db.delete("Login"," id = ? ",new String[]{row_id});
         if (resultas == -1){
             //failed
             return  false;
         }else {
             //success
             return  true;
         }
    }
    public boolean delate_user_google(String row_id,String email){
        SQLiteDatabase db = this.getWritableDatabase();
        long resultas = db.delete("Login"," id = ? AND " + COLUMN_EMAIL + " = ? ",new String[]{row_id,email});
        if (resultas == -1){
            //failed
            return  false;
        }else {
            //success
            return  true;
        }
    }
    public boolean delate_place(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long resultas = db.delete("Place"," id_place = ? ",new String[]{row_id});
        if (resultas == -1){
            //failed
            return  false;
        }else {
            //success
            return  true;
        }
    }

    public boolean delate_reservation(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long resultas = db.delete("Reservation"," id_reservation = ? ",new String[]{row_id});
        if (resultas == -1){
            //failed
            return  false;
        }else {
            //success
            return  true;
        }
    }

}
