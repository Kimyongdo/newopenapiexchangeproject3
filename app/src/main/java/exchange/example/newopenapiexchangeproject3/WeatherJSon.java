package exchange.example.newopenapiexchangeproject3;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import exchange.example.newopenapiexchangeproject3.WeatherApi.WeatherJson;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;

public class WeatherJSon {

    //이걸로 확인중
    public static String[] todayCity = new String[22];
    public static String[] todayNation= new String[22];
    public static String[] todayIcon= new String[22];
    public static String[]  todayC1= new String[22];
    public static String[] todayweather = new String[22];
    public static String[] todayIcon1= new String[22];
    public static double[] todayF= new double[22];
    public static int[] todayC= new int[22];
    public static double[] todaywind1= new double[22];
    public static String[] todaywind= new String[22];
    public static double[] todayHum1= new double[22];
    public static String[] todayHum= new String[22];

    static void WatherSendRequest(int cityid, final int q){

        String key = "7574a21624ab7a502c88c128dff5f93d";
        String url = "http://api.openweathermap.org/data/2.5/" +
                "weather?id="+cityid+
                "&forecast?id=524901&"+
                "APPID="+key;

        StringRequest request = new StringRequest(             //Volley 라이브러리
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.d("TAG",response); //reponse에 json 내용이 담겨져이 있음.
                        Gson gson = new Gson();
                        int i;
                        WeatherJson weatherJson = gson.fromJson(response, WeatherJson.class); //Json 문서가 [ 로 시작하면 바로 배열부터 시작하는것이니 []로 시작해서 꺼내준다.
                        if (weatherJson != null) { //먼저 숫자를 배정하는거지

                            todayweather[q] = weatherJson.getWeather().get(0).getMain(); //오늘 날씨  //하나밖에 없으니까.

                            todayCity[q] = weatherJson.getName(); //서울  //0일때는 되고 k일때 되는거보면 우선적으로 값을 넣는게 맞아.
                            todayNation[q] = weatherJson.getSys().getCountry(); //대한민국
                            todayIcon1[q] = weatherJson.getWeather().get(0).getIcon(); //아이콘
                            todayIcon[q] = "http://openweathermap.org/img/wn/" + todayIcon1[q] + "@2x.png";
                            todayF[q] = weatherJson.getMain().getTemp();
                            todayC[q] = (int) Math.round(((todayF[q] - 273) * 100) / 100.0);
                            todayC1[q] = todayC[q] + "";
                            todaywind1[q] = weatherJson.getWind().getSpeed();
                            todaywind[q] = todaywind1[q] + "";
                            todayHum1[q] = weatherJson.getMain().getHumidity();
                            todayHum[q] = todayHum1[q] + "";


                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

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

}
