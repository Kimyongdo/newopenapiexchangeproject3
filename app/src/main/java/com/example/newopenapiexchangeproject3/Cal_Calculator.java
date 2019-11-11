package com.example.newopenapiexchangeproject3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.newopenapiexchangeproject3.ExchangeJSON.cur_nm;
import static com.example.newopenapiexchangeproject3.ExchangeJSON.iv_nationflag;
import static com.example.newopenapiexchangeproject3.MainActivity.datas;

public class Cal_Calculator extends AppCompatActivity  {

    private static final String TAG_TEXT = "text";
    private static final String TAG_IMAGE = "image";

    boolean et1Focus, et2Focus;

    DecimalFormat df = new DecimalFormat("###,###.####");
    DecimalFormat dfTemp = new DecimalFormat("###,###.####");
    DecimalFormat df2 = new DecimalFormat("###,###.####");

    String result="";
    String resultTemp="";
    String result2="";

    Toolbar toolbar;
    ImageView iv_CompareNation1;
    EditText et_number;
    TextView tv_name;
    TextView tv_currency;

    ImageView iv_CompareNation2;
    EditText et_number2;
    TextView tv_name2;
    TextView tv_currency2;

    int nationPostionDown=21; //미국
    int nationPostionUp=12; //한국


    List<Map<String, Object>> dialog_arraylist;

    int[] image = new int[22];
    String [] text = new String[22];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calaulator_main);

        //툴바
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("환율계산기");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //뒤로가는 버튼 생성 및 클릭 시 뒤로 가기 됨.

        //첫번째 기준 환율
        tv_name = findViewById(R.id.click_nation_name);
        iv_CompareNation1=findViewById(R.id.click_nation_flag);
        et_number=findViewById(R.id.click_nation_et);
        tv_currency=findViewById(R.id.click_nation_name_currency);


        //두번째 기준 환율
        tv_name2 = findViewById(R.id.click_nation_name2);
        iv_CompareNation2=findViewById(R.id.click_nation_flag2);
        et_number2=findViewById(R.id.click_nation_et2);
        tv_currency2=findViewById(R.id.click_nation_name2_currency);


        et_number.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                switch (i){
                    case KeyEvent.KEYCODE_DEL :
                        et_number.setText("");
                }
                return false;
            }
        });


        //////////////////////////////////////////////숫자입력하는 곳/////////////////////////////////////////////////////////
        et_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(et1Focus) {
                    String input = et_number.getText().toString().trim();
                    //계속 지우기 누르면 끝에는 ""로 나오는데 이걸 double로 파싱할 수 없어서 오류가 나옴 - 튕김.
                    if (!input.equals("")) {

                        //소수점 추가코드
                        if (!charSequence.toString().equals(result)) {
                            result = df.format(Long.parseLong(charSequence.toString().replaceAll(",", "")));
                            et_number.setText(result);
                            et_number.setSelection(result.length());
                        }

                        //String으로 받아왔을 경우 ,와 . 를 제거해줌.
                        String KftcUp = ExchangeJSON.kftc_deal_bas_r[nationPostionUp];
                        String KftcDown = ExchangeJSON.kftc_deal_bas_r[nationPostionDown];

                        KftcUp = KftcUp.replace(",", "");
//                        KftcUp = KftcUp.replace(".", ""); //.까지 지우면 숫자가 훨씬 더 커지니까 여기서 변경
                        //1062.09가 나오는 상황
                        double tempup = Double.parseDouble(KftcUp); //double이 .은 허용함
                        tempup = (Math.round(tempup*10)/10.0);
                        //1062.5가 나옴


                        KftcDown = KftcDown.replace(",", "");
//                        KftcDown = KftcDown.replace(".", "");
                        double tempdown = Double.parseDouble(KftcDown); //double이 .은 허용함
                        tempdown = (Math.round(tempdown*10)/10.0);

                        input = input.replace(",", "");//지우는 순간 .은 인식하지 못해서. 오류가 나는 상황
                        double ExNum = Double.parseDouble(input);
                        double convert;
                        if(nationPostionUp==10 || nationPostionUp==11){  //첫째 edittext에서 위에가 일본,인도인 경우
                            convert = Math.round( (   ( (tempup/100) / (tempdown)  ) * ExNum * 1000)  );
                        }
                        else if(nationPostionDown==10 || nationPostionDown==11){ //첫째 edittext에서 아래가 일본,인도인 경우
                            //한국 선택했는데 아래가 일본인 경우
                            convert = Math.round( (   (tempup / (tempdown/100)  ) * ExNum * 1000)  );
                        }else{
                            convert = Math.round( (   (tempup / (tempdown)  ) * ExNum * 1000)  );
                        }
                        convert = convert/1000.0;

                        //숫자를 천단위마다 ,로 찍게 하는 코드
                        String convert2 = NumberFormat.getInstance().format(convert);
                        et_number2.setText(convert2);
                    }else{
                        et_number2.setText("");
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //커서가 옮겨졌다고 해야 에디트 텍스트에서 textwatcher가 따로 분리되서 발동됨.
        et_number.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                et1Focus =b;
                et_number.setText("");
            }
        });

        et_number2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                switch (i){
                    case KeyEvent.KEYCODE_DEL :
                        et_number2.setText("");
                }
                return false;
            }
        });

        //두번째
        et_number2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(et2Focus) {
                    String input2 = et_number2.getText().toString().trim();
                    Log.d("TAGG",input2);
                    //계속 지우기 누르면 끝에는 ""로 나오는데 이걸 double로 파싱할 수 없어서 오류가 나옴. //아무것도 없다면
                    if (!input2.equals("")) {
                        //소수점 추가해줌
                        if (!charSequence.toString().equals(result2)) {
                            result2 = df2.format(Long.parseLong(charSequence.toString().replaceAll(",", "")));
                            et_number2.setText(result2);
                            et_number2.setSelection(result2.length());
                        }

                        String KftcUp = ExchangeJSON.kftc_deal_bas_r[nationPostionUp];
                        String KftcDown = ExchangeJSON.kftc_deal_bas_r[nationPostionDown];



                        //여기에 숫자가 있는 상황 EX) 1,062.09라면

                        KftcUp = KftcUp.replace(",", "");
//                        KftcUp = KftcUp.replace(".", ""); //.까지 지우면 숫자가 훨씬 더 커지니까 여기서 변경
                        //1062.09가 나오는 상황
                        double tempup = Double.parseDouble(KftcUp); //double이 .은 허용함
                        tempup = (Math.round(tempup*10)/10.0);
                        //1062.5가 나옴


                        KftcDown = KftcDown.replace(",", "");
//                        KftcDown = KftcDown.replace(".", "");
                        double tempdown = Double.parseDouble(KftcDown); //double이 .은 허용함
                        tempdown = (Math.round(tempdown*10)/10.0);

                        /////////////////////////////////////////////////////////////////////////

                        input2 = input2.replace(",", ""); //지우는 순간 .은 인식하지 못해서. 오류가 나는 상황 .는 지워도 DOUBLE이기에 끝에 .0이 계속 남음.

                        double ExNum = Double.parseDouble(input2);//파서는 ""를 지우는 상황.

                        double convert;

                        if(nationPostionUp==11 || nationPostionUp==10){ //위에가 일본이면서 아래에서 숫자를 입력하는 경우 - 각각 위에꺼와 연동됨

                            convert = Math.round( (   ((tempdown*100) / (tempup)  ) * ExNum * 1000)  );
                        }else if(nationPostionDown==11 || nationPostionDown==10){ //두번째 eidttext이면서 아래가 일본인 경우
                            convert = Math.round( (   ((tempdown/100) / (tempup)  ) * ExNum * 1000)  );
                        } else {
                            convert = Math.round( (   (tempdown / (tempup)  ) * ExNum * 1000)  );
                        }

                        convert = convert/1000.0;

                        //숫자 천단위 마다 변형하는 코드.
                        //숫자 커지면 뒤에 E나오는 문제도 해결됨
                        String convert2 = NumberFormat.getInstance().format(convert);
                        et_number.setText(convert2);
                    }else{
                        et_number.setText("");
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_number2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                et2Focus=b;
                et_number2.setText("");
            }
        });
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //////////////////////////키보드 고정///////////////////////////////////////
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et_number ,
                InputMethodManager.SHOW_IMPLICIT);
       ////////////////////////////////////////////////////////////////////////////////

        for(int i=0; i<22; i++){
            image[i] = iv_nationflag[i];
            text[i] = cur_nm[i];
        }


        dialog_arraylist = new ArrayList<>();

        for(int i=0;i<image.length;i++)
        {
            Map<String, Object> itemMap = new HashMap<>();
            itemMap.put(TAG_IMAGE, image[i]);
            itemMap.put(TAG_TEXT, text[i]);
            dialog_arraylist.add(itemMap);
        }

    }/////////////oncreate

    //뒤로가기 화살표
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :{
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //리스튜뷰 보여주기
    public void clickNationKorea(View view) {
        firstshowAlert();
    }
    public void clickNationOther(View view) {
        secondshowAlert1();
    }

    public void firstshowAlert() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Cal_Calculator.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.calcaulator_dialog, null);
        builder.setView(view);

        final ListView listview = (ListView)view.findViewById(R.id.listview_alterdialog_list);
        final AlertDialog dialog = builder.create();

        SimpleAdapter simpleAdapter = new SimpleAdapter(Cal_Calculator.this, dialog_arraylist,
                R.layout.calculator_dialog_itemlist,
                new String[]{TAG_IMAGE, TAG_TEXT},
                new int[]{R.id.iv_nation_selection_alert, R.id.tv_nation_selection_alert});

        listview.setAdapter(simpleAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Cal_Calculator.this, text[position]+"", Toast.LENGTH_SHORT).show();
                iv_CompareNation1.setImageResource(image[position]);
                tv_name.setText(text[position]);
                tv_currency.setText(ExchangeJSON.cur_unit[position]);
                nationPostionUp = position;
                et_number.setText("");
                et_number2.setText("");

                dialog.dismiss();
            }
        });

       // dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void secondshowAlert1() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Cal_Calculator.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.calcaulator_dialog, null);
        builder.setView(view);

        final ListView listview = (ListView)view.findViewById(R.id.listview_alterdialog_list);
        final AlertDialog dialog = builder.create();

        SimpleAdapter simpleAdapter = new SimpleAdapter(Cal_Calculator.this, dialog_arraylist,
                R.layout.calculator_dialog_itemlist,
                new String[]{TAG_IMAGE, TAG_TEXT},
                new int[]{R.id.iv_nation_selection_alert, R.id.tv_nation_selection_alert});

        listview.setAdapter(simpleAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Cal_Calculator.this, text[position]+"", Toast.LENGTH_SHORT).show();
                iv_CompareNation2.setImageResource(image[position]);
                tv_name2.setText(text[position]);
                tv_currency2.setText(ExchangeJSON.cur_unit[position]);

                nationPostionDown = position;

                et_number.setText("");
                et_number2.setText("");
                dialog.dismiss();
            }
        });

        // dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }




}


