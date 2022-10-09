package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {

    Context context;
    LayoutInflater lInflater;
    ArrayList<Cklad> cklads;

    Adapter(Context context, ArrayList<Cklad> cklads){
        this.context = context;
        this.cklads = cklads;
        lInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return cklads.size();
    }

    @Override
    public Object getItem(int i) {
        return cklads.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("SetTextI18n")

    @Override
    public View getView(int i, View vieww, ViewGroup viewGroup) {

        View view = vieww;

        if (view == null) {
            view = lInflater.inflate(R.layout.listlayoutetemplate, viewGroup,false);
        }
        Cklad cklads = getCklads(i);
        ((TextView) view.findViewById(R.id.Kod)).setText(cklads.Kod_cklada);
        ((TextView) view.findViewById(R.id.IName)).setText(cklads.Nazvanie);
        ((TextView) view.findViewById(R.id.IdAdres)).setText(cklads.Adres);
        ((TextView) view.findViewById(R.id.StrixKod)).setText(cklads.Strix_kod_tovara);
        return view;
    }
    Cklad getCklads(int pos){
        return ((Cklad) getItem(pos));
    }
}