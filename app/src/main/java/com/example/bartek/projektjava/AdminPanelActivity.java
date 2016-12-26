package com.example.bartek.projektjava;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class AdminPanelActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private Button bDodajSprawdzian;
    private Button bDodajUzytkownika;
    private Button bSprawdziany;
    private Button bWyloguj;
    private Button bListaUzytkownikow;


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            onPause();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        bDodajSprawdzian = (Button) findViewById(R.id.bDodajSprawdzian);
        bDodajUzytkownika = (Button) findViewById(R.id.bEdytuj);
        bSprawdziany = (Button) findViewById(R.id.bListaSprawdzianow);
        bWyloguj = (Button) findViewById(R.id.bWyloguj);
        bListaUzytkownikow = (Button) findViewById(R.id.bListaUzytkownikow);

        bDodajSprawdzian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DodajSprawdzianActivity.class);
                finish();
                startActivity(intent);

            }
        });

        bSprawdziany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ListaSprawdzianowActivity.class);
                finish();
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
        bWyloguj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LogowanieActivity.class);
                finish();
                startActivity(intent);
            }
        });

        bListaUzytkownikow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ListaUzytkownikowActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }
}
