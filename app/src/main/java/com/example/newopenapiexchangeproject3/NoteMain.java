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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.example.newopenapiexchangeproject3.MainActivity.nicknumber;
import static com.example.newopenapiexchangeproject3.NoteText.notelist;
import static com.example.newopenapiexchangeproject3.NoteText.testlist;

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
    static NoteAdapter noteAdapter;

    boolean isDarkmode = true;
    Switch actionview;

    String str;


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


        //데이터스를 로드.
        if(nicknumber!=0.0){
           //DB데이터 로드 //쓰레드인데 바로 데이터를 얻으려고 하니. testlist.size가 0이 나옴.---여기가 또 수정해야함.
            NoteLoadFromDB();
            testlist.addAll(notelist);
        }else{
            Dataload();   //휴대폰 내부 데이터 로드
            testlist.addAll(notelist);
        }


        noteAdapter.notifyDataSetChanged();
    }//////////////////////////////////////////////////////////oncreate///////////////////////////////////////////////////

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        notesearch = findViewById(R.id.et_note_search);

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search,menu);
        final MenuItem menuitem = menu.findItem(R.id.menu_action);
        View v = menuitem.getActionView(); //레이아웃을 찾아줘
        final EditText actionVeiwEditText;
        actionVeiwEditText = v.findViewById(R.id.actionview_et); //레이아웃에 있는 editText를 차아줘
        actionVeiwEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) { //i는 actionID의 값
                if(i== EditorInfo.IME_ACTION_SEARCH) {//actionid가 에디터정보 중 서치버튼이냐?
                    actionVeiwEditText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }
                        @Override
                        public void afterTextChanged(Editable editable) {
                             str = actionVeiwEditText.getText().toString();
                            search(str);
                        }
                    });

                    menuitem.collapseActionView(); //collapse 다시 무너뜨려라 = 닫쳐라. //이거 안 쓰면 내용이 그대로 남아있음.

                }
                return true;//return false를 true로 바꿔라.
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    public void search(String text){
        Log.d("TAAAAAGGGG",str+"");
        //기본적으로 보이는 arraylist를 지우고
        notelist.clear();
        if(text.length()==0){
            //edittext에 아무것도 없다면 기존datalist에 이를 복사한 datlistSearch를 넣어주고
            notelist.addAll(testlist);
            Log.d("testlist.size",testlist.size()+""); //아니 왜 안되다가 갑자기 또 되는거지..
        }else{

            for(int i=0; i<testlist.size(); i++){
                String choName = HangulUtils.getHangulInitialSound(testlist.get(i).getNoteTitle(), text); //제목
                String choContent = HangulUtils.getHangulInitialSound(testlist.get(i).getNoteContent(), text); //내용
                //문자의 text가 하나라도 써져있다면 이에 해당하는 itemlist를 추가.
                if(choName.indexOf(text)>=0 || choContent.indexOf(text)>=0){
                    notelist.add(testlist.get(i));
                }
            }
        }

        noteAdapter.notifyDataSetChanged();
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

    public void NoteLoadFromDB(){
        String serverUrl ="http://chocojoa123.dothome.co.kr/Exchange/NoteLoad.php";
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, serverUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                notelist.clear();
                noteAdapter.notifyDataSetChanged();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        int no = Integer.parseInt(jsonObject.getString("no")); //db테이블의 제목이 no라고 하는 것을 가져옴
                        String number = jsonObject.getString("number");     //db테이블의 content 제목에 해당하는 값을 가져옴
                        String time = jsonObject.getString("time");
                        String title = jsonObject.getString("title");
                        String content = jsonObject.getString("content");

                        if(number.equals(nicknumber+"")){ //배열을 돌리면서 db number와 카카오 아이디가 같으면 그것만 추가하도록.
                            notelist.add(0,new NoteVO(time, title, content)); //첫 글씨를 쓴 것을 저장하는 만큼 문제 없음.
                            //noteAdapter.notifyItemInserted(0);
                            noteAdapter.notifyDataSetChanged();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        //실제 요청작업을 수행해주는 요청큐 객체 생성
        RequestQueue requestQueue= Volley.newRequestQueue(this);

        //요청큐에 요청객체 추가
        requestQueue.add(jsonArrayRequest);
    }
}
