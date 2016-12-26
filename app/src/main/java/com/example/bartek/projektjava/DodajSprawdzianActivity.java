package com.example.bartek.projektjava;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DodajSprawdzianActivity extends AppCompatActivity {

    DatabaseHelper db;
    List<UzytkownikTabela> uzytkownicy;
    Button bDodaj;
    Button bWroc;
    EditText etNazwaSprawdzianu;
    Integer flaga = 1;

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
        setContentView(R.layout.activity_dodaj_sprawdzian);

        db = new DatabaseHelper(getApplicationContext());

        uzytkownicy = new ArrayList<>();
        uzytkownicy = db.getAllUser();

        final DodajOcenyAdapter adapter = new DodajOcenyAdapter();
        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        bDodaj = (Button) findViewById(R.id.bDodaj);
        bWroc = (Button) findViewById(R.id.bWroc);
        etNazwaSprawdzianu = (EditText) findViewById(R.id.etNazwaSprawdzianu);

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

                if (!etNazwaSprawdzianu.getText().toString().isEmpty()) {


                    for (int i = 0; i < uzytkownicy.size(); i++) {
                        Integer ocena;
                        View view = listView.getChildAt(i);
                        EditText editText = (EditText) view.findViewById(R.id.etLogin);
                        if (!editText.getText().toString().isEmpty()) {
                            ocena = Integer.parseInt(editText.getText().toString());
                            if (ocena > 1 && ocena < 6  || editText.toString().isEmpty()) {
                                flaga = 1;
                            } else {
                                Toast.makeText(getApplicationContext(), "niepoprawna ocena w polu uzytkownika " + uzytkownicy.get(i).getNazwisko() + " " + uzytkownicy.get(i).getImie(), Toast.LENGTH_SHORT).show();
                                flaga = 0;
                                break;
                            }
                        }
                    }
                    if (flaga == 1) {
                        SprawdzianTabela sprawdzian = new SprawdzianTabela(etNazwaSprawdzianu.getText().toString());
                        long id_s = db.createSprawdzian(sprawdzian);

                        for (int i = 0; i < uzytkownicy.size(); i++) {

                            //wyciągnięcie oceny z listView
                            Integer ocena;
                            View view = listView.getChildAt(i);
                            EditText editText = (EditText) view.findViewById(R.id.etLogin);
                            if (!editText.getText().toString().isEmpty()) {
                                ocena = Integer.parseInt(editText.getText().toString());
                                if (ocena > 1 && ocena < 6) {
                                    OcenaTabela ocenaTabela = new OcenaTabela(
                                            ocena,
                                            uzytkownicy.get(i).getId(),
                                            (int) id_s
                                    );
                                    db.createOcena(ocenaTabela, uzytkownicy.get(i).getId());
                                }
                            }
                        }

                        db.close();
                        Toast.makeText(getApplicationContext(), "Dodano sprawdzian!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), AdminPanelActivity.class);
                        finish();
                        startActivity(intent);
                    }
                } else {
                        Toast.makeText(getApplicationContext(), "Dodaj nazwę sprawdzianu!", Toast.LENGTH_SHORT).show();
                    }
                }

        });


    }
    private class DodajOcenyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if(uzytkownicy !=null && uzytkownicy.size() !=0)
                return uzytkownicy.size();
            return 0;

        }

        @Override
        public Object getItem(int position) {
            return uzytkownicy.get(position);
        }


        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if(convertView == null){
                holder = new ViewHolder();
                LayoutInflater inflater = DodajSprawdzianActivity.this.getLayoutInflater();
                convertView = inflater.inflate(R.layout.dodaj_sprawdzian_list,null);
                holder.textView = (TextView) convertView.findViewById(R.id.textView);
                holder.editText = (EditText) convertView.findViewById(R.id.etLogin);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.ref = position;
            holder.textView.setText(uzytkownicy.get(position).getImie() + " " + uzytkownicy.get(position).getNazwisko());

            return convertView;
        }

        private class ViewHolder {
            TextView textView;
            EditText editText;
            int ref;
        }
    }
}
