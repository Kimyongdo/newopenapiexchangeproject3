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
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.newopenapiexchangeproject3.NoteText.notelist;

public class NoteInText extends AppCompatActivity {

    //<두번째 textnote>


    ImageView iv_done_second;
    EditText et_title_second;
    EditText et_content_second;


    String et_title_reader_second;
    String et_content_reader_second;


    InputMethodManager imm;

    int beforeEtTitle;
    int beforeEtContent;
    int afterEtTitle;
    int afterEtContent;

    int number; //리사이클러뷰 각 몇번 째인지 확인가능.
    int save;//세이브했는지 확인


    ImageView ivCamera;
    ImageView ivCemeraImage;
    Uri imgUri=null; //캡쳐이미지 저장경로

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_in_text);

        final Intent intent = getIntent();
        number = intent.getIntExtra("number",0);

        iv_done_second = findViewById(R.id.iv_updateNote_Second_done);
        et_title_second = findViewById(R.id.et_note_second_title);
        et_content_second = findViewById(R.id.et_note_second_content);

        //처음 받아온 기존 내용을 보여줘야함.
        et_title_second.setText(notelist.get(number).getNoteTitle());
        et_content_second.setText(notelist.get(number).getNoteContent());

        //페이지를 열마자마자 받은 글씨 개수
        beforeEtTitle = et_title_second.length();
        beforeEtContent = et_content_second.length();

        //처음 글을 썼을 때의 글자 개수 - 이 글자수와 달라지면 변경 된 것...(우연하게 글자수가 같은 경우는...ㅋㅋ)


        //키보드
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);


        //////////////////////////////////////////////카메라/////////////////////////////////////////////////////////////
        ivCamera = findViewById(R.id.iv_updateNote_second_camera);
        ivCemeraImage = findViewById(R.id.iv_camareaImage_second);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){ //sdk version이 마시멜로우보다 높은 겨우
            int chekcedPersmission =checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE); //Write쓰면 Read권한도 동시에 발현

            if(chekcedPersmission== PackageManager.PERMISSION_DENIED){ //처음에 거부되어있다면
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},10); //허가여부 다이얼로그 확인.
            }

        }

        //카메라 이미지 선택시.
        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                setImageUri();
                if(imgUri!=null) intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
                startActivityForResult(intent, 1000);
            }
        });
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        //완료 눌렀을 경우
        iv_done_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //처음이 아니기 때문에 이걸 수정했을 떄 모두 지웠을 경우 인정해야하나  -> 수정했을 경우 모두 지우면 제목만 제목없는 노트로 변경함.
                //제목의 글자와 내용이 수정 전 후 모두 같은 경우

                if(beforeEtTitle==0 && beforeEtContent==0){
                    et_title_reader_second = et_title_second.getText().toString();
                     et_content_reader_second= et_content_second.getText().toString();
                    et_title_reader_second="제목없는 노트";
                    IntentForResult();
                }
                //제목은 없고 내용만 있는 경우
                else if(beforeEtTitle==0 && beforeEtContent!=0) {
                    et_title_reader_second=et_content_reader_second;
                    IntentForResult();
                }else{
                    //제목, 내용을 모두 적은 경우
                    IntentForResult();
                }


            }
        });

    }


    public void IntentForResult(){
        et_title_reader_second = et_title_second.getText().toString();
        et_content_reader_second= et_content_second.getText().toString();
        save=911; //저장토큰
        notelist.set(number,new NoteVO(GlobalTime.noetime,et_title_reader_second, et_content_reader_second));
        //키보드 내리기
        imm.hideSoftInputFromWindow(et_title_second.getWindowToken(),0);
        imm.hideSoftInputFromWindow(et_content_second.getWindowToken(),0);
        Toast.makeText(NoteInText.this, "저장 되었습니다.", Toast.LENGTH_SHORT).show();
        //리사이클러뷰에 변환됨을 바로 보여줄 수 있음.
        Intent intent1 = getIntent();
        intent1.putExtra("title", et_title_reader_second);
        intent1.putExtra("content",et_content_reader_second);
        intent1.putExtra("number",number);
        NoteInText.this.setResult(RESULT_OK,intent1);
        DataSaveNote();
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


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(save==911){ //저장을 누르면 save값은 0으로 지정, 0인 경우 다이얼로그가 뜨지 않게 설정.
           Intent intent = new Intent(this,NoteMain.class);
           startActivity(intent);
           finish();
        }

        //뒤로가기 버튼을 누르면
           else if(keyCode == KeyEvent.KEYCODE_BACK){
//                et_title_reader_second = et_title_second.getText().toString();
//                et_content_reader_second= et_content_second.getText().toString();

                //수정 후 글씨 개수
                afterEtTitle = et_title_second.length();
                afterEtContent = et_content_second.length();


                //처음 글자수와 나중의 글자수가 다른 경우
                if(beforeEtTitle!=afterEtTitle || beforeEtContent!=afterEtContent){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("삭제하시겠습니까?");//창 이름
                    builder.setMessage("변경 내용이 저장되지 않았습니다. 변경 내용을 삭제하시겠습니까?");

                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(NoteInText.this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(NoteInText.this, NoteMain.class);
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
//        }

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
                    if(data!=null){
                        Uri uri= data.getData();
                        if(uri!=null) {//파일로 자동 저장되어 있는 경우 (작업이 수월함)]
                            Glide.with(this).load(uri).into(ivCemeraImage);
                        }
                        else{
                            if(imgUri!=null) Glide.with(this).load(imgUri).into(ivCemeraImage);

                            //실력부족으로 커서에 맞는 사진이미지를 넣는 것이 불가능함. ㅎ...ㅎㅎ;;;

                            Intent intent= new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                            intent.setData(imgUri);
                            sendBroadcast(intent);
                        }
                    }else{
                        if(imgUri!=null) Glide.with(this).load(imgUri).into(ivCemeraImage);
                        Intent intent= new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        intent.setData(imgUri);
                        sendBroadcast(intent);
                    }

                }

                break;
        }
    }
}
