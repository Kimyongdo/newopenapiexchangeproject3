package com.example.newopenapiexchangeproject3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UpdateMain extends AppCompatActivity {

    RecyclerView UpdateRecyclerView;
    static ArrayList<UpdateVO> updates = new ArrayList<>();
    UpdateAdapter updateAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_content);

        //툴바
        Toolbar toolbar;
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loading();
        UpdateRecyclerView = findViewById(R.id.update_recyclerview);

        updateAdapter = new UpdateAdapter(this, updates);
        UpdateRecyclerView.setAdapter(updateAdapter);

    }//onCreate

    public void loading(){
        String serverUrl ="http://chocojoa123.dothome.co.kr/Exchange/ExloadPHP.php";
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, serverUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                updates.clear();
                updateAdapter.notifyDataSetChanged();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        int no = Integer.parseInt(jsonObject.getString("no")); //db테이블의 제목이 no라고 하는 것을 가져옴
                        String title = jsonObject.getString("title");     //db테이블의 content 제목에 해당하는 값을 가져옴
                        String nickname = jsonObject.getString("name");
                        String content = jsonObject.getString("content");
                        String now = jsonObject.getString("date");

                        updates.add(0,new UpdateVO(title, nickname, content,now));
                        updateAdapter.notifyItemInserted(0);


                        Log.d("NUMBER",updates.get(0).getUpdateContent());

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
}
