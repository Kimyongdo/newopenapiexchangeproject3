package com.example.newopenapiexchangeproject3;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.newopenapiexchangeproject3.NoteText.notelist;

public class NoteSearchAdapter extends BaseAdapter {
    Context context;
    ArrayList<NoteVO> notelistcopy;

    public NoteSearchAdapter(Context context, ArrayList<NoteVO> notelistcopy) {
        this.context = context;
        this.notelistcopy = notelistcopy;
    }

    @Override
    public int getCount() {
        return notelistcopy.size();
    }

    @Override
    public Object getItem(int i) {
        return notelistcopy.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        if(view==null){
            view = LayoutInflater.from(context).inflate(R.layout.note_search_itemlist,viewGroup,false);
        }
        NoteVO noteVO = notelistcopy.get(i);
        TextView notetime = view.findViewById(R.id.tv_note_time2);
        TextView notetitle = view.findViewById(R.id.tv_note_title2);
        TextView noteContent = view.findViewById(R.id.tv_note_content2);

        notetime.setText(noteVO.getNoteDate());
        notetitle.setText(noteVO.getNoteTitle());
        noteContent.setText(noteVO.getNoteContent());

        return view;
    }
}

