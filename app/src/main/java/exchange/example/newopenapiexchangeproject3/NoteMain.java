package exchange.example.newopenapiexchangeproject3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.newopenapiexchangeproject3.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.navigation.NavigationView;

import net.igenius.customcheckbox.CustomCheckBox;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import static exchange.example.newopenapiexchangeproject3.KaKaoLoginclass.KAKAOLOGIN;
import static exchange.example.newopenapiexchangeproject3.KaKaoLoginclass.KAKAOLOGOUT;
import static exchange.example.newopenapiexchangeproject3.MainActivity.kakaodatas;
import static exchange.example.newopenapiexchangeproject3.MainActivity.nicknumber;
import static exchange.example.newopenapiexchangeproject3.NoteText.notelist;
import static exchange.example.newopenapiexchangeproject3.NoteText.searchlist;

public class NoteMain extends AppCompatActivity {

    //체크박스
    CheckBox checkbox;
    CustomCheckBox customCheckBox;
    LinearLayout NoteLinearlayout;
    static boolean ischecked =false;

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
        toolbar.setTitle("노트");
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


        /////////////////////////////////////////네비게이션//////////////////////////////////////////////////////////////////
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_note :
                        Intent intent = new Intent(NoteMain.this,NoteMain.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(navigationView); //클 릭 후 네비뷰 닫힘
                        finish(); //자기가 자기꺼 갈 때 중복되면 이상하니까.
                        break;
                    case R.id.updateIcon:
                        Intent intent0 = new Intent(NoteMain.this, UpdateMain.class);
                        startActivity(intent0);
                        drawerLayout.closeDrawer(navigationView); //클릭 후 네비뷰 닫힘
                        break;
                    case R.id.address:
                        Intent intent1 = new Intent(NoteMain.this,Address.class); //여기로 들어가면 로그인 하도록 하고 싶은뎅.
                        startActivity(intent1);
                        drawerLayout.closeDrawer(navigationView); //클릭 후 네비뷰 닫힘
                        break;
                    case R.id.rubbish:
                        Intent intent2 = new Intent(NoteMain.this,NoteRubbish.class); //여기로 들어가면 로그인 하도록 하고 싶은뎅.
                        startActivity(intent2);
                        drawerLayout.closeDrawer(navigationView); //클릭 후 네비뷰 닫힘
                        break;
                    case R.id.newsSearch:
                        Intent intent3 = new Intent(NoteMain.this,NewsPaper.class); //뉴스
                        startActivity(intent3);
                        drawerLayout.closeDrawer(navigationView); //클릭 후 네비뷰 닫힘
                        break;

                }
                return false;
            }
        });


        //로그인, 로그아웃 시 네비게이션뷰의 이미지 및 이름 변경
        MainActivity main = new MainActivity();

        if(kakaodatas.size()==0) return;
            //로그인
            //네비게이션의 헤더뷰를 제어하는 코드
        else if(kakaodatas.get(0).getLogintnumber()==KAKAOLOGIN){
            main.headerView = navigationView.getHeaderView(0);
            main.navUsername = main.headerView.findViewById(R.id.tv_navi_header_name);
            main.navUserimage = main.headerView.findViewById(R.id.iv_header);
            main.navUsername.setText(kakaodatas.get(0).getLoginNickname()+"님"); //유저이름+"님"
            Glide.with(this).load(kakaodatas.get(0).getLoginNickimage()).into(main.navUserimage);
        }

        //로그아웃
        else if(kakaodatas.get(0).getLogoutnumber()==KAKAOLOGOUT){
            main.headerView = navigationView.getHeaderView(0);
            main.navUsername = main.headerView.findViewById(R.id.tv_navi_header_name);
            main.navUserimage = main.headerView.findViewById(R.id.iv_header);
            main.navUsername.setText("Anonymous"+"님"); //유저이름+"님
            Glide.with(this).load(R.drawable.user).into(main.navUserimage);
        }


        noteRecycler = findViewById(R.id.note_recycler);
        noteAdapter = new NoteAdapter(this);
        noteRecycler.setAdapter(noteAdapter);

        nicknumber=kakaodatas.get(0).getNicknumber(); //static 공부 부족, 여기서 다시 한번 써놓으니 인식함.
        //데이터스를 로드.
        if(nicknumber!=0.0){
            Log.d("닉넘버2",nicknumber+"");
           //DB데이터 로드 //쓰레드인데 바로 데이터를 얻으려고 하니. testlist.size가 0이 나옴.---여기가 또 수정해야함(수정완료)
            NoteLoadFromDB(); //여기서 notelist는 완성임. --> 돋보기를 누른다고 한다면

        }else{
            Dataload();   //휴대폰 내부 데이터 로드

        }


        noteAdapter.notifyDataSetChanged();
    }//////////////////////////////////////////////////////////oncreate///////////////////////////////////////////////////

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int n = item.getItemId();
        switch (n){
            case R.id.menu_action:
                Intent intent = new Intent(NoteMain.this,NoteSearching.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
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
        Intent intent = new Intent(this, NationSelectNation.class);
        startActivity(intent);
    }

    public void clickCalculator2(View view) {
        fab.close(true); //자동으로 닫히도록.
        Intent intent = new Intent(this, CalCalculator.class);
        startActivity(intent);
    }


    public void clicktext2(View view) {
        fab.close(true); //자동으로 닫히도록.
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
                            searchlist.add(0,new NoteVO(time, title, content)); //첫 글씨를 쓴 것을 저장하는 만큼 문제 없음
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

}
