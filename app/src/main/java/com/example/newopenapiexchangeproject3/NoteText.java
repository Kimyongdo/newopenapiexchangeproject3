package com.example.newopenapiexchangeproject3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NoteText extends AppCompatActivity {

    ImageView iv_done;
    EditText et_title;
    EditText et_content;

    String et_title_reader;
    String et_content_reader;

    static ArrayList<NoteVO> notelist = new ArrayList<>();

    InputMethodManager imm;
    int save;

    ImageView iv_camera;
    Uri imgUri=null; //캡쳐이미지 저장경로

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_text);

        ///////////////////////////카메라를 사용하는 경우/////////////////////////////////////////////
        iv_camera = findViewById(R.id.iv_updateNote_first_camera);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){ //sdk version이 마시멜로우보다 높은 겨우
            int chekcedPersmission =checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE); //Write쓰면 Read권한도 동시에 발현

            if(chekcedPersmission== PackageManager.PERMISSION_DENIED){ //처음에 거부되어있다면
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},10); //허가여부 다이얼로그 확인.
            }

        }

        //카메라 이미지 선택시.
        iv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                setImageUri();
                if(imgUri!=null) intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
                startActivityForResult(intent, 1000);
            }
        });


        //연결
        iv_done = findViewById(R.id.iv_updateNote_first_done);
        et_title = findViewById(R.id.et_note_title);
        et_content = findViewById(R.id.et_note_content);



        //키보드가 올라오도록
        et_title.requestFocus();
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);





        //확인 누르면 글 내용 읽고 notelist에 추가 및 저장 후 닫가.
        iv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_title_reader = et_title.getText().toString();
                et_content_reader= et_content.getText().toString();



                Log.d("number", et_title_reader.length()+"");//인2a라고 했을 경우 length 3으로 변환되서 나옴.

                //edittext비어있는 경우를 아기 위해 mathces를 이용
                //모두 비어있는 경우
                if(et_content_reader.matches("") && et_title_reader.matches("")){
                   Toast.makeText(NoteText.this, "빈 노트는 저장할 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
                //제목은 없고 내용만 있는 경우
                else if(et_title_reader.matches("") && !et_content_reader.matches("")) {
                    et_title_reader=et_content_reader;
                    noelistGroup();
                }else{
                    //제목, 내용을 모두 적은 경우
                    noelistGroup();
                }


            }
        });
    }/////////////////////////////////////////////////////////////onCreate////////////////////////////////////////////////////////////////////////////

    //확인 눌렀을 경우 반복되는 메소드 압축함.
    public void noelistGroup(){
        notelist.add(0,new NoteVO(GlobalTime.noetime,et_title_reader, et_content_reader));
        DataSaveNote();
        save=0; //저장토큰
        imm.hideSoftInputFromWindow(et_title.getWindowToken(),0);
        imm.hideSoftInputFromWindow(et_content.getWindowToken(),0);
        Toast.makeText(NoteText.this, "저장 되었습니다.", Toast.LENGTH_SHORT).show();
    }


    public void DataSaveNote(){
        try {
            File file = new File(getFilesDir(),"notelist");
            FileOutputStream fos  = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(notelist); //현재의 datas를 저장.
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //뒤로가기 눌렀을 때 변경이 되도록.
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(save==0){ //저장을 누르면 save값은 0으로 지정, 0인 경우 다이얼로그가 뜨지 않게 설정.
            Intent intent = new Intent(NoteText.this, NoteMain.class);
            startActivity(intent);
            finish();
        }else{
            if(keyCode == KeyEvent.KEYCODE_BACK){
                et_title_reader = et_title.getText().toString();
                et_content_reader= et_content.getText().toString();

                //만약에
                if(!et_title_reader.matches("") || !et_content_reader.matches("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("삭제하시겠습니까?");//창 이름
                    builder.setMessage("변경 내용이 저장되지 않았습니다. 변경 내용을 삭제하시겠습니까?");

                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(NoteText.this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(NoteText.this, NoteMain.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                    builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //아무일도 안일어남.
                        }
                    });

                    AlertDialog dialog = builder.create(); //create 생성
                    dialog.show(); //.show()를 표시할것
                    return  true;
                }
            }
        }

        return super.onKeyDown(keyCode, event);
    }


    //사진촬영 reqeustPermission 선택의 결과여부를 알려주는 곳
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 10:
                if(grantResults[0]==PackageManager.PERMISSION_DENIED){
                    Toast.makeText(this, "카메라 기능 사용 제한", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "카메라 사용 가능", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    void setImageUri(){

        File path= getExternalFilesDir("photo");
        //path 경로에 없다면 폴더 만들기.
        if(!path.exists()) path.mkdirs();
        path= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES); //Picture폴더

        //1) 날짜를 이용하는 방법
        SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMddhhmmss");
        String fileName= "IMG_"+sdf.format(new Date()) + ".jpg";
        File imgFile= new File(path, fileName);

        //2) 자동으로 임시파일명으로 만들어주는 메소드 이용하는 방법
        try {
            imgFile= File.createTempFile("IMG_",".jpg", path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //File클래스 -> Uri클래스로 변경 (누가버전 이후 변경)
        if( Build.VERSION.SDK_INT<Build.VERSION_CODES.N){ //누가버전 이전이라면
            imgUri= Uri.fromFile(imgFile);
        }else{
            imgUri= FileProvider.getUriForFile(this,  "com.example.newopenapiexchangeproject3", imgFile);
        }
        //manifest 및 xml 폴더 만들기.
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1000:
                if(resultCode==RESULT_OK){
                    //intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri)를 통해 카메라앱이 실행되면
                    //그 결과를 돌려주는 Intent객체가 돌아오지 않음(즉, 이 메소드의 3번째 파라미터인 data가 null임)[단, 지니모션은 Intent가 옴]
                    //이를 기반으로 코드 작성할 필요 있음.
                    if(data!=null){//결과를 가져오는 Intent객체가 있는가?
                        //이미지의 경로의 Uri를 얻어오기
                        Uri uri= data.getData();
                        if(uri!=null) {//파일로 자동 저장되어 있는 경우 (작업이 수월함)]
                            Glide.with(this).load(uri).into(iv_camera);
                        }
                        else{
                            if(imgUri!=null) Glide.with(this).load(imgUri).into(iv_camera);
                            Intent intent= new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                            intent.setData(imgUri);
                            sendBroadcast(intent);
                        }
                    }else{
                        //Bitmap데이터로만 전달되어 온 경우 [파일로 저장되어 있지 않음, 이미지도 섬네일이미지라 해상도 낮음]
                        //캡쳐한 사진을 서버로 보내거나 하는 경우 무조건 파일로 저장할 필요 있음.

                        //카메라 액티비티에게 미리 파일로 저장하여 달라고 Intent를 만들때 추가로 정보를 주어야 만 함.(저 위에!!)
                        //저 위에서 저장파일 경로 imgUri가 제대로 동작한다면...그 경로의 이미지를 이미지뷰에 보여주기(섬네일 이미지(Bitmap)는 해상도가 안좋아서)
                        if(imgUri!=null) Glide.with(this).load(imgUri).into(iv_camera);

                        //이미지가 잘 보인다면 파일로 저장된 것임. 다만, 갤러리 앱에서 이 파일을 스캔하지 않아 갤러리앱에 목록으로 나오지 않음.
                        //갤러리 앱이 저장한 이미지파일을 스캔하도록...Broadcast를 보냄.
                        Intent intent= new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        intent.setData(imgUri);
                        sendBroadcast(intent);

                        //지니모션 애뮬레이터는 전원을 한번 껐다가 켜야 됨. SD카드가 실제로 마운트된 것이 아니어서 실시간으로 읽어들이지 못함.
                    }

                }

                break;
        }
    }
}
