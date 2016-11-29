package com.example.bartek.projektjava;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;
    private Button bDodajOceny;
    private Button bOceny;
    private Button bSprawdziany;
    private Button bDodajUzytkownika;
    private Button bLogowanie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        db = new DatabaseHelper(getApplicationContext());

        UzytkownikTabela u1 = new UzytkownikTabela("admin","admin","admin","admin");
        long u1_id = db.createUzytkownik(u1);
        Log.e("id",String.valueOf(u1_id));

        List<UzytkownikTabela> users = db.getAllUser();
        for(UzytkownikTabela u: users){
            Log.d("Uzytkownik: ",u.getNazwisko());
        }
        List<OcenaTabela> oceny = db.getOcenaByUzytkownik(1);
        for(OcenaTabela o : oceny){
            Log.d("Ocena uzytkownika 1: ",o.getOcena().toString());
        }
        List<SprawdzianTabela> spr = db.getAllSprawdzian();
        for(SprawdzianTabela s : spr){
            Log.d("Sprawdzian: ",s.getNazwaSprawdzianu());
        }
        List<SprawdzianTabela> spr1 = db.getAllSprawdzian();
        for(SprawdzianTabela s : spr1){
            Log.d("Sprawdzian: ",s.getNazwaSprawdzianu());
        }

        OcenaTabela ocenaTabela = db.getOcenaByUzytkownikAndSprawdzian(1,1);
        Log.e("u1 s1",ocenaTabela.getOcena().toString());

        db.close();
*/
        bDodajOceny = (Button) findViewById(R.id.bDodajOceny);
        bOceny = (Button) findViewById(R.id.bOceny);
        bSprawdziany = (Button) findViewById(R.id.bSprawdziany);
        bDodajUzytkownika = (Button) findViewById(R.id.bEdytuj);
        bLogowanie = (Button) findViewById(R.id.bLogowanie);




        bDodajOceny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DodajSprawdzianActivity.class);
                startActivity(intent);

            }
        });

        bOceny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),OcenyUzytkownikaActivity.class);
                startActivity(intent);

            }
        });

        bSprawdziany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ListaSprawdzianowActivity.class);
                startActivity(intent);

            }
        });

        bDodajUzytkownika.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),DodajUzytkownikaActivity.class);
                startActivity(intent);

            }
        });
        bLogowanie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LogowanieActivity.class);
                startActivity(intent);

            }
        });

    }

}
