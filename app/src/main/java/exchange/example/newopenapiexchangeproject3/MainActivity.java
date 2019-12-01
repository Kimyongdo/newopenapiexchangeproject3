package exchange.example.newopenapiexchangeproject3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;

import com.bumptech.glide.Glide;
import com.example.newopenapiexchangeproject3.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.navigation.NavigationView;
import com.skyfishjy.library.RippleBackground;

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

public class MainActivity extends AppCompatActivity {
    String str;
    //국가선택
    Context context;
    EditText et_nation_title;
    natioDialongSelctionAdapter nation;
    ArrayList<nationVO> nationSelect = new ArrayList<>();
    ArrayList<nationVO> nationSelectCopy = new ArrayList<>();


    //카카오톡 이미지 및 닉네임
    static long nicknumber; //카카오톡 고유넘버
    String kNickname;
    String kNickimage;
    String logoutNickname;
    String doLogout;
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
    ArrayList<Itemlist> datasCopy =new ArrayList<>();




    static ArrayList<kakaoVO> kakaodatas  = new ArrayList<>();
    static MainRecylcerAdapter recyclerAdapter;

    //새로고침
    public static SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true); //벡터이미지 관련코드
        setContentView(R.layout.activity_main);
        context=this;


        //Log.d("nicknumber",nicknumber+"");//0나오는걸 확인
        if(nicknumber!=0){
            nicknumber=kakaodatas.get(0).getNicknumber();
            Log.d("닉넘버",kakaodatas.get(0).getNicknumber()+"");
        }

        /////////////////////volley 라이브러리 생성//////////////////////////////
        if(AddHelper.requestQueue == null) {
            AddHelper.requestQueue = Volley.newRequestQueue(this);
        }

        //이걸 주석으로 해도 I/Choreographer: Skipped 39 frames!  The application may be doing too much work on its main thread.

        Thread t = new Thread(){
            @Override
            public void run() {
                super.run();
                MyThread();
            }
        };
        t.start();
        try {
            t.join();
            Log.d("threadsss","sssssss");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("threadsss","string");




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
        toolbar.setTitle("환율");
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle); //화살표모양 누르면 navi소환
        actionBarDrawerToggle.syncState(); //화살표모양->삼선모양



        //////////////////////////////////카카오톡 로그인 네비게이션뷰 연결/////////////////////////////////////////////////////


        ///////퍼미션-전화번호부------여기도 아니네 퍼미션 위치 모아서 한번에 해야하는데 어디서 해애햐지??////////

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){ //sdk version이 마시멜로우보다 높은 겨우
            int chekcedPersmission =checkSelfPermission(Manifest.permission.READ_CONTACTS);
            if(chekcedPersmission== PackageManager.PERMISSION_DENIED){ //처음에 거부되어있다면
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},431); //허가여부 다이얼로그 확인.
            }
        }


        Dataload();//datas가 나오겠지. 스태틱이라서 꼬인게 아닐까 싶기도함. //datacopy가 나옴.
        //Log.d("image3",datasCopy.get(0).getNationimage()+""); //여기선 값이 나오는데 -> 저장한 값은 잘 나왔음을 의미함.

        //UpdateDataLoad();
        //Log.d("image4",datasCopy.get(0).getNationimage()+""); //여기선 0이 나옴. 그러니까 에러가 남.

        //리싸이클러 연결하기 - onCreate에서만 보임.
        recyclerView = findViewById(R.id.recyclerview);
        recyclerAdapter = new MainRecylcerAdapter(datasCopy,this);
        recyclerView.setAdapter(recyclerAdapter);

//        Log.d("image1",datasCopy.get(0).getNationimage()+"");
//        Log.d("image2",R.drawable.a02_australia+"");


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
                    case R.id.rubbish:
                        Intent intent2 = new Intent(MainActivity.this,NoteRubbish.class); //여기로 들어가면 로그인 하도록 하고 싶은뎅.
                        startActivity(intent2);
                        drawerLayout.closeDrawer(navigationView); //클릭 후 네비뷰 닫힘
                        break;
                    case R.id.newsSearch:
                        Intent intent3 = new Intent(MainActivity.this,NewsPaper.class); //여기로 들어가면 로그인 하도록 하고 싶은뎅.
                        startActivity(intent3);
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
        //UpdateDataLoad(); //와 이게 여기서 오류나는거였네 데이터 null 계속 나오는게.. 여기서 이미지가 안나오는거였네.. 아 찾았다 드디어 찾았네.

    }///////////////////////////////////////////////////onCreate//////////////////////////////////////////////////////////////////



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 431:
                if(grantResults.length>0){ //0보다 커야 main에서 오류뜨지 않고 잘 크게 됨. 여기서 다 쓸어넣자.
                    if(grantResults[0]==PackageManager.PERMISSION_DENIED){
                        Toast.makeText(this, "주소록 기능 사용 제한", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this, "주소록 사용 가능", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }

        }
    }

    public void MyThread(){
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



    //oncreatemenu를 지우고 onpreapre+invaild 쓰면 바뀔때마다 적용됨.
    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater(); //메뉴에서 가져올 인플레이터이므로 layout이 아닌 Menu
        inflater.inflate(R.menu.loginicon, menu);
        if(nicknumber!=0.0){
            menu.getItem(0).setTitle("LOGOUT");
        }
        return super.onPrepareOptionsMenu(menu);

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


    //카카오톡에서 보낸 정보 받는 곳.
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
                    doLogout=data.getStringExtra("dologout");

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

                    kakaodatas.add(0,new kakaoVO(kNickname,kNickimage,logincheckin,logoutNickname,logoutImage,logoutcheckout,nicknumber));
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

    public void DataSaveRubbish(){
        try {
            File file = new File(getFilesDir(),"rubbish");
            FileOutputStream fos  = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(NoteRubbish.rubbishlist); //현재의 datas를 저장.
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        UpdateDataLoad();
    }

    public void search(String text){
        //adapter에 추가한 list를 기반으로하고 이를 복제한 list로 바꾸면서 보여지는 것.
        nationSelect.clear();
        nation.notifyDataSetChanged();

        if(text.length()==0){
            nationSelect.addAll(nationSelectCopy);
        }else{

            for(int i=0; i<nationSelectCopy.size(); i++){
                String choName = HangulUtils.getHangulInitialSound(nationSelectCopy.get(i).getTv(), text); //제목
                //문자의 text가 하나라도 써져있다면 이에 해당하는 itemlist를 추가.
                if(choName.indexOf(text)>=0){
                    nationSelect.add(nationSelectCopy.get(i));
                }
//                if(notelistcopy.get(i).getNoteTitle().contains(text) || notelistcopy.get(i).getNoteContent().contains(text)){
//                    testlistcopy.add(notelistcopy.get(i)); //계속 오류나는 이유가 필터를 써서 notelist의 개수가 줄어드는데 그걸 notelist에 number에 intent하려고하니 오류남.
//                }

            }
        }

        nation.notifyDataSetChanged();
    }
    //////////////////////////////FAB - 국가환율추가로 이동/////////////////////////////////////////////
    public void ClikNationSelction(View view) {
        nationSelect.clear();
        nationSelectCopy.clear();

        for(int i=0; i<22; i++){
            nationSelect.add(new nationVO(JsonExchangeRate.cur_nm[i], JsonExchangeRate.iv_nationflag[i]));
        }//정보겟
        nationSelectCopy.addAll(nationSelect);

        fab.close(true); //자동으로 닫히도록.
//        Intent intent = new Intent(this, NationSelectNation.class);
//        startActivity(intent);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view2 = inflater.inflate(R.layout.nation_dialog, null); //view2 레이아웃이 따로 발동 중

        et_nation_title = view2.findViewById(R.id.et_nation_title);
        et_nation_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = et_nation_title.getText().toString();
                search(str);
            }
        });



        builder.setView(view2);

        //다이얼로그의 커스텀뷰를 listview와 연결
        final ListView nationlistview = view2.findViewById(R.id.listviewSelct);
        final AlertDialog dialog = builder.create();



        nation = new natioDialongSelctionAdapter(nationSelect,context);
        nationlistview.setAdapter(nation);
        nationlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int num=nationSelectCopy.indexOf(nation.getItem(position));
                num=num%22;

                datasCopy.add(new Itemlist(JsonExchangeRate.cur_nm[num], JsonExchangeRate.cur_unit[num], JsonExchangeRate.kftc_deal_bas_r[num], JsonExchangeRate.iv_nationflag[num]
                        , GlobalTime.newstime2, GlobalTime.dateFormat2[num].format(GlobalTime.date2),
                        WeatherJSon.todayC1[num], WeatherJSon.todayweather[num], GlobalTime.timedifferent[num]

                ));
                Log.d("kekekjeDone",JsonExchangeRate.cur_nm[num]+"");//여긴 국가이름이 잘 나오는데.
                Toast.makeText(MainActivity.this, JsonExchangeRate.cur_nm[num]+" 추가 완료", Toast.LENGTH_SHORT).show();

                for(int k=0; k<datasCopy.size(); k++){ //1번칸일때
                        for(int j=0; j<k; j++){ //0번 칸
                            if(datasCopy.get(j).getCur_nm().equals(datasCopy.get(k).getCur_nm())){ //0번과 1번칸을 비교
                                Toast.makeText(context, "이미 추가되어있습니다.", Toast.LENGTH_SHORT).show();
                                datasCopy.remove(k); //add했을때 맨 마지막에 추가된 것을 제거.
                                k--; //제거하고 한발짝 뒤로 옮김.
                            }
                        }
                    }

                DataSave();
               recyclerAdapter.notifyDataSetChanged();
               dialog.dismiss();
            }
        });


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();


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


    //이게 뭡니까 왜 저장이 안됩니까 ㅠㅠ
    @Override
    protected void onStop() {
        super.onStop();
        DataSave();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DataSave();
    }

    //load까지 다 했었었네..
    public void Dataload(){
        try {
            File file = new File(getFilesDir(),"t.tmp");
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            datasCopy = (ArrayList<Itemlist>) ois.readObject();  //앱 꺼도 새롭게 저장한 파일이 있음을 파악함.
            ois.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void DataSave(){
        try {
            File file = new File(getFilesDir(),"t.tmp");
            FileOutputStream  fos  = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(datasCopy); //현재의 datas를 저장.
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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



    /////////////////////////////////////////////////////////새로고침기능//////////////////////////////////////////////////////////////
    public void swiperefresh(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(datasCopy.size()==0) return;
                UpdateDataLoad();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }//refresh

    public void UpdateDataLoad(){

        if(datasCopy.size()==0){
            return; //0이여도 할게 없으니까 바로 종료해도 문제 없을 듯
        }else{
            for(int i=0; i<datasCopy.size(); i++){
                int k;
                Log.d("sizesizesize",datasCopy.size()+"");
                Log.d("sizesizeimage",datasCopy.get(0).getNationimage()+"");

                switch (datasCopy.get(i).getNationimage()){ //여기서는 준비중입니다임.
                    case R.drawable.a01_arabemirates:
                        Log.d("sizesize2",datasCopy.get(0).getNationimage()+""); //여기까진 값이 나오는데.
                        k=0; //숫자를 직접 입력해서 넣으면 OOM이 발생해서 오류가 뜬다.
                        //시계는 같이 써야 작용한다.SearchNaverJson1를 써야 뉴스의 글씨가 나타난다!!!<----------매우중요.. ㅋㅋ 그럼 왜  static쓴거지 ㅠㅠ
                        Log.d("sizesize2IK",i+" "+k+" "); //여기까진 값이 나오는데.
                        SetData(i,k);

                        break;
                    case R.drawable.a02_australia:
                        Log.d("sizesize3",datasCopy.get(0).getNationimage()+"");
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
             recyclerAdapter.notifyDataSetChanged(); //이건 필요함.  근데 이거 onCreate에서는 이게 먼저 나오면 오류 아니냐 일단은 주석처리함 이거 다시 풀어야함
        }

    }

    public void SetData(int i, int k){

        datasCopy.set(i,new Itemlist(JsonExchangeRate.cur_nm[k], JsonExchangeRate.cur_unit[k], JsonExchangeRate.kftc_deal_bas_r[k], JsonExchangeRate.iv_nationflag[k]
                , GlobalTime.newstime2, GlobalTime.dateFormat2[k].format(GlobalTime.date2),
                WeatherJSon.todayC1[k], WeatherJSon.todayweather[k], GlobalTime.timedifferent[k]
        ));

        Log.d("kekekje",JsonExchangeRate.cur_nm[0]+""); //이게 null값이 나오네 여기서 null 안나오게 만들어야 자동업데이트가 가능함.

    }


    //카카오톡 해시값
    private void getHashKey(){
        try {                                                        // 패키지이름을 입력해줍니다.  //여기 패키지명 제대로 바뀌었는지 확인 바람.
            PackageInfo info = getPackageManager().getPackageInfo("exchange.example.newopenapiexchangeproject3", PackageManager.GET_SIGNATURES);
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

        //카카오톡 키해시 구글 플레이 버전
        byte[] sha1 = {
                (byte)0xA0,(byte)0xD6,(byte)0x4F,(byte)0xDA,(byte)0x02,(byte)0xDF,(byte)0xAE,
                (byte)0xFD,(byte)0xE3,(byte)0x45,(byte)0xEE,(byte)0x6E,(byte)0x78,(byte)0xDA,
                (byte)0x24,(byte)0x27,(byte)0x1D,(byte)0x63,(byte)0xFC,(byte)0x1F
        };
        Log.e("카카오톡 해쉬코드 구글플레이", Base64.encodeToString(sha1, Base64.NO_WRAP));


    }




}
