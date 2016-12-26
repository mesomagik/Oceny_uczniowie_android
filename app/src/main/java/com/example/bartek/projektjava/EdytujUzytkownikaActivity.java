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

public class EdytujUzytkownikaActivity extends AppCompatActivity {

    private Button bEdytuj;
    private Button bWroc;
    private EditText etImie;
    private EditText etNazwisko;
    private EditText etLogin;
    private  EditText etHaslo;
    private DatabaseHelper db;
    UzytkownikTabela uzytkownik;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Intent intent = new Intent(getApplicationContext(), ListaUzytkownikowActivity.class);
            finish();
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edytuj_uzytkownika);

        db = new DatabaseHelper(getApplicationContext());

        Intent intent =  getIntent();
        uzytkownik = new UzytkownikTabela();
        uzytkownik = (UzytkownikTabela) intent.getSerializableExtra("uzytkownik");

        bEdytuj = (Button) findViewById(R.id.bEdytuj);
        bWroc = (Button) findViewById(R.id.bWroc);
        etHaslo = (EditText) findViewById(R.id.etHaslo);
        etHaslo.setText(uzytkownik.getHaslo());
        etImie = (EditText) findViewById(R.id.etImie);
        etImie.setText(uzytkownik.getImie());
        etNazwisko = (EditText) findViewById(R.id.etNazwisko);
        etNazwisko.setText(uzytkownik.getNazwisko());
        etLogin = (EditText) findViewById(R.id.etLogin);
        etLogin.setText(uzytkownik.getLogin());

        bWroc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListaUzytkownikowActivity.class);
                finish();
                startActivity(intent);
            }
        });

        bEdytuj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etHaslo.getText().toString().compareTo("") == 0 ||
                        etNazwisko.getText().toString().compareTo("") == 0 ||
                        etLogin.getText().toString().compareTo("") == 0 ||
                        etImie.getText().toString().compareTo("") == 0) {
                    Toast.makeText(getApplicationContext(), "Niepoprawnie wypełniony formularz", Toast.LENGTH_SHORT).show();
                } else {

                    uzytkownik.setNazwisko(etNazwisko.getText().toString());
                    uzytkownik.setImie(etImie.getText().toString());
                    uzytkownik.setLogin(etLogin.getText().toString());
                    uzytkownik.setHaslo(etHaslo.getText().toString());
                    uzytkownik.setId(uzytkownik.getId());
                    db.updateUzytkownik(uzytkownik);
                    Toast.makeText(getApplicationContext(), "Edytowano użytkownika", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), ListaUzytkownikowActivity.class);
                    finish();
                    startActivity(intent);
                }
            }
        });
    }
}
