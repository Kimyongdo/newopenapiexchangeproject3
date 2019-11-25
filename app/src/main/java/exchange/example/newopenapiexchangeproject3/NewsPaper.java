package exchange.example.newopenapiexchangeproject3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.newopenapiexchangeproject3.R;

import java.util.ArrayList;

public class NewsPaper extends AppCompatActivity {

    EditText etNewsPaper;
    static  RecyclerView newsPaperRecylcer;
    static NewsAdapter newsAdapter;
    static ArrayList<NewsSearchVO> newsDatas = new ArrayList<NewsSearchVO>(); //시간, 날짜
    static ArrayList<String> newsLink = new ArrayList<>();


    Button newsBtn;
    static RelativeLayout newsLayoutGone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_paper);

    etNewsPaper = findViewById(R.id.et_newspaper);
    newsPaperRecylcer = findViewById(R.id.paperRecycler);
    newsBtn = findViewById(R.id.newsButton);
    newsLayoutGone = findViewById(R.id.newsLayoutGone);

    newsAdapter = new NewsAdapter(this,newsDatas);
    newsPaperRecylcer.setAdapter(newsAdapter);


    //키패드에 있는 엔터키를 눌렀을 때 반응하도록.
    etNewsPaper.setOnKeyListener(new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode== KeyEvent.KEYCODE_ENTER)  ){
                String keywrod = null;
                keywrod = etNewsPaper.getText().toString();
                NewsNaverSearch.NewSearching(keywrod);
                return  true;
            }
            return false;
        }
    });

    }


    //검색버튼을 눌렀을 때 반응하도록.
    public void clickNewsBtn(View view) {
        String keywrod = null;
        keywrod = etNewsPaper.getText().toString();
        NewsNaverSearch.NewSearching(keywrod);
        Log.d("tagagaga",keywrod);
    }
}
