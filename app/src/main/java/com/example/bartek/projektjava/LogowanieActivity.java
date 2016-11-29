package com.example.bartek.projektjava;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LogowanieActivity extends AppCompatActivity {

    private Button bLogin;
    private EditText etLogin;
    private EditText etHaslo;
    private UzytkownikTabela uzytkownik;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logowanie);

        db = new DatabaseHelper(getApplicationContext());

        /*
        UzytkownikTabela check_admin = db.getUserById(1);
        if(check_admin.getLogin().toString().compareTo("-1")==0 ){
            Log.e("login",check_admin.getLogin().toString() );
            db.createUzytkownik(new UzytkownikTabela("admin","admin","admin","admin"));
        }
        */

        uzytkownik =  new UzytkownikTabela("0","0","0","0");

        etLogin = (EditText) findViewById(R.id.etLogin);
        etHaslo = (EditText) findViewById(R.id.etHaslo);
        bLogin = (Button) findViewById(R.id.bLogin);

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!etLogin.getText().toString().isEmpty() || !etHaslo.getText().toString().isEmpty()) {
                   if(db.getUserByLogin(etLogin.getText().toString()) != null)
                       uzytkownik=db.getUserByLogin(etLogin.getText().toString());
                    if(!etHaslo.getText().toString().isEmpty()) {
                        if (uzytkownik.getHaslo().toString().compareTo(etHaslo.getText().toString()) == 0) {
                            if (uzytkownik.getId().compareTo(new Integer(1)) == 0) {
                                Intent intent = new Intent(getApplicationContext(), AdminPanelActivity.class);
                                finish();
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(getApplicationContext(), OcenyUzytkownikaActivity.class);
                                intent.putExtra("id_uzytkownik", uzytkownik.getId().toString());
                                finish();
                                startActivity(intent);

                            }
                        }

                    else {
                            Toast.makeText(getApplicationContext(), "niepoprawne dane", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "niepoprawne dane", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"wpisz dane",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
