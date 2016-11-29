package com.example.bartek.projektjava;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bartek on 2016-05-12.
 */
public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(Context context){
        super(context,"oceny",null,12);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE uzytkownik (id INTEGER PRIMARY KEY,imie TEXT, nazwisko TEXT,login TEXT,haslo TEXT);");
        db.execSQL("CREATE TABLE ocena (id INTEGER PRIMARY KEY,ocena INTEGER,id_uzytkownik INTEGER,id_sprawdzianu INTEGER);");
        db.execSQL("CREATE TABLE sprawdzian (id INTEGER PRIMARY KEY,nazwaSprawdzianu TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS uzytkownik");
        db.execSQL("DROP TABLE IF EXISTS ocena");
        db.execSQL("DROP TABLE IF EXISTS sprawdzian");
        onCreate(db);

    }

    public long createUzytkownik(UzytkownikTabela uzytkownik){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nazwisko",uzytkownik.getNazwisko());
        values.put("imie", uzytkownik.getImie());
        values.put("login", uzytkownik.getLogin());
        values.put("haslo", uzytkownik.getHaslo());

        long uzytkownik_id = db.insert("uzytkownik",null,values);
        return uzytkownik_id;

    }

    public int updateUzytkownik(UzytkownikTabela uzytkownik) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nazwisko",uzytkownik.getNazwisko().toString());
        values.put("imie", uzytkownik.getImie().toString());
        values.put("login", uzytkownik.getLogin().toString());
        values.put("haslo", uzytkownik.getHaslo().toString());

        Integer wynik = db.update("uzytkownik", values, "id=" + uzytkownik.getId().toString(), null);
        Log.e("wynik edycji",wynik.toString());
        return wynik;

    }

    public List<UzytkownikTabela> getAllUser(){
        List<UzytkownikTabela> uzytkownicy = new ArrayList<>();
        String query = "select * from uzytkownik";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()) {
            do {
                UzytkownikTabela u = new UzytkownikTabela();
                u.setId(c.getInt((c.getColumnIndex("id"))));
                u.setNazwisko(c.getString((c.getColumnIndex("nazwisko"))));
                u.setImie(c.getString((c.getColumnIndex("imie"))));
                u.setLogin(c.getString((c.getColumnIndex("login"))));
                u.setHaslo(c.getString((c.getColumnIndex("haslo"))));

                if(u.getId()!=1)
                uzytkownicy.add(u);
            } while (c.moveToNext());
            c.moveToFirst();
        }
        return uzytkownicy;
    }

    public UzytkownikTabela getUserById(long id_uzytkownik){
        String query = "select * from uzytkownik where id="+String.valueOf(id_uzytkownik)+";";


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()) {
            do {
                UzytkownikTabela u = new UzytkownikTabela();
                u.setId(c.getInt((c.getColumnIndex("id"))));
                u.setNazwisko(c.getString((c.getColumnIndex("nazwisko"))));
                u.setImie(c.getString((c.getColumnIndex("imie"))));
                u.setLogin(c.getString((c.getColumnIndex("login"))));
                u.setHaslo(c.getString((c.getColumnIndex("haslo"))));

                if(u.getId()!=1)
                return u;
            } while (c.moveToNext());

        }
        return new UzytkownikTabela("-1","-1","-1","-1");
    }

    public UzytkownikTabela getUserByLogin(String login){
        String query = "select * from uzytkownik ;";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()) {
            do {

                UzytkownikTabela u = new UzytkownikTabela();
                u.setId(c.getInt((c.getColumnIndex("id"))));
                u.setNazwisko(c.getString((c.getColumnIndex("nazwisko"))));
                u.setImie(c.getString((c.getColumnIndex("imie"))));
                u.setLogin(c.getString((c.getColumnIndex("login"))));
                u.setHaslo(c.getString((c.getColumnIndex("haslo"))));

                if(u.getLogin().compareTo(String.valueOf(login))==0)
                return u;
            } while (c.moveToNext());

        }
        return new UzytkownikTabela("","","","");
    }

    public List<UzytkownikTabela> getUserNotInSprawdzian(long id_sprawdzian){
        String query = "select * from uzytkownik where id not in(select id_uzytkownik from ocena where id_sprawdzianu="+String.valueOf(id_sprawdzian)+");";

        List<UzytkownikTabela> uzytkownicy = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()) {
            do {
                UzytkownikTabela u = new UzytkownikTabela();
                u.setId(c.getInt((c.getColumnIndex("id"))));
                u.setNazwisko(c.getString((c.getColumnIndex("nazwisko"))));
                u.setImie(c.getString((c.getColumnIndex("imie"))));
                u.setLogin(c.getString((c.getColumnIndex("login"))));
                u.setHaslo(c.getString((c.getColumnIndex("haslo"))));

                if(u.getId()!=1)
                uzytkownicy.add(u);
            } while (c.moveToNext());

        }
        return uzytkownicy;
    }

    public int deleteUser(UzytkownikTabela uzytkownikTabela) {
        List<OcenaTabela> oceny = this.getOcenaByUzytkownik(uzytkownikTabela.getId());
        for(int i=0; i<oceny.size();i++){
            this.deleteOcena(oceny.get(i).getObj());
        }
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete("uzytkownik", "id=?", new String[]{String.valueOf((uzytkownikTabela.getId()))});
    }

    public long createSprawdzian(SprawdzianTabela sprawdzian){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nazwaSprawdzianu",sprawdzian.getNazwaSprawdzianu());

        long sprawdzian_id = db.insert("sprawdzian", null, values);
        return sprawdzian_id;
    }

    public int deleteSprawdzian(SprawdzianTabela sprawdzian) {
        List<OcenaTabela> oceny = this.getOcenyUzytkownikowBySprawdzian(sprawdzian.getId());
        for(int i=0; i<oceny.size();i++){
            this.deleteOcena(oceny.get(i).getObj());
        }
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("sprawdzian", "id=?", new String[]{String.valueOf((sprawdzian.getId()))});
    }

    public List<SprawdzianTabela> getAllSprawdzian(){
        List<SprawdzianTabela> sprawdzian = new ArrayList<>();
        String query = "select * from sprawdzian";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()) {
            do {
                SprawdzianTabela spr = new SprawdzianTabela();
                spr.setId(c.getInt(c.getColumnIndex("id")));
                spr.setNazwaSprawdzianu(c.getString(c.getColumnIndex("nazwaSprawdzianu")));

                sprawdzian.add(spr);
            } while (c.moveToNext());
            c.moveToFirst();
        }
        return sprawdzian;
    }

    public SprawdzianTabela getSprawdzianById(long id){
        String query = "select * from sprawdzian where id="+String.valueOf(id).toString()+";";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()) {
            do {
                SprawdzianTabela spr = new SprawdzianTabela();
                spr.setId(c.getInt(c.getColumnIndex("id")));
                spr.setNazwaSprawdzianu(c.getString(c.getColumnIndex("nazwaSprawdzianu")));

               return spr;
            } while (c.moveToNext());
        }
        return new SprawdzianTabela("0");
    }

    public List<SprawdzianTabela> getAllSprawdzianByUzytkownik( int id_uzytkownik){
        List<SprawdzianTabela> sprawdzian = new ArrayList<>();
        String query = "select * from sprawdzian where id in (select id_sprawdzianu from ocena where id_uzytkownik="+String.valueOf(id_uzytkownik)+");";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        if(c.moveToFirst()) {
            do {
                SprawdzianTabela spr = new SprawdzianTabela();
                spr.setId(c.getInt(c.getColumnIndex("id")));
                spr.setNazwaSprawdzianu(c.getString(c.getColumnIndex("nazwaSprawdzianu")));

                sprawdzian.add(spr);
            } while (c.moveToNext());
            c.moveToFirst();
        }
        return sprawdzian;
    }

    public long createOcena(OcenaTabela ocena, long id_uzytkownik){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("ocena",ocena.getOcena());
        values.put("id_sprawdzianu",ocena.getId_sprawdzianu());
        values.put("id_uzytkownik", id_uzytkownik);

        long ocena_id = db.insert("ocena", null, values);
        return ocena_id;
    }

    public int updateOcena(OcenaTabela ocena) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("ocena",ocena.getOcena());
        values.put("id_sprawdzianu",ocena.getId_sprawdzianu());
        values.put("id_uzytkownik", ocena.getId_uzytkownik());

        Integer wynik = db.update("ocena",values,"id="+ocena.getId().toString(),null );
        Log.e("wynik edycji",wynik.toString());
        return wynik;

    }

    public void deleteOcena(OcenaTabela ocena) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("ocena","id="+ocena.getId().toString(),null );

    }

    public List<OcenaTabela> getOcenaByUzytkownik(long id_uzytkownik) {
        List<OcenaTabela> oceny = new ArrayList<>();
        String query = "Select * from ocena where id_uzytkownik="+String.valueOf(id_uzytkownik)+";";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        if(c.moveToFirst()) {
            do {
                OcenaTabela o = new OcenaTabela();
                o.setId(c.getInt(c.getColumnIndex("id")));
                o.setOcena(c.getInt(c.getColumnIndex("ocena")));
                o.setId_uzytkownik((int) id_uzytkownik);
                o.setId_sprawdzianu(c.getInt(c.getColumnIndex("id_sprawdzianu")));

                oceny.add(o);
            } while (c.moveToNext());
            c.moveToFirst();
        }
        return  oceny;
    }

    public OcenaTabela getOcenaByUzytkownikAndSprawdzian(long id_uzytkownik,int id_sprawdzianu) {
        String query = "Select * from ocena where id_uzytkownik="+String.valueOf(id_uzytkownik)+" and id_sprawdzianu="+String.valueOf(id_sprawdzianu)+";";
        OcenaTabela o;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        if(c.moveToFirst()) {
            do {
                o = new OcenaTabela();
                o.setId(c.getInt(c.getColumnIndex("id")));
                o.setOcena(c.getInt(c.getColumnIndex("ocena")));
                o.setId_uzytkownik((int) id_uzytkownik);
                o.setId_sprawdzianu(c.getInt(c.getColumnIndex("id_sprawdzianu")));
                return  o;

            } while (c.moveToNext());

        }
        return  new OcenaTabela();
    }

    public List<OcenaTabela> getOcenyUzytkownikowBySprawdzian(long id_sprawdzian) {
        List<OcenaTabela> oceny = new ArrayList<>();
        String query = "Select * from ocena where id_sprawdzianu="+String.valueOf(id_sprawdzian)+";";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        if(c.moveToFirst()) {
            do {
                OcenaTabela o = new OcenaTabela();
                o.setId(c.getInt(c.getColumnIndex("id")));
                o.setOcena(c.getInt(c.getColumnIndex("ocena")));
                o.setId_uzytkownik(c.getInt(c.getColumnIndex("id_uzytkownik")));
                o.setId_sprawdzianu(c.getInt(c.getColumnIndex("id_sprawdzianu")));

                oceny.add(o);
            } while (c.moveToNext());
            c.moveToFirst();
        }
        return  oceny;
    }


}
