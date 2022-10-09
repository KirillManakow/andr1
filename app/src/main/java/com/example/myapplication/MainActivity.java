package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import android.widget.Spinner;


public class MainActivity extends AppCompatActivity {

    ArrayList<Cklad> ckladList = new ArrayList<>();
    ArrayList<Cklad> ckladss;
    Connection connect;
    String ConnectionResult="";
    ListView listView;
    Intent Remove_UpdateActivityy;
    Spinner spinner;
    Adapter adapter;
    EditText searh_button;

    @SuppressLint("MissingInflatedId")
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list_bd);

        Remove_UpdateActivityy = new Intent(this,Remove_UpdateActivity.class);

        GetCkladList();

        listView = findViewById(R.id.list_bd);
        searh_button = findViewById(R.id.searchbutton);

        String[] sort_name = { "Стандартная сортировка", "По имени склада","По адресу"};
        spinner= findViewById(R.id.spin);
        ArrayAdapter<String> spinner_adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, sort_name);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinner_adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
               Cklad item = (Cklad) listView.getItemAtPosition(i);

                Remove_UpdateActivityy.putExtra("Название",item.Nazvanie);
                Remove_UpdateActivityy.putExtra("Адрес",item.Adres);
                Remove_UpdateActivityy.putExtra("Штрих код",item.Strix_kod_tovara);
                Remove_UpdateActivityy.putExtra("Код", item.Kod_cklada);

                startActivity(Remove_UpdateActivityy);
            }
        });

      spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                switch (position) {

                    case 0:
                        Collections.sort(ckladList, new Comparator<Cklad>() {
                            @Override
                            public int compare(Cklad cklad, Cklad t1) {
                                return cklad.Kod_cklada.compareTo(t1.Kod_cklada);
                            }
                        });
                        adapter = new Adapter(MainActivity.this,ckladList);
                        listView.setAdapter(adapter);
                        break;

                    case 1:
                        Collections.sort(ckladList, new Comparator<Cklad>() {
                            @Override
                            public int compare(Cklad cklad, Cklad t1) {
                                return cklad.Nazvanie.compareTo(t1.Nazvanie);
                            }
                        });
                        adapter = new Adapter(MainActivity.this,ckladList);
                        listView.setAdapter(adapter);
                        break;

                    case 2:
                        Collections.sort(ckladList, new Comparator<Cklad>() {
                            @Override
                            public int compare(Cklad cklad, Cklad t1) {
                                return cklad.Adres.compareTo(t1.Adres);
                            }
                        });
                        adapter = new Adapter(MainActivity.this, ckladList);
                        listView.setAdapter(adapter);
                        break;
                }
            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });



        searh_button.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(s.toString().equals(" ")){

                    adapter = new Adapter(MainActivity.this,ckladss);
                    listView.setAdapter(adapter);
                }
                else {
                    for(Cklad item:ckladss){
                        if(!item.Nazvanie.contains(s.toString())){
                            ckladList.remove(item);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


     }
     SimpleAdapter ad;




    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonadd:
                Intent intent = new Intent(this, Add_activity.class);
                startActivity(intent);

        }
    }

    public void GetList(View v)
 {

     ListView lstv=(ListView)  findViewById(R.id.list_bd);

     List<Map<String, String>> Mydatalist = null;
     ListItem MyData= new ListItem();
     Mydatalist = MyData.getlist();

     String[] From={"Kod","IName","IdAdres","StrixKod"};
     int[ ] Tow= { R.id.Kod,R.id.IName,R.id.IdAdres,R.id.StrixKod};
     ad = new SimpleAdapter(MainActivity.this,Mydatalist,R.layout.listlayoutetemplate,From,Tow);
     lstv.setAdapter((ad));

 }


    public void GetCkladList(){
        try{
            ckladList.clear();
            connect = ConnectionHelp.connect();
            if(connect != null){
                String qu = "select * from Cklad";
                Statement statement = connect.createStatement();
                ResultSet resultSet = statement.executeQuery(qu);
                while (resultSet.next())
                {
                    ckladList.add(
                            new Cklad(resultSet.getString("Kod_cklada"),
                            resultSet.getString("Nazvanie"),
                            resultSet.getString("Adres"),
                            resultSet.getString("Strix_kod_tovara")));
                }
                ConnectionResult = "Success";
                Adapter adapter = new Adapter(this,ckladList);
               listView.setAdapter(adapter);
                ckladss= (ArrayList<Cklad>) ckladList.clone();
            }
            else {
                ConnectionResult = "Failed";
            }
            Log.d(ConnectionResult,"");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Log.d(ConnectionResult, throwables.getMessage());
        }
    }
}