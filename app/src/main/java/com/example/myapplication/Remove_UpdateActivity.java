package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class Remove_UpdateActivity extends AppCompatActivity {

    Integer Kod_id;
    TextInputLayout  Name, Adres, Strix ;
    Connection connection;
    String ConnectionResult = "";
    Connection connect;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_update);

        Bundle arguments = getIntent().getExtras();

        String _name = arguments.get("Название").toString();
        String _adres = arguments.get("Адрес").toString();
        String _strix_kod = arguments.get("Штрих код").toString();
        Kod_id = Integer.valueOf(arguments.get("Код").toString());


        Name   = findViewById(R.id.nazvanie);
        Adres  = findViewById(R.id.adres);
        Strix  = findViewById(R.id.strix_kod);


        Name.getEditText().setText(_name);
        Adres.getEditText().setText(_adres);
        Strix.getEditText().setText(_strix_kod);
        
    }


    public void Delete(View v) {
        try {
            ConnectionHelp conectionHellper = new ConnectionHelp();
            connection = conectionHellper.connect();
                    String delet = "delete FROM Cklad where Kod_cklada = "+Kod_id;
                    Statement statement2 = connection.createStatement();
                    statement2.execute(delet);
                    finish();
                    Toast.makeText(this,"Успешно удалено", Toast.LENGTH_LONG).show();

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public void Update(View v) {


        try {
            ConnectionHelp connectionHelper = new ConnectionHelp();
            connection = connectionHelper.connect();
            if (connection != null) {

                String upd = "update Cklad set Nazvanie ='"
                        + Objects.requireNonNull(Name.getEditText().getText())
                        + "',Adres ='" + Objects.requireNonNull(Adres.getEditText()).getText()
                        + "', Strix_kod_tovara ='" + Objects.requireNonNull(Strix.getEditText()).getText()+
                        "' where Kod_cklada = " + Kod_id;
                Statement statement = connection.createStatement();
                statement.execute(upd);
                Toast.makeText(this,"Успешно изменено", Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            Log.e("Error", ex.getMessage());
        }
    }

    public void back(View v) {
        switch (v.getId()) {
            case R.id.button_back:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }

}