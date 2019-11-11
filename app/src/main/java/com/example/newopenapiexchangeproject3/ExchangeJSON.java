package com.example.newopenapiexchangeproject3;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.example.newopenapiexchangeproject3.FXapi.ExchangeMoney;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExchangeJSON {
  public  static ExchangeMoney[] exchangeMonies;
    public  static String[] cur_nm = new String[22];
    public  static String[] cur_unit = new String[22];
    public  static   String[] kftc_deal_bas_r = new String[22];
    public  static  int[] iv_nationflag = new int[22];
    /////////////////////////////////대량의데이터 정보요청///////////////////////////////////
    public void sendRequest(){

        Date date = new Date();
//        date.setTime(date.getTime()-(1000*60*60*24));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateStr = sdf.format(date); //null값 아직 해결 안 함.

        String mykey = "2u3tcj729pppDBhULM3oFrRI7iRfkGlQ";
        String url = "https://www.koreaexim.go.kr/site/program/financial/exchangeJSON?"
                +"authkey="+mykey        //인증키
                +"&searchdate="+dateStr  //날짜               주말에 null 값으로 변화되서 아무것도 나오지 않음.. 흠..... 대책이 필요함.
                +"&data=AP01";           //(성공==AP01
        StringRequest request = new StringRequest(             //Volley 라이브러리
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.d("TAG",response); //reponse에 json 내용이 담겨져이 있음.
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
        request.setShouldCache(false); //그대로 보여주세요  - 이거 따로 공부 필요
        AddHelper.requestQueue.add(request);
    }//sendRequest

    //gson-> 정보 가져오기 + 대량의데이터
    public void processResponse(String response){
        Gson gson = new Gson();
        int i;
        exchangeMonies = gson.fromJson(response, ExchangeMoney[].class); //Json 문서가 [ 로 시작하면 바로 배열부터 시작하는것이니 []로 시작해서 꺼내준다.
        if(exchangeMonies !=null){
            for(i=0; i<exchangeMonies.length; i++){
                //환율Json
                cur_nm[i] =  exchangeMonies[i].getCur_nm(); //나라이름
                cur_unit[i] = exchangeMonies[i].getCur_unit(); //통화종류
                kftc_deal_bas_r[i] = exchangeMonies[i].getKftc_deal_bas_r(); //기준매매환율
                //환율이미지(안드로이드 내장)
                iv_nationflag[i] = R.drawable.a01_arabemirates+i;       //순서를 통해 나라를 지정함.
            }
        }
    }//processResponse
}
