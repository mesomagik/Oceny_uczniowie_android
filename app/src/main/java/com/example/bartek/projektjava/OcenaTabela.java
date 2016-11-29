package com.example.bartek.projektjava;

import java.io.Serializable;

/**
 * Created by Bartek on 2016-05-12.
 */
public class OcenaTabela implements Serializable {
    Integer id;
    Integer ocena;
    Integer id_uzytkownik;
    Integer id_sprawdzianu;

    public Integer getId_sprawdzianu() {
        return id_sprawdzianu;
    }

    public void setId_sprawdzianu(Integer id_sprawdzianu) {
        this.id_sprawdzianu = id_sprawdzianu;
    }

    public OcenaTabela(){

    }
    public OcenaTabela(
            Integer ocena,
            Integer id_uzytkownik,
            Integer id_sprawdzianu){
        this.id_uzytkownik = id_uzytkownik;
        this.ocena = ocena;
        this.id_sprawdzianu = id_sprawdzianu;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOcena() {
        return ocena;
    }

    public void setOcena(Integer ocena) {
        this.ocena = ocena;
    }
    public Integer getId_uzytkownik() {
        return id_uzytkownik;
    }

    public void setId_uzytkownik(Integer id_uzytkownik) {
        this.id_uzytkownik = id_uzytkownik;
    }

    public OcenaTabela getObj(){
        return this;
    }

}

