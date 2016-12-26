package com.example.bartek.projektjava;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
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


import java.util.List;


public class ListaUzytkownikowActivity extends AppCompatActivity {

    DatabaseHelper db;
    ListView lvUzytkownicy;
    Button bWroc;
    Button bEdytuj;
    List<UzytkownikTabela> listaUzytkownikow;
    UzytkownikTabela wybranyUzytkownik;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Intent intent = new Intent(getApplicationContext(), AdminPanelActivity.class);
            finish();
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_uzytkownikow);

        lvUzytkownicy = (ListView) findViewById(R.id.lvUzytkownicy);
        bWroc = (Button) findViewById(R.id.bWroc);
        bEdytuj = (Button) findViewById(R.id.bEdytuj);

        db = new DatabaseHelper(getApplicationContext());

        listaUzytkownikow = db.getAllUser();

        wybranyUzytkownik = new UzytkownikTabela("0", "0", "0", "0");
        final SprawdzianyAdapter adapter = new SprawdzianyAdapter();
        lvUzytkownicy.setAdapter(adapter);
        db.close();

        lvUzytkownicy.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                db.deleteUser(listaUzytkownikow.get(position).getObj());
                Intent intent1 = new Intent(getApplicationContext(), ListaUzytkownikowActivity.class);
                finish();
                startActivity(intent1);
                return true;
            }
        });

        lvUzytkownicy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                wybranyUzytkownik = listaUzytkownikow.get(position).getObj();
                Toast.makeText(getApplicationContext(), "wybrano " + listaUzytkownikow.get(position).getNazwisko() + " " + listaUzytkownikow.get(position).getImie(), Toast.LENGTH_SHORT).show();
            }
        });

        bEdytuj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wybranyUzytkownik.getNazwisko().compareTo("0") > 0) {
                    Intent intent1 = new Intent(getApplicationContext(), EdytujUzytkownikaActivity.class);
                    intent1.putExtra("uzytkownik", wybranyUzytkownik);
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
                Intent intent = new Intent(getApplicationContext(), AdminPanelActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }


    private class SprawdzianyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (listaUzytkownikow != null && listaUzytkownikow.size() != 0)
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
            if (convertView == null) {
                holder = new ViewHolder();
                LayoutInflater inflater = ListaUzytkownikowActivity.this.getLayoutInflater();
                convertView = inflater.inflate(R.layout.lista_sprawdzianow_list, null);
                holder.textView = (TextView) convertView.findViewById(R.id.tvSprawdzian);


                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.ref = position;


            holder.textView.setText(listaUzytkownikow.get(position).getNazwisko() + " " + listaUzytkownikow.get(position).getImie());


            return convertView;
        }

        private class ViewHolder {
            TextView textView;
            int ref;
        }
    }
}
