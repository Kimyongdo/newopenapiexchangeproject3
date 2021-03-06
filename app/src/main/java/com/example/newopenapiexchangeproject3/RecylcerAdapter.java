package com.example.newopenapiexchangeproject3;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newopenapiexchangeproject3.NationIntent.NationIntent;
//import com.example.newopenapiexchangeproject3.NationIntent.Auckland;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


//https://stackoverflow.com/questions/35500322/expand-and-collapse-cardview/54156690#54156690 카드뷰 느는건 참고하도록 하자
public class RecylcerAdapter extends RecyclerView.Adapter {

    ArrayList<Itemlist> datas;
    Context context;
    //RecyclerView recyclerView;
    public static int OpenAndClose;  //확대,축소


    public RecylcerAdapter(ArrayList<Itemlist> datas, Context context, RecyclerView recyclerView) {
        this.datas = datas;
        this.context = context;
        //this.recyclerView = recyclerView;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.cardview_recylcer, parent, false);
        itemView.findViewById(R.id.cardviewlayoutroot);
         VH vh = new VH(itemView);
        return vh;




    }

    //새로운 참조변수 - 연결된 XML - datas 연결
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        final VH vh = (VH)holder;
        final Itemlist itemlist = datas.get(position);  //itemlist가 화물칸마다 position으로 연결

        //환율
        vh.flag_name.setText(itemlist.getCur_nm());
        vh.dollar_name.setText(itemlist.getCur_unit());
        vh.dollar_rate.setText(itemlist.getKftc_deal_bas_r());
        Glide.with(context).load(itemlist.getNationimage()).into(vh.iv_NationSeleciton);



        //날씨
        vh.TodayC.setText(itemlist.getTdoayC1());
        vh.WeatherConditon.setText(itemlist.getTodayConditon());
        vh.newtime_diffet.setText(itemlist.getTimedifferent());


        //세계시간설정
        //여기서 세계시간설정하기

        vh.newstime_global.setText(itemlist.getNewstimeglobal());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    //XML 위치 연결
    //여기서 카드뷰의 모양을 결정하고 있음.
    class VH extends RecyclerView.ViewHolder{

        //세계시간설정
        TextView newstime_global;
        TextView newtime_diffet;


        //환율
        ImageView iv_NationSeleciton; //나라이미지
        TextView flag_name; //국가이름
        TextView dollar_name;//달러이름
        TextView dollar_rate;//달러환율

        //날씨 설정
        TextView TodayC;
        TextView WeatherConditon;


        //뷰 설정
        RelativeLayout cardview_title;
        CardView cardviewlayoutroot;


        //기타설정
        // ImageView iv_rotate;         //톱니바퀴모양
        ImageView cardview_remove;   // x 모양
        ImageView iv_expand;

        RelativeLayout Relativelayout_cardview_title;

        //연결하는 곳
        public VH(@NonNull View itemView) {
            super(itemView);

            //세계시간설정
            newstime_global = itemView.findViewById(R.id.cardview_news_dattime_global);


            //환율
            iv_NationSeleciton=itemView.findViewById(R.id.iv_flag);
            flag_name = itemView.findViewById(R.id.tv_nation_name);
            dollar_name = itemView.findViewById(R.id.tv_dollar_name);
            dollar_rate = itemView.findViewById(R.id.tv_dollar_rate);


            //시간
             TodayC = itemView.findViewById(R.id.tv_TodayC);
            WeatherConditon = itemView.findViewById(R.id.tv_WeahterCondtion);
            newtime_diffet = itemView.findViewById(R.id.cardview_news_dattime_different);

            //기타설정
            cardview_remove=itemView.findViewById(R.id.iv_remoce_cancel);
            cardview_title = itemView.findViewById(R.id.Relativelayout_cardview_title);
            cardviewlayoutroot = itemView.findViewById(R.id.cardviewlayoutroot);
            Relativelayout_cardview_title = itemView.findViewById(R.id.Relativelayout_cardview_title);



            Relativelayout_cardview_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int a= getAdapterPosition();
                    SearchNaverJson1 searchNaverJson1 = new SearchNaverJson1(); //여기로 하나로 고정해도 괜찮지 않을까?? 11060916
                    for(int i=0; i<22; i++){
                        switch (datas.get(a).getCur_unit()){ //
                            case "AED"://아랍에미리트 디르함cardview_news_dattime_global
                                searchNaverJson1.searching("아랍에미리트",0);
                                Intent intent0 = new Intent(context, NationIntent.class);
                                intent0.putExtra("i",0);
                                intent0.putExtra("name","Arab");
                                intent0.putExtra("time","Asia/Dubai");
                                context.startActivity(intent0);

                                break;
                            case "AUD"://호주달러
                                searchNaverJson1.searching("호주",1);
                                intent0 = new Intent(context, NationIntent.class);
                                intent0.putExtra("i",1);
                                intent0.putExtra("name","Australia");
                                intent0.putExtra("time","Australia/Canberra");
                                context.startActivity(intent0);
                                break;
                            case "BHD"://바레인
                                searchNaverJson1.searching("바레인",2);
                                intent0 = new Intent(context, NationIntent.class);
                                intent0.putExtra("i",2);
                                intent0.putExtra("name","Bahrain");
                                intent0.putExtra("time","Asia/Bahrain");
                                context.startActivity(intent0);
                                break;
                            case "CAD"://캐나다
                                searchNaverJson1.searching("캐나다",3);
                                intent0 = new Intent(context, NationIntent.class);
                                intent0.putExtra("i",3);
                                intent0.putExtra("name","Canada");
                                intent0.putExtra("time","America/Toronto");
                                context.startActivity(intent0);
                                break;
                            case "CHF"://스위스
                                searchNaverJson1.searching("스위스",4);
                                intent0 = new Intent(context, NationIntent.class);
                                intent0.putExtra("i",4);
                                intent0.putExtra("name","Swiss");
                                intent0.putExtra("time","Europe/Zurich");
                                context.startActivity(intent0);
                                break;
                            case "CNH"://중국
                                searchNaverJson1.searching("중국",5);
                                intent0 = new Intent(context, NationIntent.class);
                                intent0.putExtra("i",5);
                                intent0.putExtra("name","China");
                                intent0.putExtra("time","Asia/Shanghai");
                                context.startActivity(intent0);
                                break;
                            case "DKK"://덴마크
                                searchNaverJson1.searching("덴마크",6);
                                 intent0 = new Intent(context, NationIntent.class);
                                intent0.putExtra("i",6);
                                intent0.putExtra("name","Denmark");
                                intent0.putExtra("time","Europe/Copenhagen");
                                context.startActivity(intent0);
                                break;
                            case "EUR"://유로
                                searchNaverJson1.searching("유럽",7);
                                intent0 = new Intent(context, NationIntent.class);
                                intent0.putExtra("i",7);
                                intent0.putExtra("name","Europe");
                                intent0.putExtra("time","Europe/Brussels");
                                context.startActivity(intent0);
                                break;
                            case "GBP"://영국
                                searchNaverJson1.searching("영국",8);
                                intent0 = new Intent(context, NationIntent.class);
                                intent0.putExtra("i",8);
                                intent0.putExtra("name","England");
                                intent0.putExtra("time","Europe/London");
                                context.startActivity(intent0);
                                break;
                            case "HKD"://홍콩
                                searchNaverJson1.searching("홍콩",9);
                                intent0 = new Intent(context, NationIntent.class);
                                intent0.putExtra("i",9);
                                intent0.putExtra("name","HongKong");
                                intent0.putExtra("time","Asia/Hong_Kong");
                                context.startActivity(intent0);
                                break;
                            case "IDR(100)"://인도네시아
                                searchNaverJson1.searching("인도네시아",10);
                                intent0 = new Intent(context, NationIntent.class);
                                intent0.putExtra("i",10);
                                intent0.putExtra("name","Indonesia");
                                intent0.putExtra("time","Asia/Jakarta");
                                context.startActivity(intent0);
                                break;
                            case "JPY(100)"://일본
                                searchNaverJson1.searching("일본",11);
                                intent0 = new Intent(context, NationIntent.class);
                                intent0.putExtra("i",11);
                                intent0.putExtra("name","Japan");
                                intent0.putExtra("time","Asia/Tokyo");
                                context.startActivity(intent0);
                                break;
                            case "KRW"://한국
                                searchNaverJson1.searching("한국",12);
                                intent0 = new Intent(context, NationIntent.class);
                                intent0.putExtra("i",12);
                                intent0.putExtra("name","Korea");
                                intent0.putExtra("time","Asia/Seoul");
                                context.startActivity(intent0);
                                break;
                            case "KWD"://쿠웨이트
                                searchNaverJson1.searching("쿠웨이트",13);
                                intent0 = new Intent(context, NationIntent.class);
                                intent0.putExtra("i",13);
                                intent0.putExtra("name","Kuwait");
                                intent0.putExtra("time","Asia/Kuwait");
                                context.startActivity(intent0);
                                break;
                            case "MYR"://말레이지아
                                searchNaverJson1.searching("말레이지아",14);
                                intent0 = new Intent(context, NationIntent.class);
                                intent0.putExtra("i",1);
                                intent0.putExtra("name","Malaysia");
                                intent0.putExtra("time","Asia/Kuala_Lumpur");
                                context.startActivity(intent0);
                                break;
                            case "NOK"://노르웨이
                                searchNaverJson1.searching("노르웨이",15);
                                intent0 = new Intent(context, NationIntent.class);
                                intent0.putExtra("i",15);
                                intent0.putExtra("name","Norway");
                                intent0.putExtra("time","Europe/Oslo");
                                context.startActivity(intent0);
                                break;
                            case "NZD"://뉴질랜드
                                searchNaverJson1.searching("뉴질랜드",16);
                                intent0 = new Intent(context, NationIntent.class);
                                intent0.putExtra("i",16);
                                intent0.putExtra("name","New Zealand");
                                intent0.putExtra("time","Pacific/Auckland");
                                context.startActivity(intent0);
                                break;
                            case "SAR"://사우디
                                searchNaverJson1.searching("사우디",17);
                                intent0 = new Intent(context, NationIntent.class);
                                intent0.putExtra("i",17);
                                intent0.putExtra("name","Saudi");
                                intent0.putExtra("time","Asia/Riyadh");
                                context.startActivity(intent0);
                                break;
                            case "SEK"://스웨덴
                                searchNaverJson1.searching("스웨덴",18);
                                intent0 = new Intent(context, NationIntent.class);
                                intent0.putExtra("i",18);
                                intent0.putExtra("name","Sweden");
                                intent0.putExtra("time","Europe/Stockholm");
                                context.startActivity(intent0);
                                break;
                            case "SGD"://싱가포르
                                searchNaverJson1.searching("싱가포르",19);
                                intent0 = new Intent(context, NationIntent.class);
                                intent0.putExtra("i",19);
                                intent0.putExtra("name","Singapore");
                                intent0.putExtra("time","Asia/Singapore");
                                context.startActivity(intent0);
                                break;
                            case "THB"://태국
                                searchNaverJson1.searching("태국",20);
                                intent0 = new Intent(context, NationIntent.class);
                                intent0.putExtra("i",20);
                                intent0.putExtra("name","Thailand");
                                intent0.putExtra("time","Asia/Bangkok");
                                context.startActivity(intent0);
                                break;
                            case "USD"://미국
                                searchNaverJson1.searching("미국",21);
                                intent0 = new Intent(context, NationIntent.class);
                                intent0.putExtra("i",21);
                                intent0.putExtra("name","USA");
                                intent0.putExtra("time","America/New_York");
                                context.startActivity(intent0);
                                break;

                        }
                    }

                }
            });


            //////////////////////////////카드뷰 x버튼을 누르면 제거되도록 만드는 기능//////////////////////////////
            cardview_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getLayoutPosition();
                    if(position!=RecyclerView.NO_POSITION){
                        if(datas.size()==0) return;
                        datas.remove(position);
                        notifyItemRemoved(position);
                        DataSave();
                    }

                }
            });
        }//VH
    }



    public void DataSave(){
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
