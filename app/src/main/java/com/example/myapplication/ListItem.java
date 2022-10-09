package com.example.myapplication;

import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListItem {

Connection connect;
String ConnectionResult="";
Boolean isSucces= false;

public List<Map<String,String>>getlist()
{
    List<Map<String,String>> data= null;
    data = new ArrayList<Map<String,String>>();
    try
    {
        ConnectionHelp connectionHelp = new ConnectionHelp();
        connect = connectionHelp.connect();
        if( connect!= null)
        {
            String qu = "select * from Cklad";
            Statement statement= connect.createStatement();
            ResultSet resultSet = statement.executeQuery(qu);
            while ( resultSet.next())
            {
                Map<String,String> dtname = new HashMap<String,String>();
                dtname.put("Kod",resultSet.getString("Kod_cklada"));
                dtname.put("IName",resultSet.getString("Nazvanie"));
                dtname.put("IdAdres",resultSet.getString("Adres"));
                dtname.put("StrixKod",resultSet.getString("Strix_kod_tovara"));
                data.add(dtname);
            }
            ConnectionResult = "Succes";
            isSucces=true;
            connect.close();
        }
        else
        {
            ConnectionResult= "Failed";
        }
    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }
    return data;
}
}
