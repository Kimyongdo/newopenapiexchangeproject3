package com.example.newopenapiexchangeproject3.NationIntent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.newopenapiexchangeproject3.ExchangeJSON;
import com.example.newopenapiexchangeproject3.GlobalTime;
import com.example.newopenapiexchangeproject3.MainActivity;
import com.example.newopenapiexchangeproject3.R;
import com.example.newopenapiexchangeproject3.SearchNaverJson1;
import com.example.newopenapiexchangeproject3.WeatherJSon;
import com.example.newopenapiexchangeproject3.newsWebview;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static com.example.newopenapiexchangeproject3.GlobalTime.date2;
import static com.example.newopenapiexchangeproject3.GlobalTime.dateFormat2;
import static com.example.newopenapiexchangeproject3.GlobalTime.newstime2;
import static com.example.newopenapiexchangeproject3.GlobalTime.sdf2;
import static com.example.newopenapiexchangeproject3.GlobalTime.timeZone2;
import static com.example.newopenapiexchangeproject3.GlobalTime.timedifferent;

public class NationIntent extends AppCompatActivity {

    ImageView iv_D_Nationflag;
    TextView tv_D_NationName;
    TextView tv_D_NationMoeny;
    TextView tv_D_NationExchange;
    TextView tv_D_NationGlobalTime;
    TextView tv_D_NationNowTime;
    ImageView iv_D_NationWehaterImage;
    TextView tv_D_NationCountry;
    TextView tv_D_NationCity;
    TextView tv_D_NationTemp;
    TextView tv_newtitle1;
    TextView tv_newtitle2;
    TextView tv_newtitle3;
    TextView tv_D_NationNowTimeDifferent;
    TextView tv_D_WeatherCondition;
    TextView tv_D_WeatherHumidity;
    TextView tv_D_WeatherWind;


    TextView korea;

    int i;
    String NationTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nation_intent);



        Intent intent = getIntent();
        String NationName = intent.getStringExtra("name");
        NationTime = intent.getStringExtra("time");
        i = intent.getIntExtra("i",0);

        //툴바
        Toolbar toolbar;
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(NationName+"  상세정보");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




           //환율
            iv_D_Nationflag = findViewById(R.id.iv_D_Nationflag);
            tv_D_NationName = findViewById(R.id.tv_D_NationName);
            tv_D_NationMoeny = findViewById(R.id.tv_D_NationMoney);
            tv_D_NationExchange = findViewById(R.id.tv_D_NationExchange);

            //시간
            tv_D_NationGlobalTime = findViewById(R.id.tv_D_NationGlobalTime);
            tv_D_NationNowTime = findViewById(R.id.tv_D_NationNowTime);
            tv_D_NationNowTimeDifferent = findViewById(R.id.tv_D_NationNowTimeDifferent);

            //날씨
            iv_D_NationWehaterImage = findViewById(R.id.iv_D_NationWeahterImage);
            tv_D_NationCountry = findViewById(R.id.tv_D_NationWeahterCountry);
            tv_D_NationCity = findViewById(R.id.tv_D_NationWeahterCity);
            tv_D_NationTemp = findViewById(R.id.tv_D_NationWeahterTemp);
             tv_D_WeatherCondition = findViewById(R.id.tv_D_WeatherCondition);
             tv_D_WeatherHumidity = findViewById(R.id.tv_D_WeatherHumidity);
              tv_D_WeatherWind = findViewById(R.id.tv_D_WeatherWind);
              korea = findViewById(R.id.tv_D_NationNowTimeDifferent2);

            //뉴스
            tv_newtitle1 = findViewById(R.id.tv_newstitle1);
            tv_newtitle2 = findViewById(R.id.tv_newstitle2);
            tv_newtitle3 = findViewById(R.id.tv_newstitle3);


        //정보받기
        //환율
        Glide.with(this).load(ExchangeJSON.iv_nationflag[i]).into(iv_D_Nationflag);
        tv_D_NationName.setText(ExchangeJSON.cur_nm[i]);
        tv_D_NationMoeny.setText(ExchangeJSON.cur_unit[i]);
        tv_D_NationExchange.setText(ExchangeJSON.kftc_deal_bas_r[i]);

        //시간
        TimeThread timeThread = new TimeThread();
        timeThread.start();

        tv_D_NationGlobalTime.setText(dateFormat2[i].format(GlobalTime.date2));
        tv_D_NationNowTime.setText(GlobalTime.newstime2);
        tv_D_NationNowTimeDifferent.setText(timedifferent[i]);

        //날씨
        Glide.with(this).load(WeatherJSon.todayIcon[i]).into(iv_D_NationWehaterImage);
        tv_D_NationCountry.setText(WeatherJSon.todayNation[i]);
        tv_D_NationCity.setText(WeatherJSon.todayCity[i]);
        tv_D_NationTemp.setText(WeatherJSon.todayC1[i]);
        tv_D_WeatherCondition.setText(WeatherJSon.todayweather[i]);
        tv_D_WeatherWind.setText(WeatherJSon.todaywind[i]);
        tv_D_WeatherHumidity.setText(WeatherJSon.todayHum[i]);

       //뉴스
        tv_newtitle1.setText(SearchNaverJson1.newstitle[i][0]);
        tv_newtitle2.setText(SearchNaverJson1.newstitle[i][1]);
        tv_newtitle3.setText(SearchNaverJson1.newstitle[i][2]);



        //뉴스 URL
        tv_newtitle1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_newtitle1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String newsurl1 =  SearchNaverJson1.newsUrl[i][0];
                        Intent intent = new Intent(NationIntent.this, newsWebview.class);
                        intent.putExtra("Link",newsurl1);
                        startActivity(intent);
                    }
                });
                tv_newtitle2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String newsurl2 =  SearchNaverJson1.newsUrl[i][1];
                        Intent intent = new Intent(NationIntent.this, newsWebview.class);
                        intent.putExtra("Link",newsurl2);
                        startActivity(intent);
                    }
                });
                tv_newtitle3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String newsurl3 =  SearchNaverJson1.newsUrl[i][2];
                        Intent intent = new Intent(NationIntent.this, newsWebview.class);
                        intent.putExtra("Link",newsurl3);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :{
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }

        return super.onOptionsItemSelected(item);

    }
//
//    @Override
//    public void onBackPressed() {
//        //뒤로가기 누르면 계속 원래 페이지를 보여준다. 그래서 우선 intent를 시킴. 임시방편임.
//        super.onBackPressed();
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
//        finish();
//    }


    @Override
    protected void onPause() {
        super.onPause();
        TimeThread timeThread = new TimeThread();
        timeThread.interrupt(); //이거 좀 더 공부해봐야할듯  stop과 interrupt 상태표시
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TimeThread timeThread = new TimeThread();
        timeThread.interrupt(); //이거 좀 더 공부해봐야할듯  stop과 interrupt 상태표시
    }

    class TimeThread extends Thread{
        @Override
        public void run() {
            while(true){
                date2 = new Date();
                dateFormat2[i] = new SimpleDateFormat("yy-MM-dd a hh:mm:ss"); //그 나라 시간.
                timeZone2[i]  = TimeZone.getTimeZone(NationTime); //그 나라 표준시
                dateFormat2[i].setTimeZone(timeZone2[i]);

                date2= new Date();
                sdf2 = new SimpleDateFormat("yy-MM-dd a hh:mm:ss ");
                newstime2 = sdf2.format(date2);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_D_NationGlobalTime.setText(dateFormat2[i].format(GlobalTime.date2));
                        tv_D_NationNowTime.setText(GlobalTime.newstime2);
                    }
                });

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
