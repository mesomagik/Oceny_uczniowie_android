package com.example.bartek.projektjava;

import android.content.Intent;
import android.media.tv.TvContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class OcenyUzytkownikaActivity extends AppCompatActivity {

    private ListView listView;
    DatabaseHelper db;
    private List<SprawdzianTabela> listaSprawdzianów;
    private OcenaTabela ocena;
    private List<String> ocenySprawdzianow;
    private Intent intent;
    private Integer id_uzytkownik;
    private Button bWyloguj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oceny_uzytkownika);

        listView = (ListView) findViewById(R.id.listView);
        bWyloguj = (Button) findViewById(R.id.bWyloguj);

        intent = getIntent();
        id_uzytkownik = Integer.valueOf(intent.getStringExtra("id_uzytkownik"));

        db = new DatabaseHelper(getApplicationContext());
        ocenySprawdzianow = new ArrayList<>();
        listaSprawdzianów = db.getAllSprawdzianByUzytkownik(id_uzytkownik);


        if(!listaSprawdzianów.isEmpty()) {
            for (int i = 0; i < listaSprawdzianów.size(); i++) {
                Log.e("spr1", listaSprawdzianów.get(i).getId().toString());
                ocena = new OcenaTabela();
                ocena = db.getOcenaByUzytkownikAndSprawdzian(id_uzytkownik, listaSprawdzianów.get(i).getId());
                String ocena1 = ocena.getOcena().toString();
                ocenySprawdzianow.add(ocena1);
            }

            final OcenyUzytkownikaAdapter adapter = new OcenyUzytkownikaAdapter();
            listView.setAdapter(adapter);
        }
        db.close();

        bWyloguj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LogowanieActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }

    private class OcenyUzytkownikaAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if(listaSprawdzianów !=null && listaSprawdzianów.size() !=0)
                return listaSprawdzianów.size();
            return 0;

        }

        @Override
        public Object getItem(int position) {
            return listaSprawdzianów.get(position);
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
                LayoutInflater inflater = OcenyUzytkownikaActivity.this.getLayoutInflater();
                convertView = inflater.inflate(R.layout.oceny_uzytkownika_list,null);
                holder.textView = (TextView) convertView.findViewById(R.id.sprawdzian);


                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.ref = position;


            holder.textView.setText(listaSprawdzianów.get(position).getNazwaSprawdzianu() + " " + ocenySprawdzianow.get(position));


            return convertView;
        }

        private class ViewHolder {
            TextView textView;
            int ref;
        }
    }
}
