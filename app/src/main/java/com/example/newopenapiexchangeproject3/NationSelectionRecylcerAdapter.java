package com.example.newopenapiexchangeproject3;

import android.content.Context;
import android.media.audiofx.AudioEffect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;

import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.newopenapiexchangeproject3.NaverSearchApi.SearchApi;
import com.google.android.material.internal.NavigationMenuItemView;
import com.google.gson.Gson;

import net.igenius.customcheckbox.CustomCheckBox;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import static com.example.newopenapiexchangeproject3.ExchangeJSON.cur_nm;
import static com.example.newopenapiexchangeproject3.ExchangeJSON.cur_unit;
import static com.example.newopenapiexchangeproject3.ExchangeJSON.iv_nationflag;
import static com.example.newopenapiexchangeproject3.ExchangeJSON.kftc_deal_bas_r;
import static com.example.newopenapiexchangeproject3.GlobalTime.date2;
import static com.example.newopenapiexchangeproject3.GlobalTime.dateFormat2;
import static com.example.newopenapiexchangeproject3.GlobalTime.newstime2;

import static com.example.newopenapiexchangeproject3.GlobalTime.timedifferent;
import static com.example.newopenapiexchangeproject3.MainActivity.datas;
import static com.example.newopenapiexchangeproject3.SearchNaverJson1.newsContent;
import static com.example.newopenapiexchangeproject3.SearchNaverJson1.newsUrl;
import static com.example.newopenapiexchangeproject3.SearchNaverJson1.newstitle;
import static com.example.newopenapiexchangeproject3.WeatherJSon.todayC;
import static com.example.newopenapiexchangeproject3.WeatherJSon.todayC1;
import static com.example.newopenapiexchangeproject3.WeatherJSon.todayCity;
import static com.example.newopenapiexchangeproject3.WeatherJSon.todayIcon;
import static com.example.newopenapiexchangeproject3.WeatherJSon.todayNation;
import static com.example.newopenapiexchangeproject3.WeatherJSon.todayweather;

public class NationSelectionRecylcerAdapter extends RecyclerView.Adapter {

    ArrayList<Itemlist_nation> NSdata;
    static Context context;
    //public static int a;
    static String[] newstime = new String[22];
    static TimeZone[] timeZone = new TimeZone[22];
    static DateFormat[] dateFormat = new DateFormat[22];
    static Date[] date = new Date[22];
    int k;
//    static String[][] newstitle = new String[22][3];
//    static String[][] newsUrl = new String[22][3];
//    static String[][] newsContent = new String[22][3];

    public NationSelectionRecylcerAdapter(ArrayList<Itemlist_nation> datas,Context context) {
        this.NSdata = datas;
        this.context = context;

    }


    ////////////////////////체크박스 중복출현을 막는 코드///////////
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    ///////////////////////////////////////////////////////////////////

    //연결하는 곳
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        for(int i=0; i<22; i++){
            date[i] = new Date();
            dateFormat[i] = new SimpleDateFormat("yy-MM-dd a hh:mm"); //그 나라 시간.
            timeZone[i]  = TimeZone.getTimeZone("Asia/Dubai"); //그 나라 표준시
            dateFormat[i].setTimeZone(timeZone[i] );
            SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd a hh:mm ");
            newstime[i] = sdf.format(new Date());
        }

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemview = layoutInflater.inflate(R.layout.nation_selction_item_list,parent,false);
        itemview.findViewById(R.id.cardviewlayoutroot_second); //객체로 만들었기 때문에 findviewbyid가 가능해짐.
        VH2 vh2 = new VH2(itemview);
        return vh2;


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        //holder.setIsRecyclable(false);     //계속 되는 오류로 일단 추가해봄.
        VH2 vh2 = (VH2)holder;
        Itemlist_nation itemlist_nation  =  NSdata.get(position);
        vh2.tv_NationSeleciton.setText(itemlist_nation.getTv());
        Glide.with(context).load(itemlist_nation.getIv()).into(vh2.iv_NationSeleciton); //이미지는 글라이드로 처리
    }

    @Override
    public int getItemCount() {
        return NSdata.size();
    }

    class VH2 extends RecyclerView.ViewHolder{

        ImageView iv_NationSeleciton;                    //나라이미지
        TextView tv_NationSeleciton;                    //국가이름
        LinearLayout Linear_cardview_content_NS;
        CardView cardviewlayoutroot_second;
        RelativeLayout Relativelayout_cardview_title;


        public VH2(@NonNull View itemView) {
            super(itemView);

            iv_NationSeleciton = itemView.findViewById(R.id.iv_nation_selection);  //각 나라 이미지 추가해야함.
            tv_NationSeleciton = itemView.findViewById(R.id.tv_nation_selection);
            Linear_cardview_content_NS = itemView.findViewById(R.id.Linear_cardview_content_NS);
            cardviewlayoutroot_second = itemView.findViewById(R.id.cardviewlayoutroot_second);
            Relativelayout_cardview_title = itemView.findViewById(R.id.Relativelayout_cardview_title);



            Linear_cardview_content_NS.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int a = getAdapterPosition(); //0이라는 숫자가 나옴.

                    a=a%22;                   //이렇게 하면 ArrayoutofBounds을 잘 막을 수 있음.
                    if(a!=RecyclerView.NO_POSITION){  //이 코드를 추가해야 recyclerview ArrayoutofBounds의 발생을 막을 수 있다.
                        for(int i=0; i<NSdata.size(); i++) {
                            if (a == i) {
                                Toast.makeText(context, a+"성공적으로 추가하였습니다.", Toast.LENGTH_SHORT).show();
                                Animation anim = AnimationUtils.loadAnimation(context,R.anim.bounce);
                                cardviewlayoutroot_second.startAnimation(anim);

                                datas.add(new Itemlist(cur_nm[a],cur_unit[a],kftc_deal_bas_r[a],iv_nationflag[a]
                                       ,newstime2,dateFormat2[a].format(date2),
                                        todayC1[a],todayweather[a],timedifferent[a]
                                ));
                                MainActivity.recyclerAdapter.notifyDataSetChanged();
                                DataSave();
                            }
                        }
                    }

                }
            });
        }

    }//VH2

    public static void DataSave(){
        try {
            File file = new File(context.getFilesDir(),"t.tmp");
            FileOutputStream fos  = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(datas); //현재의 datas를 저장.
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
