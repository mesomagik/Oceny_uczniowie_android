package com.example.bartek.projektjava;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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


import java.util.List;


public class ListaSprawdzianowActivity extends AppCompatActivity {

    DatabaseHelper db;
    ListView lvSprawdziany;
    Button bWroc;
    List<SprawdzianTabela> listaSprawdzianow;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_sprawdzianow);

        lvSprawdziany = (ListView) findViewById(R.id.lvSprawdziany);
        bWroc = (Button) findViewById(R.id.bWroc);

        db = new DatabaseHelper(getApplicationContext());

        listaSprawdzianow = db.getAllSprawdzian();

        final SprawdzianyAdapter adapter = new SprawdzianyAdapter();
        lvSprawdziany.setAdapter(adapter);
        db.close();

        lvSprawdziany.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                db.deleteSprawdzian(listaSprawdzianow.get(position).getObj());
                Intent intent = new Intent(getApplicationContext(), ListaSprawdzianowActivity.class);
                startActivity(intent);
                return true;
            }
        });

        lvSprawdziany.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), EdytujSprawdzianActivity.class);
                intent.putExtra("Sprawdzian_id", listaSprawdzianow.get(position).getId().toString());
                intent.putExtra("Sprawdzian_nazwa", listaSprawdzianow.get(position).getNazwaSprawdzianu());
                finish();
                startActivity(intent);
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
            if (listaSprawdzianow != null && listaSprawdzianow.size() != 0)
                return listaSprawdzianow.size();
            return 0;

        }

        @Override
        public Object getItem(int position) {
            return listaSprawdzianow.get(position);
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
                LayoutInflater inflater = ListaSprawdzianowActivity.this.getLayoutInflater();
                convertView = inflater.inflate(R.layout.oceny_uzytkownika_list, null);
                holder.textView = (TextView) convertView.findViewById(R.id.sprawdzian);


                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.ref = position;


            holder.textView.setText(listaSprawdzianow.get(position).getNazwaSprawdzianu());


            return convertView;
        }

        private class ViewHolder {
            TextView textView;
            int ref;
        }
    }
}
