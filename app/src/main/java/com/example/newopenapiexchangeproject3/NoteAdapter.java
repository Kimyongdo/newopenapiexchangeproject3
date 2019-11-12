package com.example.newopenapiexchangeproject3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.newopenapiexchangeproject3.NoteText.notelist;

public class NoteAdapter extends RecyclerView.Adapter {
    Context context;

    public NoteAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemview = layoutInflater.inflate(R.layout.note_itemlist,parent,false);
        NoteVH noteVH = new NoteVH(itemview);
        return noteVH;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        NoteVH noteVH = (NoteVH)holder;
        NoteVO noteVO = notelist.get(position);
        noteVH.NoteDate.setText(noteVO.getNoteDate());
        noteVH.NoteTitle.setText(noteVO.getNoteTitle());
        noteVH.NoteContent.setText(noteVO.getNoteContent());
    }

    @Override
    public int getItemCount() {
        return notelist.size();
    }



    class NoteVH extends RecyclerView.ViewHolder{

        TextView NoteDate;
        TextView NoteTitle;
        TextView NoteContent;

        LinearLayout NoteLinearlayout;
        public NoteVH(@NonNull View itemView) {
            super(itemView);

            NoteDate = itemView.findViewById(R.id.tv_note_time);
            NoteTitle = itemView.findViewById(R.id.tv_note_title);
            NoteContent = itemView.findViewById(R.id.tv_note_content);

            NoteLinearlayout = itemView.findViewById(R.id.NoteLinearLayout);

            NoteLinearlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int number = getAdapterPosition();
                    Intent intent = new Intent(context, NoteInText.class);
                    intent.putExtra("number",number);
                    ((Activity)context).startActivityForResult(intent,100);
                    //리사이클러뷰는 no place이므로 이를 받아주는 main에서 onResult를 행해야함.
                    //100은 임의의 수로 나중에 FIANL로 상수로 바꿔주어야한다.

                }

            });

        }

    }

}
