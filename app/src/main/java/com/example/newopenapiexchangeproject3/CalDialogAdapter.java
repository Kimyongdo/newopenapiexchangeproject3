package com.example.newopenapiexchangeproject3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CalDialogAdapter extends BaseAdapter {
    LayoutInflater inflater;
    ArrayList<CalAlertdialog> arraylist;

    public CalDialogAdapter(LayoutInflater inflater, ArrayList<CalAlertdialog> arraylist) {
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

        CalAlertdialog cal_alertdialog = arraylist.get(position);

        ImageView iv = view.findViewById(R.id.iv_nation_selection_alert);
        TextView tv = view.findViewById(R.id.tv_nation_selection_alert);

        iv.setImageResource(cal_alertdialog.getIv());
        tv.setText(cal_alertdialog.getTv());
        return view;
    }
}
