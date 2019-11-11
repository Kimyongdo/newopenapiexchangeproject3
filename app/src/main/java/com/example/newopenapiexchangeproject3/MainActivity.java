package com.example.newopenapiexchangeproject3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import static com.example.newopenapiexchangeproject3.ExchangeJSON.cur_nm;
import static com.example.newopenapiexchangeproject3.ExchangeJSON.cur_unit;
import static com.example.newopenapiexchangeproject3.ExchangeJSON.iv_nationflag;
import static com.example.newopenapiexchangeproject3.ExchangeJSON.kftc_deal_bas_r;
import static com.example.newopenapiexchangeproject3.GlobalTime.date2;
import static com.example.newopenapiexchangeproject3.GlobalTime.dateFormat2;
import static com.example.newopenapiexchangeproject3.GlobalTime.newstime2;
import static com.example.newopenapiexchangeproject3.GlobalTime.timedifferent;
import static com.example.newopenapiexchangeproject3.WeatherJSon.todayC1;
import static com.example.newopenapiexchangeproject3.WeatherJSon.todayweather;

public class MainActivity extends AppCompatActivity {



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
    RecyclerView recyclerView;

    public static ArrayList<Itemlist> datas =new ArrayList<>();
    static RecylcerAdapter recyclerAdapter;

    //다크모드 on/off
    boolean isDarkmode = true;
    //새로고침 기능
    public static SwipeRefreshLayout swipeRefreshLayout;

    Switch actionview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true); //벡터이미지 관련코드
        setContentView(R.layout.activity_main);

        /////////////////////volley 라이브러리 생성//////////////////////////////
        if(AddHelper.requestQueue == null) {
            AddHelper.requestQueue = Volley.newRequestQueue(this);
        }

        //이걸 주석으로 해도 I/Choreographer: Skipped 39 frames!  The application may be doing too much work on its main thread.
        ExchangeJSON exchangeJSON = new ExchangeJSON();
        exchangeJSON.sendRequest();
        WeahterCallMethod  weahterCallMethod= new WeahterCallMethod();
        weahterCallMethod.WeahterCallMethod();
        //대량의 데이터 - 시간
        GlobalTime globalTime = new GlobalTime();
        globalTime.globaltime();
        globalTime.koreantime();
        /////////////////////////////////////////////////////////////////////////////////////////////////



        //플로팅 버튼 외부 터치시 끝나도록.
        fab = findViewById(R.id.flaotingActionButton);
        fab.setClosedOnTouchOutside(true);
        fabbtn_world = findViewById(R.id.menu_item_world);
        fabbtn_cal = findViewById(R.id.menu_item_calculator);
        fabbtn_text = findViewById(R.id.menu_item_text);
        Glide.with(this).load(R.drawable.worldwide1).into(fabbtn_world); //navigation item은 px상관없이 작게 들어가는데 비해 fab 속 이미지는 glide을 통해서 들어가야 사이즈 조절이 알맞게 가능함. wrap -> 24dp
        Glide.with(this).load(R.drawable.calculator1).into(fabbtn_cal);
        Glide.with(this).load(R.drawable.text).into(fabbtn_text);


        //레이아웃 연결
        drawerLayout = findViewById(R.id.layout_drawer);
        navigationView = findViewById(R.id.navi);
        navigationView.setItemIconTintList(null); //네비게이션 아이콘 보임.
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle); //화살표모양 누르면 navi소환
        actionBarDrawerToggle.syncState(); //화살표모양->삼선모양

        ///////////////////////////////////////////////다크 테마////////////////////////////////////////////////////////////////////
        Menu menu = navigationView.getMenu(); //네비게이션의 메뉴부분을 가져오고,
        actionview= (Switch)menu.findItem(R.id.nav_switch).getActionView().findViewById(R.id.otoSwitch); // 그 중 switch에 해당하는 아이디를 가져온다.
        actionview.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) { //처음엔 true로 시작한다.
                if(isDarkmode==b){
                    AppCompatDelegate.setDefaultNightMode((AppCompatDelegate.MODE_NIGHT_YES));
                    SharedPreferences preferences =MainActivity.this.getSharedPreferences("switch",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("switchman",true);
                    editor.commit();
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    SharedPreferences preferences =MainActivity.this.getSharedPreferences("switch",MODE_PRIVATE);
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

        //데이터 로드
        Dataload();
        //리싸이클러 연결하기 - onCreate에서만 보임.
        recyclerView = findViewById(R.id.recyclerview);
        recyclerAdapter = new RecylcerAdapter(datas,this);
        recyclerView.setAdapter(recyclerAdapter);

        //비교 후 최신데이터
        //NewData(); - 주말이라 데이터가 없어서 그런지 오류가 생김. 평일에 다시 한번 확인해볼 필요가 있음.
        /////////////////////////////////////////네비게이션//////////////////////////////////////////////////////////////////
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_note :
                        Intent intent = new Intent(MainActivity.this,NoteMain.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(navigationView); //클 릭 후 네비뷰 닫힘
                        break;
                    case R.id.updateIcon:
                        Intent intent0 = new Intent(MainActivity.this, UpdateMain.class);
                        startActivity(intent0);
                        drawerLayout.closeDrawer(navigationView); //클릭 후 네비뷰 닫힘

                }

                return false;
            }
        });

        ///////////////////////////////////////새로고침 기능////////////////////////////////////////////////////////
        swipeRefreshLayout = findViewById(R.id.layout_refresh);
        swiperefresh();

        //데이터를 처음부터 로드하는 순간.

    }///////////////////////////////////////////////////onCreate//////////////////////////////////////////////////////////////////




    // onDestory를 통해 뒤로가기 누르고 화면이 완전히 없어지면 datas.add가 추가로 들어가는 것을 막는다.
    @Override
    protected void onDestroy() {
        super.onDestroy();
        datas.removeAll(datas);
    }

    //////////////////////////////FAB - 국가환율추가로 이동/////////////////////////////////////////////
    public void ClikNationSelction(View view) {
        fab.close(true); //자동으로 닫히도록.
        Intent intent = new Intent(this, SelectNation.class);
        startActivity(intent);
    }

    //뒤로가기 버튼을 눌렀을 경우, 네비게이션 화면이 열려있다면  닫혀라
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            finish(); //뒤로가기 누른 경우 앱을 종료.
            super.onBackPressed();
        }
    }

    //load까지 다 했었었네..
    public void Dataload(){
        try {
            File file = new File(getFilesDir(),"t.tmp");
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            datas = (ArrayList<Itemlist>) ois.readObject();  //앱 꺼도 새롭게 저장한 파일이 있음을 파악함.
            ois.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    public void clickCalculator(View view) {
        Intent intent = new Intent(this, Cal_Calculator.class);
        startActivity(intent);

    }
    public void clicktext(View view) {
        Intent intent = new Intent(this, NoteText.class);
        startActivity(intent);

    }

    public void DataSave(){
        try {
            File file = new File(getFilesDir(),"t.tmp");
            FileOutputStream  fos  = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(datas); //현재의 datas를 저장.
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    /////////////////////////////////////////////////////////새로고침기능//////////////////////////////////////////////////////////////
    public void swiperefresh(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                if(datas.size()==0) return;
                UpdateDataLoad();
            }
        });
    }//refresh

    public void UpdateDataLoad(){
        //Dataload();

        //다시 한번 메소드를 집어넣음 안넣으니까 실시간 반영이 불가능함.
        ExchangeJSON exchangeJSON = new ExchangeJSON();
        exchangeJSON.sendRequest();
        //대량의 데이터 - 시간
        GlobalTime globalTime = new GlobalTime();
        globalTime.globaltime();
        globalTime.koreantime();
        if(datas.size()==0) return; //0이여도 할게 없으니까 바로 종료해도 문제 없을 듯
        for(int i=0; i<datas.size(); i++){
            int k;
            switch (datas.get(i).getCur_nm()){
                case "아랍에미리트 디르함":
                  k=0; //숫자를 직접 입력해서 넣으면 OOM이 발생해서 오류가 뜬다.
                       //시계는 같이 써야 작용한다.SearchNaverJson1를 써야 뉴스의 글씨가 나타난다!!!<----------매우중요.. ㅋㅋ 그럼 왜  static쓴거지 ㅠㅠ
                    SetData(i,k);
                    break;
                case "호주 달러":
                   k=1;
                    SetData(i,k);
                    break;
                case "바레인 디나르":
                    k=2;
                    SetData(i,k);
                    break;
                case "캐나다 달러":
                    k=3;
                    SetData(i,k);
                    break;
                case "스위스 프랑":
                    k=4;
                    SetData(i,k);
                    break;
                case "위안화":
                    k=5;
                    SetData(i,k);
                    break;
                case "덴마아크 크로네":
                    k=6;
                    SetData(i,k);
                    break;
                case "유로":
                    k=7;
                    SetData(i,k);
                    break;
                case "영국 파운드":
                    k=8;
                    SetData(i,k);
                    break;
                case "홍콩 달러":
                    k=9;
                    SetData(i,k);
                    break;
                case "인도네시아 루피아":
                    k=10;
                    SetData(i,k);
                    break;
                case "일본 옌":
                    k=11;
                    SetData(i,k);
                    break;
                case "한국 원":
                    k=12;
                    SetData(i,k);
                    break;
                case "쿠웨이트 디나르":
                    k=13;
                    SetData(i,k);
                    break;
                case "말레이지아 링기트":
                    k=14;
                    SetData(i,k);
                    break;
                case "노르웨이 크로네":
                    k=15;
                    SetData(i,k);
                    break;
                case "뉴질랜드 달러":
                    k=16;
                    SetData(i,k);
                    break;
                case "사우디 리얄":
                    k=17;
                    SetData(i,k);
                    break;
                case "스웨덴 크로나":
                    k=18;
                    SetData(i,k);
                    break;
                case "싱가포르 달러":
                    k=19;
                    SetData(i,k);
                    break;
                case "태국 바트":
                    k=20;
                    SetData(i,k);
                    break;
                case "미국 달러":
                    k=21;
                    SetData(i,k);
                    break;
            }

        }
        //아예 새롭게 new RecyclerAdapter를 통해서 만들어냄. 이거 두줄 추가하는데 4시간 걸림 ㅠㅠ..

//        recyclerAdapter = new RecylcerAdapter(datas,MainActivity.this, recyclerView);
//        recyclerView.setAdapter(recyclerAdapter);
//        DataSave();
//        recyclerAdapter.notifyDataSetChanged();
    }

    public void SetData(int i, int k){
        datas.set(i,new Itemlist(cur_nm[k],cur_unit[k],kftc_deal_bas_r[k],iv_nationflag[k]
                ,newstime2,dateFormat2[k].format(date2),
                todayC1[k],todayweather[k],timedifferent[k]
        ));
        DataSave();
        recyclerAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    public void NewData() {
        if (datas.size() == 0) return; //0이여도 할게 없으니까 바로 종료해도 문제 없을 듯
        for (int i = 0; i < datas.size(); i++) {
            int k;
            switch (datas.get(i).getCur_nm()) {
                case "아랍에미리트 디르함":
                    k=0; //숫자를 직접 입력해서 넣으면 OOM이 발생해서 오류가 뜬다.
                    //시계는 같이 써야 작용한다.SearchNaverJson1를 써야 뉴스의 글씨가 나타난다!!!<----------매우중요.. ㅋㅋ 그럼 왜  static쓴거지 ㅠㅠ
                    NewDataCall(i,k);
                    break;
                case "호주 달러":
                    k=1;
                    NewDataCall(i,k);
                    break;
                case "바레인 디나르":
                    k=2;
                    NewDataCall(i,k);
                    break;
                case "캐나다 달러":
                    k=3;
                    NewDataCall(i,k);
                    break;
                case "스위스 프랑":
                    k=4;
                    NewDataCall(i,k);
                    break;
                case "위안화":
                    k=5;
                    NewDataCall(i,k);
                    break;
                case "덴마아크 크로네":
                    k=6;
                    NewDataCall(i,k);
                    break;
                case "유로":
                    k=7;
                    NewDataCall(i,k);
                    break;
                case "영국 파운드":
                    k=8;
                    NewDataCall(i,k);
                    break;
                case "홍콩 달러":
                    k=9;
                    NewDataCall(i,k);
                    break;
                case "인도네시아 루피아":
                    k=10;
                    NewDataCall(i,k);
                    break;
                case "일본 옌":
                    k=11;
                    NewDataCall(i,k);
                    break;
                case "한국 원":
                    k=12;
                    NewDataCall(i,k);
                    break;
                case "쿠웨이트 디나르":
                    k=13;
                    NewDataCall(i,k);
                    break;
                case "말레이지아 링기트":
                    k=14;
                    NewDataCall(i,k);
                    break;
                case "노르웨이 크로네":
                    k=15;
                    NewDataCall(i,k);
                    break;
                case "뉴질랜드 달러":
                    k=16;
                    NewDataCall(i,k);
                    break;
                case "사우디 리얄":
                    k=17;
                    NewDataCall(i,k);
                    break;
                case "스웨덴 크로나":
                    k=18;
                    NewDataCall(i,k);
                    break;
                case "싱가포르 달러":
                    k=19;
                    NewDataCall(i,k);
                    break;
                case "태국 바트":
                    k=20;
                    NewDataCall(i,k);
                    break;
                case "미국 달러":
                    k=21;
                    NewDataCall(i,k);
                    break;
            }
        }
    }
    public void NewDataCall(int i, int k){
        datas.set(i,new Itemlist(cur_nm[k],cur_unit[k],kftc_deal_bas_r[k],iv_nationflag[k]
                ,newstime2,dateFormat2[k].format(date2),
                todayC1[k],todayweather[k],timedifferent[k]
        ));
        DataSave();
        recyclerAdapter.notifyDataSetChanged();
    }
}
