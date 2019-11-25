package exchange.example.newopenapiexchangeproject3;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.newopenapiexchangeproject3.R;

public class Tutorialpage extends Dialog {

    ImageView iv_tutorialpage;

    public Tutorialpage(@NonNull Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);   //다이얼로그의 타이틀바를 없애주는 옵션입니다.
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  //다이얼로그의 배경을 투명으로 만듭니다.
        setContentView(R.layout.z_tutorialpage);     //다이얼로그에서 사용할 레이아웃입니다.

        iv_tutorialpage = findViewById(R.id.clicktutorial);  //여기서 튜토리얼 이미지를 추가하면 된다.
        iv_tutorialpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
