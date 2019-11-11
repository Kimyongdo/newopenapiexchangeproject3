package com.example.newopenapiexchangeproject3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class NoteText extends AppCompatActivity {

    ImageView iv_done;
    EditText et_title;
    EditText et_content;

    String et_title_reader;
    String et_content_reader;

    static ArrayList<String> notelist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_text);

        iv_done = findViewById(R.id.iv_updateNote_first_done);
        et_title = findViewById(R.id.et_note_title);
        et_content = findViewById(R.id.et_note_content);

        et_title.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);



        iv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_title_reader = et_title.getText().toString();
                et_content_reader= et_content.getText().toString();

                notelist.add(et_title_reader);
                notelist.add(et_content_reader);
                //arraylist string으로 저장함.  -> 휴대폰 내부로 저장해야할듯. -> 일단은 그 전에 reclycerview에서 보여주어야하니까.
            }
        });
    }
}
