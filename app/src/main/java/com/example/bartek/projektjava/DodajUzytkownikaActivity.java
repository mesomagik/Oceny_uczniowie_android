package com.example.bartek.projektjava;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DodajUzytkownikaActivity extends AppCompatActivity {

    private Button bDodaj;
    private Button bWroc;
    private EditText etImie;
    private EditText etNazwisko;
    private EditText etLogin;
    private  EditText etHaslo;
    private DatabaseHelper db;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Intent intent = new Intent(getApplicationContext(),AdminPanelActivity.class);
            finish();
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_uzytkownika);

        db = new DatabaseHelper(getApplicationContext());

        bDodaj = (Button) findViewById(R.id.bEdytuj);
        bWroc = (Button) findViewById(R.id.bWroc);
        etHaslo = (EditText) findViewById(R.id.etHaslo);
        etImie = (EditText) findViewById(R.id.etImie);
        etNazwisko = (EditText) findViewById(R.id.etNazwisko);
        etLogin = (EditText) findViewById(R.id.etLogin);

        bWroc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AdminPanelActivity.class);
                finish();
                startActivity(intent);
            }
        });

        bDodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( etHaslo.getText().toString().compareTo("")==0 ||
                        etNazwisko.getText().toString().compareTo("")==0 ||
                        etLogin.getText().toString().compareTo("")==0  ||
                        etImie.getText().toString().compareTo("")==0 ){
                    Toast.makeText(getApplicationContext(),"Niepoprawnie wypełniony formularz",Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.e("uzytkownik do dodania",etNazwisko.getText().toString()+" "+etImie.getText().toString()+" "+ etLogin.getText().toString()+" "+ etHaslo.getText().toString());
                    UzytkownikTabela u1 = new UzytkownikTabela(
                            etNazwisko.getText().toString(),
                            etImie.getText().toString(),
                            etLogin.getText().toString(),
                            etHaslo.getText().toString());
                    long id_u = db.createUzytkownik(u1);
                    Toast.makeText(getApplicationContext(),"Dodano użytkownika",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),AdminPanelActivity.class);
                    finish();
                    startActivity(intent);
                }
            }
        });
    }
}
