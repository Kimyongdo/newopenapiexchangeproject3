package com.example.newopenapiexchangeproject3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import static com.example.newopenapiexchangeproject3.MainActivity.nicknumber;
import static com.example.newopenapiexchangeproject3.NoteMain.noteAdapter;
import static com.example.newopenapiexchangeproject3.NoteText.notelist;
import static com.example.newopenapiexchangeproject3.NoteText.testlist;

public class NoteSearching extends AppCompatActivity {

    EditText noteEditText;
    ListView notesearchlistview;
    NoteSearchAdapter noteSearchAdapter;
    ArrayList<NoteVO> notelistcopy = new ArrayList<>();
    ArrayList<NoteVO> testlistcopy = new ArrayList<>();

    String str;
    ImageView imageView;
    ListView notelistview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_searching);

        imageView = findViewById(R.id.arrow);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        noteEditText = findViewById(R.id.note_search2);
        notesearchlistview = findViewById(R.id.notesearchlistview2);

        notelistcopy.clear(); //clear쓴 이유는 onCreate할때마다 notelist가 copy버전에 계속 추가되서 list분량이 들어남;
        notelistcopy.addAll(notelist);
        testlistcopy.clear();
        testlistcopy.addAll(testlist);

        noteSearchAdapter = new NoteSearchAdapter(this,notelistcopy);
        notesearchlistview.setAdapter(noteSearchAdapter);

        notesearchlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //리스튜뷰에 보이는 숫자를 나타냄 3 4 5
                noteSearchAdapter.getItem(i);
                //복사한 datalistSearch(전체) 중 그에 속한 번호를 알려줌  5라면 전체중에서 몇번일까?
                Log.d("tttttttttttttttttttttt", notelistcopy.size()+"");
                Log.d("tttttttttttttttttttttt", notelist.size()+"");
                int actualPostion=testlistcopy.indexOf(noteSearchAdapter.getItem(i));
                Toast.makeText(NoteSearching.this, actualPostion+"", Toast.LENGTH_SHORT).show(); //현위치 <- 가장 문제가 되는거
                Toast.makeText(NoteSearching.this, notelistcopy.size()+"", Toast.LENGTH_SHORT).show();//지금 보여주는 량
                Toast.makeText(NoteSearching.this, notelist.size()+"", Toast.LENGTH_SHORT).show();//전체량
                Toast.makeText(NoteSearching.this, testlistcopy.size()+"", Toast.LENGTH_SHORT).show();
            }
        });

        noteEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                str = noteEditText.getText().toString();
                search(str);
            }
        });
    }/////////////////////oncreate///////////////////////////////////////////////


    public void search(String text){
        Log.d("TAAAAAGGGG",str+"");
        //adapter에 추가한 list를 기반으로하고 이를 복제한 list로 바꾸면서 보여지는 것.
        notelistcopy.clear();
        if(text.length()==0){
            notelistcopy.addAll(testlistcopy);
        }else{

            for(int i=0; i<testlistcopy.size(); i++){
                String choName = HangulUtils.getHangulInitialSound(testlistcopy.get(i).getNoteTitle(), text); //제목
                String choContent = HangulUtils.getHangulInitialSound(testlistcopy.get(i).getNoteContent(), text); //내용
                //문자의 text가 하나라도 써져있다면 이에 해당하는 itemlist를 추가.
                if(choName.indexOf(text)>=0 || choContent.indexOf(text)>=0){
                    notelistcopy.add(testlistcopy.get(i));
                }
            }
        }

        noteSearchAdapter.notifyDataSetChanged();
    }
}
