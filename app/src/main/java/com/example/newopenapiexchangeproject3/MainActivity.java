package com.example.newopenapiexchangeproject3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;

import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.newopenapiexchangeproject3.JsonExchangeRate.cur_nm;
import static com.example.newopenapiexchangeproject3.JsonExchangeRate.cur_unit;
import static com.example.newopenapiexchangeproject3.JsonExchangeRate.iv_nationflag;
import static com.example.newopenapiexchangeproject3.JsonExchangeRate.kftc_deal_bas_r;
import static com.example.newopenapiexchangeproject3.GlobalTime.date2;
import static com.example.newopenapiexchangeproject3.GlobalTime.dateFormat2;
import static com.example.newopenapiexchangeproject3.GlobalTime.newstime2;
import static com.example.newopenapiexchangeproject3.GlobalTime.timedifferent;
import static com.example.newopenapiexchangeproject3.WeatherJSon.todayC1;
import static com.example.newopenapiexchangeproject3.WeatherJSon.todayweather;

public class MainActivity extends AppCompatActivity {


    //카카오톡 이미지 및 닉네임
    static long nicknumber; //카카오톡 고유넘버
    String kNickname;
    String kNickimage;
    String logoutNickname;
    int logoutImage;
    int logoutcheckout;
    int logincheckin;

    View headerView;
    TextView navUsername;
    CircleImageView navUserimage;

    //플로팅버튼
    FloatingActionMenu fab;
    FloatingActionButton fabbtn_world;
    FloatingActionButton fabbtn_cal;
    FloatingActionButton fabbtn_text;

    //레이아웃
    DrawerLayout drawerLayout;
    static NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;                                     //toolbar import시 appcompat->setsupport 가능해짐.
    RecyclerView recyclerView;

    public static ArrayList<Itemlist> datas =new ArrayList<>();
    static ArrayList<kakaoVO> kakaodatas  = new ArrayList<>();
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

        Runnable r = new MyRunnable();
        Thread tt = new Thread(r);
        tt.start();
        try {
            tt.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Toast.makeText(this, "no"+"", Toast.LENGTH_SHORT).show();
        }
//        JsonExchangeRate jsonExchangeRate = new JsonExchangeRate();
//        jsonExchangeRate.sendRequest();
//
//
//        WeahterCallMethod  weahterCallMethod= new WeahterCallMethod();
//        weahterCallMethod.WeahterCallMethod();
//        //대량의 데이터 - 시간
//        GlobalTime globalTime = new GlobalTime();
//        globalTime.globaltime();
//        globalTime.koreantime();
//        globalTime.Notetime();
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


        //////////////////////////////////카카오톡 로그인 네비게이션뷰 연결/////////////////////////////////////////////////////

        ///////////////////////////////////////////////다크 테마////////////////////////////////////////////////////////////////////
        Menu menu = navigationView.getMenu(); //네비게이션의 메뉴부분을 가져오고,
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                actionview= (Switch)menu.findItem(R.id.nav_switch).getActionView().findViewById(R.id.otoSwitch); // 그 중 switch에 해당하는 아이디를 가져온다.
            }
        }
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

        Dataload();
        //리싸이클러 연결하기 - onCreate에서만 보임.
        recyclerView = findViewById(R.id.recyclerview);
        recyclerAdapter = new RecylcerAdapter(datas,this);
        recyclerView.setAdapter(recyclerAdapter);

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
                        break;
                    case R.id.address:
                        Intent intent1 = new Intent(MainActivity.this,Address.class); //여기로 들어가면 로그인 하도록 하고 싶은뎅.
                        startActivity(intent1);
                        drawerLayout.closeDrawer(navigationView); //클릭 후 네비뷰 닫힘
                        break;
                }
                return false;
            }
        });

        ///////////////////////////////////////새로고침 기능////////////////////////////////////////////////////////
        swipeRefreshLayout = findViewById(R.id.layout_refresh);
        swiperefresh();

        ////////////////////////////////////////////카카오 기능//////////////////////////////////////////////////
        getHashKey();
        DataloadKakao();

        if(kakaodatas.size()==0) return;
            //로그인
        else if(kakaodatas.get(0).getLogintnumber()==124){
            headerView = navigationView.getHeaderView(0);
            navUsername = headerView.findViewById(R.id.tv_navi_header_name);
            navUserimage = headerView.findViewById(R.id.iv_header);
            navUsername.setText(kakaodatas.get(0).getLoginNickname()+"님"); //유저이름+"님"
            Glide.with(this).load(kakaodatas.get(0).getLoginNickimage()).into(navUserimage);
        }
        //로그아웃
        else if(kakaodatas.get(0).getLogoutnumber()==123){
            headerView = navigationView.getHeaderView(0);
            navUsername = headerView.findViewById(R.id.tv_navi_header_name);
            navUserimage = headerView.findViewById(R.id.iv_header);
            navUsername.setText("Anonymous"+"님"); //유저이름+"님
            Glide.with(this).load(R.drawable.user).into(navUserimage);
        }

        //업데이트 기능 - 끝쪽에 놔야 작동을- 대기시간이 필요한듯.
        UpdateDataLoad();
    }///////////////////////////////////////////////////onCreate//////////////////////////////////////////////////////////////////

    class MyRunnable implements Runnable{

        @Override
        public void run() {
            JsonExchangeRate jsonExchangeRate = new JsonExchangeRate();
            jsonExchangeRate.sendRequest();
            WeahterCallMethod  weahterCallMethod= new WeahterCallMethod();
            weahterCallMethod.WeahterCallMethod();
            //대량의 데이터 - 시간
            GlobalTime globalTime = new GlobalTime();
            globalTime.globaltime();
            globalTime.koreantime();
            globalTime.Notetime();
        }
    }

    //actionbar 붙이는 곳
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater(); //메뉴에서 가져올 인플레이터이므로 layout이 아닌 Menu
        inflater.inflate(R.menu.loginicon, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int n = item.getItemId();
        switch (n){
            case R.id.menu_lock:
                Intent intent = new Intent(this, KaKaoLoginclass.class); //여기로 들어가면 로그인 하도록 하고 싶은뎅.
                startActivityForResult(intent,333);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 333:
                if(resultCode==RESULT_OK){
                    //로그인 넘버로 판별식 짜야함.
                    nicknumber = data.getLongExtra("nicknumber",0);
                    //로그인사진
                    kNickname = data.getStringExtra("nickname");
                    kNickimage = data.getStringExtra("nicknameimage");
                    //로그아웃 사진
                    logoutNickname = data.getStringExtra("logoutNickname");
                    logoutImage = data.getIntExtra("logoutImage",0);
                    //로그인,로그아웃 임의토큰
                    logincheckin = data.getIntExtra("login",0);
                    logoutcheckout  = data.getIntExtra("logout",0);

                    //카카오톡 프로필 이미지 사진 나옴.

                     headerView = navigationView.getHeaderView(0);
                     navUsername = headerView.findViewById(R.id.tv_navi_header_name);
                     navUserimage = headerView.findViewById(R.id.iv_header);

                    //로그인
                    if(logincheckin==124){
                        Glide.with(this).load(kNickimage).into(navUserimage);
                        navUsername.setText(kNickname+"님"); //유저이름+"님"
                    }
                    //로그아웃
                    else if(logoutcheckout==123){
                        Glide.with(this).load(logoutImage).into(navUserimage);
                        navUsername.setText(logoutNickname+"님"); //유저이름+"님"
                    }

                    kakaodatas.add(0,new kakaoVO(kNickname,kNickimage,logincheckin,logoutNickname,logoutImage,logoutcheckout));
                    DataSaveKakao();
                }

        }
    }

    public void DataSaveKakao(){
        try {
            File file = new File(getFilesDir(),"kakao.tmp");
            FileOutputStream  fos  = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(kakaodatas); //현재의 datas를 저장.
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void DataloadKakao(){
        try {
            File file = new File(getFilesDir(),"kakao.tmp");
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            kakaodatas= (ArrayList<kakaoVO>) ois.readObject();  //앱 꺼도 새롭게 저장한 파일이 있음을 파악함.
            ois.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }




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
        }else if(fab.isOpened()) {
            fab.close(true);
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



    //계산기로 이동
    public void clickCalculator(View view) {
        fab.close(true);
        Intent intent = new Intent(this, CalCalculator.class);
        startActivity(intent);

    }

    //메모장으로 이동
    public void clicktext(View view) {
        fab.close(true);
        Intent intent = new Intent(this, NoteText.class);
        startActivity(intent);

    }

    //
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
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }//refresh

    public void UpdateDataLoad(){

        Runnable r = new MyRunnable();
        Thread tt = new Thread(r);
        tt.start();
        try {
            tt.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Toast.makeText(this, "no"+"", Toast.LENGTH_SHORT).show();
        }

      ////////////////////  //지우지 마시길.. /////////////////////
        //다시 한번 메소드를 집어넣음 안넣으니까 실시간 반영이 불가/능함.
//        JsonExchangeRate jsonExchangeRate = new JsonExchangeRate();
//        jsonExchangeRate.sendRequest();
//
//        //대량의 데이터 - 시간
//        GlobalTime globalTime = new GlobalTime();
//        globalTime.globaltime();
//        globalTime.koreantime();
        if(datas.size()==0) return; //0이여도 할게 없으니까 바로 종료해도 문제 없을 듯
        for(int i=0; i<datas.size(); i++){
            int k;
            switch (datas.get(i).getNationimage()){ //여기서는 준비중입니다임.
                case R.drawable.a01_arabemirates:
                  k=0; //숫자를 직접 입력해서 넣으면 OOM이 발생해서 오류가 뜬다.
                       //시계는 같이 써야 작용한다.SearchNaverJson1를 써야 뉴스의 글씨가 나타난다!!!<----------매우중요.. ㅋㅋ 그럼 왜  static쓴거지 ㅠㅠ
                    SetData(i,k);
                    break;
                case R.drawable.a02_australia:
                   k=1;
                    SetData(i,k);
                    break;
                case R.drawable.a03_bahrain:
                    k=2;
                    SetData(i,k);
                    break;
                case R.drawable.a04_canada:
                    k=3;
                    SetData(i,k);
                    break;
                case R.drawable.a05_switzerland:
                    k=4;
                    SetData(i,k);
                    break;
                case R.drawable.a06_china:
                    k=5;
                    SetData(i,k);
                    break;
                case R.drawable.a07_denmark:
                    k=6;
                    SetData(i,k);
                    break;
                case R.drawable.a08_europeaninion:
                    k=7;
                    SetData(i,k);
                    break;
                case R.drawable.a09_unitedkingdom:
                    k=8;
                    SetData(i,k);
                    break;
                case R.drawable.a10_hongkong:
                    k=9;
                    SetData(i,k);
                    break;
                case R.drawable.a11_indonesia:
                    k=10;
                    SetData(i,k);
                    break;
                case R.drawable.a12_japan:
                    k=11;
                    SetData(i,k);
                    break;
                case R.drawable.a13_korea:
                    k=12;
                    SetData(i,k);
                    break;
                case R.drawable.a14_kuwait:
                    k=13;
                    SetData(i,k);
                    break;
                case R.drawable.a15_malaysia:
                    k=14;
                    SetData(i,k);
                    break;
                case R.drawable.a16_norway:
                    k=15;
                    SetData(i,k);
                    break;
                case R.drawable.a17_newzealand:
                    k=16;
                    SetData(i,k);
                    break;
                case R.drawable.a18_saudiarabia:
                    k=17;
                    SetData(i,k);
                    break;
                case R.drawable.a19_sweden:
                    k=18;
                    SetData(i,k);
                    break;
                case R.drawable.a20_singapore:
                    k=19;
                    SetData(i,k);
                    break;
                case R.drawable.a21_thailand:
                    k=20;
                    SetData(i,k);
                    break;
                case R.drawable.a22_usa:
                    k=21;
                    SetData(i,k);
                    break;
            }

        }
        //swipeRefreshLayout.setRefreshing(false);
        DataSave();
        recyclerAdapter.notifyDataSetChanged();
    }

    public void SetData(int i, int k){
        datas.set(i,new Itemlist(cur_nm[k],cur_unit[k],kftc_deal_bas_r[k],iv_nationflag[k]
                ,newstime2,dateFormat2[k].format(date2),
                todayC1[k],todayweather[k],timedifferent[k]
        ));
    }
    private void getHashKey(){
        try {                                                        // 패키지이름을 입력해줍니다.
            PackageInfo info = getPackageManager().getPackageInfo("com.example.newopenapiexchangeproject3", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
                    Log.d("카카오톡 해쉬코드","key_hash="+ Base64.encodeToString(md.digest(), Base64.DEFAULT));
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


}
