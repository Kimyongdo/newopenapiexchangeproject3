package exchange.example.newopenapiexchangeproject3;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.example.newopenapiexchangeproject3.R;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import exchange.example.newopenapiexchangeproject3.FXapi.ExchangeMoney;

import static exchange.example.newopenapiexchangeproject3.MainActivity.CanlenderTime;

public class JsonExchangeRateYear {
     public static ExchangeMoney[] exchangeMonies2;
     //
     static ArrayList<String> kftc_deal_bas_r2=new ArrayList<>();
//    static ArrayList<String> CanlenderTime=new ArrayList<>();



    /////////////////////////////////대량의데이터 정보요청///////////////////////////////////
    public void sendRequest(String dateStr2){

//        int time=0;
//        //Date date2 = new Date();//오늘날짜
//        CanlenderTime.clear();
//        for(int k=0; k<10; k++){
//            Date date2 = new Date(); //오늘날짜
//            date2.setTime(date2.getTime()-time);//time 전날+전날..반복
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//            dateStr2 = sdf.format(date2);
//            CanlenderTime.add(dateStr2);
//            time = time+(1000*60*60*24);
//            Log.d("TAG2223",dateStr2);
//        }


//        Date date = new Date();//오늘날짜
//        date.setTime(date.getTime());
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//        String dateStr = sdf.format(date);

        //공휴일 20201005 입니다.

        String mykey = "2u3tcj729pppDBhULM3oFrRI7iRfkGlQ";
        String url = "https://www.koreaexim.go.kr/site/program/financial/exchangeJSON?"
                +"authkey="+mykey        //인증키
                +"&searchdate="+dateStr2  //날짜               주말에 null 값으로 변화되서 아무것도 나오지 않음.. 흠..... 대책이 필요함.
                +"&data=AP01";           //(성공==AP01)
        StringRequest request = new StringRequest(             //Volley 라이브러리
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.d("TAGad",response); //reponse에 json 내용이 담겨져이 있음.
                        processResponse(response);  //Gson으로 이동
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //println("에러발생"+error.getMessage());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                return params;
            }
        };
        request.setShouldCache(false);
        AddHelper.requestQueue.add(request);
    }//sendRequest

    public void processResponse(String response){
        Gson gson = new Gson();
        exchangeMonies2 = gson.fromJson(response, ExchangeMoney[].class);
        if(exchangeMonies2.length!=0){//json 오후에 끊기기 때문에.
            for(int i=0; i<exchangeMonies2.length; i++){//각 국가
                kftc_deal_bas_r2.add(exchangeMonies2[i].getKftc_deal_bas_r()) ;


            }
        }else{

            for(int k=0; k<exchangeMonies2.length; k++){
                kftc_deal_bas_r2.add("") ;
            }

        }

    }//processResponse
}
