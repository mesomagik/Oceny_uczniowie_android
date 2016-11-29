package com.example.bartek.projektjava;

import java.io.Serializable;

/**
 * Created by Bartek on 2016-05-12.
 */
public class UzytkownikTabela implements Serializable {

    Integer id;
    String nazwisko;
    String imie;
    String login;
    String haslo;

    public UzytkownikTabela(){

    }
    public UzytkownikTabela(
            String nazwisko,
            String imie,
            String login,
            String haslo){
        this.nazwisko = nazwisko;
        this.imie = imie;
        this.login = login;
        this.haslo = haslo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public UzytkownikTabela getObj() {
        return this;
    }
}
