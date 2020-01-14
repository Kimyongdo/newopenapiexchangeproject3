package exchange.example.newopenapiexchangeproject3;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.newopenapiexchangeproject3.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.EntryXComparator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.TimeZone;

import static exchange.example.newopenapiexchangeproject3.GlobalTime.date2;
import static exchange.example.newopenapiexchangeproject3.GlobalTime.dateFormat2;
import static exchange.example.newopenapiexchangeproject3.GlobalTime.newstime2;
import static exchange.example.newopenapiexchangeproject3.GlobalTime.sdf2;
import static exchange.example.newopenapiexchangeproject3.GlobalTime.timeSubstract;
import static exchange.example.newopenapiexchangeproject3.GlobalTime.timeZone2;

public class NationIntent extends NationIntentDemoBase implements SeekBar.OnSeekBarChangeListener,
        OnChartValueSelectedListener {

    TextView tvExhcangeName;

    ImageView iv_D_Nationflag;
    TextView tv_D_NationName;
    TextView tv_D_NationMoeny;
    TextView tv_D_NationExchange;
    TextView tv_D_NationGlobalTime;
    TextView tv_D_NationNowTime;
    TextView tv_D_timedifferentSubtract;

    ImageView iv_D_NationWehaterImage;
    TextView tv_D_NationCountry;
    TextView tv_D_NationCity;
    TextView tv_D_NationTemp;
    TextView tv_newtitle1;
    TextView tv_newtitle2;
    TextView tv_newtitle3;

    TextView tv_D_WeatherCondition;
    TextView tv_D_WeatherHumidity;
    TextView tv_D_WeatherWind;

    int i;
    String NationTime;
    String NationName;

    //차트
    private LineChart chart;
    private SeekBar seekBarX, seekBarY;
//    private TextView tvX, tvY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nation_intent);

        //MainRecyclerAdapter보낸 인텐트 ---->인텐트받기.
        Intent intent = getIntent();
        NationName = intent.getStringExtra("name");
        NationTime = intent.getStringExtra("time");
        i = intent.getIntExtra("i",0);



//        tvX = findViewById(R.id.tvXMax);
//        tvY = findViewById(R.id.tvYMax);

        seekBarX = findViewById(R.id.seekBar1);
        seekBarY = findViewById(R.id.seekBar2);

        seekBarY.setOnSeekBarChangeListener(this);
        seekBarX.setOnSeekBarChangeListener(this);

        chart = findViewById(R.id.chart);
        chart.setOnChartValueSelectedListener(this);
        chart.setDrawGridBackground(false);

        // no description text
        chart.getDescription().setEnabled(false);

        // enable touch gestures
        chart.setTouchEnabled(true);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(true);

        // set an alternative background color
        // chart.setBackgroundColor(Color.GRAY);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        //클릭했을때 차트의 숫자 표기
        NationIntentTableView mv = new NationIntentTableView(this, R.layout.custom_marker_view);
        mv.setChartView(chart); // For bounds control
        chart.setMarker(mv); // Set the marker to the chart

        XAxis xl = chart.getXAxis();
        xl.setAvoidFirstLastClipping(true);
        xl.setAxisMinimum(0f);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setInverted(true);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);

        // add data
        //초기 데이터 위치
        seekBarX.setProgress(10);
        seekBarY.setProgress(10);

        // // restrain the maximum scale-out factor
        // chart.setScaleMinima(3f, 3f);
        //
        // // center the view to a specific position inside the chart
        // chart.centerViewPort(10, 50);

        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);

        // don't forget to refresh the drawing
        chart.invalidate();






        //툴바
        Toolbar toolbar;
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(NationName+"  상세정보");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView tvTime;
        TextView tvWeather;
        TextView newsNote;

        //폰트변경
        AssetManager assetManager = getAssets();
        Typeface typeface = Typeface.createFromAsset(assetManager,"Fonts/lottemart.ttf");

        tvExhcangeName = findViewById(R.id.exchagne_note);
        tvTime = findViewById(R.id.timenote);
        tvWeather = findViewById(R.id.weather_note);
        newsNote = findViewById(R.id.new_note);
        tvExhcangeName.setTypeface(typeface);
        tvTime.setTypeface(typeface);
        tvWeather.setTypeface(typeface);
        newsNote.setTypeface(typeface);

           //환율
            iv_D_Nationflag = findViewById(R.id.iv_D_Nationflag);
            tv_D_NationName = findViewById(R.id.tv_D_NationName);
            tv_D_NationMoeny = findViewById(R.id.tv_D_NationMoney);
            tv_D_NationExchange = findViewById(R.id.tv_D_NationExchange);

            //시간
            tv_D_NationGlobalTime = findViewById(R.id.tv_D_NationGlobalTime);
            tv_D_NationNowTime = findViewById(R.id.tv_D_NationNowTime);
            tv_D_timedifferentSubtract = findViewById(R.id.tv_D_timesubstract);

            //날씨
            iv_D_NationWehaterImage = findViewById(R.id.iv_D_NationWeahterImage);
            tv_D_NationCountry = findViewById(R.id.tv_D_NationWeahterCountry);
            tv_D_NationCity = findViewById(R.id.tv_D_NationWeahterCity);
            tv_D_NationTemp = findViewById(R.id.tv_D_NationWeahterTemp);
             tv_D_WeatherCondition = findViewById(R.id.tv_D_WeatherCondition);
             tv_D_WeatherHumidity = findViewById(R.id.tv_D_WeatherHumidity);
              tv_D_WeatherWind = findViewById(R.id.tv_D_WeatherWind);


            //뉴스
            tv_newtitle1 = findViewById(R.id.tv_newstitle1);
            tv_newtitle2 = findViewById(R.id.tv_newstitle2);
            tv_newtitle3 = findViewById(R.id.tv_newstitle3);


        //정보받기
        //환율
        Glide.with(this).load(JsonExchangeRate.iv_nationflag.get(i)).into(iv_D_Nationflag);
        tv_D_NationName.setText(JsonExchangeRate.cur_nm.get(i));
        tv_D_NationName.setTypeface(typeface);
        tv_D_NationMoeny.setText(JsonExchangeRate.cur_unit.get(i));
        tv_D_NationMoeny.setTypeface(typeface);
        tv_D_NationExchange.setText(JsonExchangeRate.kftc_deal_bas_r.get(i));
        tv_D_NationExchange.setTypeface(typeface);

        //시간
        TimeThread timeThread = new TimeThread();
        timeThread.start();

        tv_D_NationGlobalTime.setText(dateFormat2[i].format(date2));
        tv_D_NationGlobalTime.setTypeface(typeface);
        tv_D_NationNowTime.setText(newstime2);
        tv_D_NationNowTime.setTypeface(typeface);
        tv_D_timedifferentSubtract.setTypeface(typeface);
        tv_D_timedifferentSubtract.setText(timeSubstract[i]);


        //날씨
        Glide.with(this).load(WeatherJSonArray.todayIcon[i]).into(iv_D_NationWehaterImage);
        tv_D_NationCountry.setText(WeatherJSonArray.todayNation[i]);
        tv_D_NationCountry.setTypeface(typeface);
        tv_D_NationCity.setText(WeatherJSonArray.todayCity[i]);
        tv_D_NationCity.setTypeface(typeface);
        tv_D_NationTemp.setText(WeatherJSonArray.todayC1[i]);
        tv_D_WeatherCondition.setText(WeatherJSonArray.todayweather[i]);
        tv_D_WeatherWind.setText(WeatherJSonArray.todaywind[i]);
        tv_D_WeatherHumidity.setText(WeatherJSonArray.todayHum[i]);

       //뉴스
        tv_newtitle1.setText(SearchNaverJson1.newstitle[i][0]);
        tv_newtitle1.setTypeface(typeface);
        tv_newtitle2.setText(SearchNaverJson1.newstitle[i][1]);
        tv_newtitle2.setTypeface(typeface);
        tv_newtitle3.setText(SearchNaverJson1.newstitle[i][2]);
        tv_newtitle3.setTypeface(typeface);


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

    @Override
    protected void onPause() {
        super.onPause();
        TimeThread timeThread = new TimeThread();
        timeThread.interrupt();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TimeThread timeThread = new TimeThread();
        timeThread.interrupt();
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

                //1초마다 보이도록
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    //////////////////////////////////////////데이터 테이블  라이브러리//////////////////////////////////////////////////
    private void setData(int count, float range) {

        ArrayList<Entry> entries = new ArrayList<>();

        //여기서 나온 데이터 값이 차트에 찍힌다,
        //count : 기준날짜부터 현재날짜
        //x축 : 어디기준 날짜
        //y축 : 그떄의 환율가격


        for (int i = 0; i < count; i++) {
            float xVal = (float) (Math.random() * range);//그때의 날짜
            float yVal = (float) (Math.random() * range);//그때의 환율
            entries.add(new Entry(xVal, yVal));
        }

        // sort by x-value
        Collections.sort(entries, new EntryXComparator());

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(entries, "DataSet 1");

        set1.setLineWidth(1.5f);
        set1.setCircleRadius(4f);

        // create a data object with the data sets
        LineData data = new LineData(set1);

        // set data
        chart.setData(data);
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

//        tvX.setText(String.valueOf(seekBarX.getProgress()));
//        tvY.setText(String.valueOf(seekBarY.getProgress()));

        setData(seekBarX.getProgress(), seekBarY.getProgress());

        // redraw
        chart.invalidate();
    }

    @Override
    protected void saveToGallery() {
        saveToGallery(chart, "InvertedLineChartActivity");
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
    }
    @Override
    public void onNothingSelected() {}

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}
}

