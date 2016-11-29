package com.example.bartek.projektjava;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DodajOceneDoSprawdzianuActivity extends AppCompatActivity {


    private Button bWroc;
    private Button bDodaj;
    private ListView lvUzytkownicyBezOceny;
    private EditText etOcena;
    private DatabaseHelper db;
    private Intent intent;
    private List<UzytkownikTabela> listaUzytkownikow;
    private UzytkownikTabela wybranyUzytkownik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_ocene_do_sprawdzianu);

        bWroc= (Button) findViewById(R.id.bWroc);
        bDodaj = (Button) findViewById(R.id.bDodaj);
        lvUzytkownicyBezOceny = (ListView) findViewById(R.id.lvUzytkownicyBezOceny);
        etOcena = (EditText) findViewById(R.id.etOcena);
        db = new DatabaseHelper(getApplicationContext());

        intent = getIntent();
        final Integer id_sprawdzian = Integer.valueOf(intent.getStringExtra("sprawdzian_id"));
        listaUzytkownikow = new ArrayList<>();
        listaUzytkownikow = db.getUserNotInSprawdzian(id_sprawdzian);

        wybranyUzytkownik = new UzytkownikTabela("0","0","0","0");

        final UzytkownicyAdapter adapter =  new UzytkownicyAdapter();
        lvUzytkownicyBezOceny.setAdapter(adapter);
        db.close();

        lvUzytkownicyBezOceny.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                wybranyUzytkownik = listaUzytkownikow.get(position).getObj();
                Toast.makeText(getApplicationContext(), "wybrano użytkownika " + listaUzytkownikow.get(position).getImie() + " " + listaUzytkownikow.get(position).getNazwisko(), Toast.LENGTH_SHORT).show();

            }
        });

        bDodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wybranyUzytkownik.getNazwisko().compareTo("0") > 0) {
                    OcenaTabela o1 = new OcenaTabela(Integer.valueOf(etOcena.getText().toString()), (int) wybranyUzytkownik.getId(), (int) id_sprawdzian);
                    long o1_id = db.createOcena(o1, wybranyUzytkownik.getId());
                    Toast.makeText(getApplicationContext(), "Dodano ocenę", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(getApplicationContext(), EdytujSprawdzianActivity.class);
                    intent1.putExtra("Sprawdzian_id", intent.getStringExtra("sprawdzian_id"));
                    intent1.putExtra("Sprawdzian_nazwa", intent.getStringExtra("sprawdzian_nazwa"));
                    finish();
                    startActivity(intent1);
                } else {
                    Toast.makeText(getApplicationContext(), "Nie wybrano użytkownika", Toast.LENGTH_SHORT).show();
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
    private class UzytkownicyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if(listaUzytkownikow !=null && listaUzytkownikow.size() !=0)
                return listaUzytkownikow.size();
            return 0;

        }

        @Override
        public Object getItem(int position) {
            return listaUzytkownikow.get(position);
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
                LayoutInflater inflater = DodajOceneDoSprawdzianuActivity.this.getLayoutInflater();
                convertView = inflater.inflate(R.layout.oceny_uzytkownika_list,null);
                holder.textView = (TextView) convertView.findViewById(R.id.sprawdzian);


                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.ref = position;


            holder.textView.setText(listaUzytkownikow.get(position).getImie() +" "+ listaUzytkownikow.get(position).getNazwisko());


            return convertView;
        }

        private class ViewHolder {
            TextView textView;
            int ref;
        }
    }
}
