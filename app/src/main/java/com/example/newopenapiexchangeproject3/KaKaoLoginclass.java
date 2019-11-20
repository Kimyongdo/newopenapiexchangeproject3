package com.example.newopenapiexchangeproject3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kakao.auth.ErrorCode;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.LoginButton;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;


public class KaKaoLoginclass extends AppCompatActivity {



    private static final String TAG = "";
    SessionCallback callback;

    Button kakao_custom_login;
    LoginButton com_kakao_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.z_loginclass);

        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
        requestMe();

        kakao_custom_login = findViewById(R.id.kakao_custom_login);
        kakao_custom_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                com_kakao_login.performClick();
            }
        });
        com_kakao_login = findViewById(R.id.com_kakao_login);

    }

    //로그아웃 기능은 따로 설정 탭 만들어서 하는게 나을 수 있음. --> 한 로그인 페이지에 넣지 말자.
    public void kakao_logout(View view) {
        UserManagement.requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {

                Intent intent = getIntent();
                intent.putExtra("logout",123);
                intent.putExtra("logoutNickname","Anonymous");
                intent.putExtra("logoutImage",R.drawable.user);
                KaKaoLoginclass.this.setResult(RESULT_OK,intent); //이거 끝에 intent 또 빼먹네
                Toast.makeText(KaKaoLoginclass.this, "로그아웃 완료", Toast.LENGTH_SHORT).show();
            }
        });
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
                //    Log.e("UserProfile", userProfile.getNickname());
                //    Log.e("UserProfile", userProfile.getThumbnailImagePath());
                //    Log.e("UserProfile", userProfile.getId() + "");
                    long nicknumber = userProfile.getId();
                    String nickname = userProfile.getNickname();
                    String nickimage = userProfile.getProfileImagePath();

                    Intent intent = getIntent();
                    intent.putExtra("login",124);
                    intent.putExtra("nicknumber",nicknumber);
                    intent.putExtra("nickname",nickname);
                    intent.putExtra("nicknameimage",nickimage);
                    KaKaoLoginclass.this.setResult(RESULT_OK,intent); //이거 끝에 intent 또 빼먹네

                    Toast.makeText(KaKaoLoginclass.this, "로그인 완료", Toast.LENGTH_SHORT).show();
                    //바로 인텐트하고 finish 하면 스레드로 인해 적용이 바로 안됨.
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
