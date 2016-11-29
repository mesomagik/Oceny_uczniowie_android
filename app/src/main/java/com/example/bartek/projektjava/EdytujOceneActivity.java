package com.example.bartek.projektjava;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EdytujOceneActivity extends AppCompatActivity {

    TextView tvSprawdzianNazwisko;
    Button bWroc;
    Button bAkceptuj;
    EditText etOcena;
    DatabaseHelper db;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edytuj_ocene);

        db = new DatabaseHelper(getApplicationContext());

        tvSprawdzianNazwisko = (TextView) findViewById(R.id.tvSprawdzianNazwisko);
        bAkceptuj = (Button) findViewById(R.id.bAkceptuj);
        bWroc = (Button) findViewById(R.id.bWroc);
        etOcena = (EditText) findViewById(R.id.etOcena);


         intent = getIntent();
        UzytkownikTabela uzytkownik = (UzytkownikTabela)intent.getSerializableExtra("uzytkownik");
        final OcenaTabela ocena = (OcenaTabela)intent.getSerializableExtra("ocena");
        tvSprawdzianNazwisko.setText(intent.getStringExtra("sprawdzian_nazwa")+" - "+uzytkownik.getImie().toString()+" "+uzytkownik.getNazwisko().toString());

        bAkceptuj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etOcena.getText().toString().isEmpty()) {
                    Integer o = Integer.valueOf(etOcena.getText().toString());
                    if (o.intValue() >= 2 && o.intValue() <= 5) {

                        ocena.setOcena(o);
                        db.updateOcena(ocena);
                        Intent intent1 = new Intent(getApplicationContext(), EdytujSprawdzianActivity.class);
                        Toast.makeText(getApplicationContext(), "Edytowano ocene", Toast.LENGTH_SHORT).show();
                        intent1.putExtra("Sprawdzian_id", intent.getStringExtra("sprawdzian_id"));
                        intent1.putExtra("Sprawdzian_nazwa", intent.getStringExtra("sprawdzian_nazwa"));
                        finish();
                        startActivity(intent1);
                    } else {
                        Intent intent1 = new Intent(getApplicationContext(), EdytujSprawdzianActivity.class);
                        intent1.putExtra("Sprawdzian_id", intent.getStringExtra("sprawdzian_id"));
                        intent1.putExtra("Sprawdzian_nazwa", intent.getStringExtra("sprawdzian_nazwa"));
                        Toast.makeText(getApplicationContext(), "Podano niepoprawna ocene", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(intent1);
                    }
                }
            }
        });

        bWroc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), EdytujSprawdzianActivity.class);
                intent1.putExtra("Sprawdzian_id", intent.getStringExtra("sprawdzian_id"));
                intent1.putExtra("Sprawdzian_nazwa", intent.getStringExtra("sprawdzian_nazwa"));
                finish();
                startActivity(intent1);
            }
        });

    }
}
