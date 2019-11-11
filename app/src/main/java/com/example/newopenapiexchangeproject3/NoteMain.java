package com.example.newopenapiexchangeproject3;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.navigation.NavigationView;

public class NoteMain extends AppCompatActivity {

    //플로팅버튼
    FloatingActionMenu fab;
    FloatingActionButton fabbtn_world;
    FloatingActionButton fabbtn_cal;
    FloatingActionButton fabbtn_text;

    //레이아웃
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;                                     //toolbar import시 appcompat->setsupport 가능해짐.

    RecyclerView noteRecycler;
    //어레이리스트는 note에서 저장할떄 만들었으니 거기서 불러오는 걸로 해야할듯?
    NoteAdapter noteAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_main);


        ///////////////////////////네비게이션 뷰 및 플로팅 버튼 연결하기 ///////////////////////////////////////
        drawerLayout = findViewById(R.id.layout_drawer);
        navigationView = findViewById(R.id.navi);
        navigationView.setItemIconTintList(null); //네비게이션 아이콘 보임.
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle); //화살표모양 누르면 navi소환
        actionBarDrawerToggle.syncState(); //화살표모양->삼선모양

        fab = findViewById(R.id.flaotingActionButton);
        fab.setClosedOnTouchOutside(true);
        fabbtn_world = findViewById(R.id.menu_item_world);
        fabbtn_cal = findViewById(R.id.menu_item_calculator);
        fabbtn_text = findViewById(R.id.menu_item_text);
        Glide.with(this).load(R.drawable.worldwide1).into(fabbtn_world); //navigation item은 px상관없이 작게 들어가는데 비해 fab 속 이미지는 glide을 통해서 들어가야 사이즈 조절이 알맞게 가능함. wrap -> 24dp
        Glide.with(this).load(R.drawable.calculator1).into(fabbtn_cal);
        Glide.with(this).load(R.drawable.text).into(fabbtn_text);




        noteRecycler = findViewById(R.id.note_recycler);

        noteAdapter = new NoteAdapter(this, notelist);
        noteRecycler.setAdapter(noteAdapter);




    }
}
