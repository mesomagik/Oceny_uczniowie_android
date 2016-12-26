package com.example.bartek.projektjava;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EdytujSprawdzianActivity extends AppCompatActivity {

    TextView tvNazwaSprawdzianu;
    Button bDodajOcene;
    Button bWroc;
    ListView lvEdytujSprawdzian;
    DatabaseHelper db;
    List<OcenaTabela> oceny;
    List<UzytkownikTabela> listaUzytkownik;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Intent intent = new Intent(getApplicationContext(), ListaSprawdzianowActivity.class);
            finish();
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edytuj_sprawdzian);

        db = new DatabaseHelper(getApplicationContext());

        Intent intent = getIntent();
        final Integer sprawdzian_id =Integer.valueOf(intent.getStringExtra("Sprawdzian_id"));
        final String sprawdzian_nazwa = intent.getStringExtra("Sprawdzian_nazwa");

        tvNazwaSprawdzianu = (TextView) findViewById(R.id.tvNazwaSprawdzianu);
        bDodajOcene = (Button) findViewById(R.id.bDodajOcene);
        bWroc = (Button) findViewById(R.id.bWroc);
        lvEdytujSprawdzian = (ListView) findViewById(R.id.lvEdytujSprawdzian);

        tvNazwaSprawdzianu.setText(sprawdzian_nazwa);

        oceny = db.getOcenyUzytkownikowBySprawdzian(sprawdzian_id);
        for(int i=0;i<oceny.size();i++)
            Log.e("ocena ",oceny.get(i).getOcena().toString());

        listaUzytkownik = new ArrayList<>();
        for(int i=0;i<oceny.size();i++){
            listaUzytkownik.add(db.getUserById(oceny.get(i).getId_uzytkownik()));
        }

        final EdytujSprawdzianAdapter adapter = new EdytujSprawdzianAdapter();
        lvEdytujSprawdzian.setAdapter(adapter);

        db.close();

        lvEdytujSprawdzian.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), EdytujOceneActivity.class);
                intent.putExtra("sprawdzian_id", sprawdzian_id.toString());
                intent.putExtra("uzytkownik", listaUzytkownik.get(position).getObj());
                intent.putExtra("ocena", oceny.get(position).getObj());
                intent.putExtra("sprawdzian_nazwa", sprawdzian_nazwa.toString());
                finish();
                startActivity(intent);

            }
        });

        lvEdytujSprawdzian.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                db.deleteOcena(oceny.get(position).getObj());
                Intent intent = new Intent(getApplicationContext(), ListaSprawdzianowActivity.class);
                startActivity(intent);
                return true;
            }
        });

        bWroc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListaSprawdzianowActivity.class);
                finish();
                startActivity(intent);
            }
        });

        bDodajOcene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),DodajOceneDoSprawdzianuActivity.class);
                intent.putExtra("sprawdzian_id", sprawdzian_id.toString());
                intent.putExtra("sprawdzian_nazwa", sprawdzian_nazwa.toString());
                finish();
                startActivity(intent);
            }
        });
    }
    private class EdytujSprawdzianAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if(oceny !=null && oceny.size() !=0)
                return oceny.size();
            return 0;

        }

        @Override
        public Object getItem(int position) {
            return oceny.get(position);
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
                LayoutInflater inflater = EdytujSprawdzianActivity.this.getLayoutInflater();
                convertView = inflater.inflate(R.layout.oceny_uzytkownika_list,null);
                holder.tvImie = (TextView) convertView.findViewById(R.id.tvImie);
                holder.tvOcena = (TextView) convertView.findViewById(R.id.tvOcena);


                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.ref = position;
            holder.tvImie.setText(listaUzytkownik.get(position).getImie()+" "+listaUzytkownik.get(position).getNazwisko() );
            holder.tvOcena.setText(String.valueOf(oceny.get(position).getOcena()));

            return convertView;
        }

        private class ViewHolder {
            TextView tvImie;
            TextView tvOcena;
            int ref;
        }
    }
}
