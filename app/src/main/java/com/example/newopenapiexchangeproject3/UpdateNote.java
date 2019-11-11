package com.example.newopenapiexchangeproject3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import static com.example.newopenapiexchangeproject3.UpdateMain.updates;

public class UpdateNote extends AppCompatActivity {


    TextView tv_updateContent;
    TextView tv_updateTitle;
    TextView tv_updateTime;

    int number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_note);


        //툴바
        Toolbar toolbar;
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        number = intent.getIntExtra("number",0);

        tv_updateContent=findViewById(R.id.tv_UpdateNote_content);
        tv_updateTitle = findViewById(R.id.tv_updateNote_title);
        tv_updateTime = findViewById(R.id.tv_updateNote_Time);

        tv_updateContent.setText(updates.get(number).getUpdateContent());
        tv_updateTitle.setText(updates.get(number).getUpdateTtitle());
        tv_updateTime.setText(updates.get(number).getUpdateTime());


    }


    @Override
    public void onBackPressed() {
        //뒤로가기 누르면 계속 원래 페이지를 보여준다. 그래서 우선 intent를 시킴.
        super.onBackPressed();
        Intent intent = new Intent(this, UpdateMain.class);
        startActivity(intent);
        finish();
    }

}
