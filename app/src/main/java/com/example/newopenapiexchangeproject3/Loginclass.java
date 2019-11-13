package com.example.newopenapiexchangeproject3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newopenapiexchangeproject3.WeatherApi.Main;
import com.kakao.auth.ErrorCode;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;

import static com.example.newopenapiexchangeproject3.MainActivity.navigationView;


public class Loginclass extends AppCompatActivity {

    static String nnickname=null;

    private static final String TAG = "";
    SessionCallback callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.z_loginclass);

        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
        requestMe();



//        Intent intent = new Intent(Loginclass.this,MainActivity.class);
//        intent.putExtra("nickname",nnickname);
//        startActivity(intent);


//        navigationView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//            @Override
//            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
//                navigationView.removeOnLayoutChangeListener(this);
//                TextView textView = navigationView.findViewById(R.id.tv_navi_header_name);
//                textView.setText("gd");
//
//            }
//        });

    }




    class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {

            UserManagement.requestMe(new MeResponseCallback() {
                @Override
                public void onFailure(ErrorResult errorResult) {
                    String message = "failed to get user info. msg=" + errorResult;

                    ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                    if (result == ErrorCode.CLIENT_ERROR_CODE) {
                        //에러로 인한 로그인 실패
                        // finish();
                    } else {
                        //redirectMainActivity();
                    }
                }

                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                }

                @Override
                public void onNotSignedUp() {

                }

                @Override
                public void onSuccess(UserProfile userProfile) {
                    //로그인에 성공하면 로그인한 사용자의 일련번호, 닉네임, 이미지url등을 리턴합니다.
                    //사용자 ID는 보안상의 문제로 제공하지 않고 일련번호는 제공합니다.
                    Log.e("UserProfile", userProfile.getNickname());
                    Log.e("UserProfile", userProfile.getThumbnailImagePath());
                    Log.e("UserProfile", userProfile.getId() + "");
                    String nickname = userProfile.getNickname();
                    String nickimage = userProfile.getProfileImagePath();

                    Intent intent = getIntent();
                    intent.putExtra("nickname",nickname);
                    intent.putExtra("nicknameimage",nickimage);
                    Loginclass.this.setResult(RESULT_OK,intent); //이거 끝에 intent 또 빼먹네

                    Toast.makeText(Loginclass.this, "로그인 완료", Toast.LENGTH_SHORT).show();
                }

//여기다가 Toast를 하는 경우 mainThread와 인터넷 Thread의 차이로 null이 생성된다.
            });


        }
        // 세션 실패시
        @Override
        public void onSessionOpenFailed(KakaoException exception) {


        }
    }

    public void requestMe() {
        //유저의 정보를 받아오는 함수
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                Log.e(TAG, "error message=" + errorResult);
//                super.onFailure(errorResult);
            }
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.d(TAG, "onSessionClosed1 =" + errorResult);
            }
            @Override
            public void onNotSignedUp() {
                //카카오톡 회원이 아닐시
                Log.d(TAG, "onNotSignedUp ");
            }
            @Override
            public void onSuccess(UserProfile result) {
                Log.e("UserProfile", result.toString());
                Log.e("UserProfile", result.getId() + "");
            }
        });
    }

}
