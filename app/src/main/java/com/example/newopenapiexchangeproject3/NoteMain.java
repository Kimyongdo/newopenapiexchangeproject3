package com.example.newopenapiexchangeproject3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import static com.example.newopenapiexchangeproject3.NoteText.notelist;

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

    boolean isDarkmode = true;
    Switch actionview;

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
        fabbtn_text = findViewById(R.id.menu_item3);
        Glide.with(this).load(R.drawable.worldwide1).into(fabbtn_world); //navigation item은 px상관없이 작게 들어가는데 비해 fab 속 이미지는 glide을 통해서 들어가야 사이즈 조절이 알맞게 가능함. wrap -> 24dp
        Glide.with(this).load(R.drawable.calculator1).into(fabbtn_cal);
        Glide.with(this).load(R.drawable.text).into(fabbtn_text);


        ///////////////////////////////////////////////다크 테마////////////////////////////////////////////////////////////////////
        Menu menu = navigationView.getMenu(); //네비게이션의 메뉴부분을 가져오고,
        actionview= (Switch)menu.findItem(R.id.nav_switch).getActionView().findViewById(R.id.otoSwitch); // 그 중 switch에 해당하는 아이디를 가져온다.
        actionview.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) { //처음엔 true로 시작한다.
                if(isDarkmode==b){
                    AppCompatDelegate.setDefaultNightMode((AppCompatDelegate.MODE_NIGHT_YES));
                    SharedPreferences preferences =NoteMain.this.getSharedPreferences("switch",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("switchman",true);
                    editor.commit();
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    SharedPreferences preferences =NoteMain.this.getSharedPreferences("switch",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("switchman",false);
                    editor.commit();
                }
            }         //토글버튼을 이용할 때는 ChangeListner를 사용해야함, click을 하면 두번 눌러야 한번 작동이 됨.
        });

        SharedPreferences sharedPreferences = getSharedPreferences("switch",MODE_PRIVATE);
        if(sharedPreferences!=null){
            boolean trueman = sharedPreferences.getBoolean("switchman",true);
            if(trueman){
                actionview.setChecked(true);
            }else{
                actionview.setChecked(false);
            }
        }
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



        /////////////////////////////////////////네비게이션//////////////////////////////////////////////////////////////////
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_note :
                        Intent intent = new Intent(NoteMain.this,NoteMain.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(navigationView); //클 릭 후 네비뷰 닫힘
                        break;
                    case R.id.updateIcon:
                        Intent intent0 = new Intent(NoteMain.this, UpdateMain.class);
                        startActivity(intent0);
                        drawerLayout.closeDrawer(navigationView); //클릭 후 네비뷰 닫힘

                }

                return false;
            }
        });

        //노트 연결화면 준비중.


        noteRecycler = findViewById(R.id.note_recycler);
        noteAdapter = new NoteAdapter(this);
        noteRecycler.setAdapter(noteAdapter);


        Dataload();
        noteAdapter.notifyDataSetChanged();
    }

    //리사이클러뷰는 장소가 없기 때문에 이를 보여주는 액티비티에서 result를 받아야한다.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 100:
                if(resultCode==RESULT_OK){
                    //에버노트 경우 최신으로 바뀌지 않고 그 날 고정함.
                    notelist.set(data.getIntExtra("number",0),new NoteVO(GlobalTime.noetime,data.getStringExtra("title"),data.getStringExtra("content")));
                    noteAdapter.notifyDataSetChanged();
                }
        }
    }

    public void Dataload(){
        try {
            File file = new File(getFilesDir(),"notelist");
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            notelist = (ArrayList<NoteVO>) ois.readObject();  //앱 꺼도 새롭게 저장한 파일이 있음을 파악함.
            ois.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void ClikNationSelction2(View view) {
        fab.close(true); //자동으로 닫히도록.
        Intent intent = new Intent(this, SelectNation.class);
        startActivity(intent);
    }

    public void clickCalculator2(View view) {
        Intent intent = new Intent(this, CalCalculator.class);
        startActivity(intent);
    }


    public void clicktext2(View view) {
        Intent intent = new Intent(this, NoteText.class);
        startActivity(intent);
    }
}
