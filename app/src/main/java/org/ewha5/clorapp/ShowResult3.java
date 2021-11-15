package org.ewha5.clorapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.channguyen.rsv.RangeSliderView;

public class ShowResult3 extends AppCompatActivity {

    private static final String TAG = "ShowResult3";

    ImageView final1;
    ImageView final2;
    ImageView line5;
    ImageView line6;
    ImageView cloricon;

    TextView rating;
    TextView finalresult;

    int mMode = AppConstants.MODE_INSERT;
    int _id = -1;
    RangeSliderView moodSlider;
    int moodIndex = 2;
    Note item;
    Context context;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_result3);

        line5 = findViewById(R.id.line5);
        line6 = findViewById(R.id.line6);
        cloricon = findViewById(R.id.cloricon);

        rating = findViewById(R.id.rating);
        finalresult = findViewById(R.id.finalresult);

        //색상 변경 - 보색으로 2.png 기준
        final1 = findViewById(R.id.final1);
        final1.setColorFilter(Color.parseColor("#E5DABF"), PorterDuff.Mode.SRC_IN);
        final1.bringToFront();
        final2 = findViewById(R.id.final2);
        final2.setColorFilter(Color.parseColor("#7B7663"), PorterDuff.Mode.SRC_IN);

        //통계로 이동
        Button btnstatics = findViewById(R.id.btnstatics);
        btnstatics.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Fragment3.class);
                startActivityForResult(intent, AppConstants.STATICS);
            }
        });

        //홈으로 연결
        Button btnhome = findViewById(R.id.btnhome);
        btnhome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivityForResult(intent, AppConstants.MAIN);
            }
        });

        //웹으로 연결
        Button btnweb = findViewById(R.id.btnweb);
        btnweb.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.naver.com"));
                startActivity(myIntent);
            }
        });

        //평가도 저장
        Button saveRate = findViewById(R.id.saverate);
        saveRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMode == AppConstants.MODE_INSERT) {
                    //saveNote();
                } else if(mMode == AppConstants.MODE_MODIFY) {
                    //modifyNote();
                }
            }
        });

        moodSlider = findViewById(R.id.sliderView);
        final RangeSliderView.OnSlideListener listener = new RangeSliderView.OnSlideListener() {
            @Override
            public void onSlide(int index) {
                AppConstants.println("moodIndex changed to " + index);
                moodIndex = index;
            }
        };

        moodSlider.setOnSlideListener(listener);
        moodSlider.setInitialIndex(2);

    }

}
