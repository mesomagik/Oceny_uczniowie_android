package com.example.bartek.projektjava;

import java.io.Serializable;

/**
 * Created by Bartek on 2016-05-13.
 */
public class SprawdzianTabela implements Serializable {
    private Integer id;
    private String nazwaSprawdzianu;

    public SprawdzianTabela(){

    }

    public SprawdzianTabela(
            String nazwaSprawdzianu){
        this.nazwaSprawdzianu = nazwaSprawdzianu;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNazwaSprawdzianu() {
        return nazwaSprawdzianu;
    }

    public void setNazwaSprawdzianu(String nazwaSprawdzianu) {
        this.nazwaSprawdzianu = nazwaSprawdzianu;
    }

    public SprawdzianTabela getObj() {
        return this;
    }
}
