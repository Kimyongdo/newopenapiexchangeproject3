package com.example.newopenapiexchangeproject3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Cal_DialogAdapter extends BaseAdapter {
    LayoutInflater inflater;
    ArrayList<Cal_Alert_dialog> arraylist;

    public Cal_DialogAdapter(LayoutInflater inflater, ArrayList<Cal_Alert_dialog> arraylist) {
        this.inflater = inflater;
        this.arraylist = arraylist;
    }

    @Override
    public int getCount() {
        return arraylist.size();
    }

    @Override
    public Object getItem(int position) {
        return arraylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view==null){
            view = inflater.inflate(R.layout.calculator_dialog_itemlist,null);
        }

        Cal_Alert_dialog cal_alert_dialog = arraylist.get(position);

        ImageView iv = view.findViewById(R.id.iv_nation_selection_alert);
        TextView tv = view.findViewById(R.id.tv_nation_selection_alert);

        iv.setImageResource(cal_alert_dialog.getIv());
        tv.setText(cal_alert_dialog.getTv());
        return view;
    }
}
