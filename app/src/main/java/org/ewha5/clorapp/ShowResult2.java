package org.ewha5.clorapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShowResult2 extends AppCompatActivity {

    private static final String TAG = "ShowResult2";


    ImageView upper1;
    ImageView upper2;
    ImageView upper3;
    ImageView under1;
    ImageView under2;
    ImageView under3;
    ImageView line4;
    ImageView conicon2;
    ImageView tonalicon2;

    TextView select_comb2;

    RadioButton comb1, comb2, comb3;
    RadioGroup radioGroup2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_result2);

        line4 = findViewById(R.id.line4);
        conicon2 = findViewById(R.id.combicon2);
        tonalicon2 = findViewById(R.id.tonalicon2);

        select_comb2 = findViewById(R.id.select_comb2);

        //색상 변경 - 보색으로 2.png 기준
        upper1 = findViewById(R.id.upper1);
        upper1.setColorFilter(Color.parseColor("#E5DABF"), PorterDuff.Mode.SRC_IN);
        under1 = findViewById(R.id.under1);
        under1.setColorFilter(Color.parseColor("#A7ACB2"), PorterDuff.Mode.SRC_IN);
        under1.bringToFront();

        upper2 = findViewById(R.id.upper2);
        upper2.setColorFilter(Color.parseColor("#E5DABF"), PorterDuff.Mode.SRC_IN);
        under2 = findViewById(R.id.under2);
        under2.setColorFilter(Color.parseColor("#7B7663"), PorterDuff.Mode.SRC_IN);
        under2.bringToFront();

        upper3 = findViewById(R.id.upper3);
        upper3.setColorFilter(Color.parseColor("#E5DABF"), PorterDuff.Mode.SRC_IN);
        under3 = findViewById(R.id.under3);
        under3.setColorFilter(Color.parseColor("#434B4D"), PorterDuff.Mode.SRC_IN);
        under2.bringToFront();

        //라디오 버튼 설정
        comb1 = (RadioButton) findViewById(R.id.comb1);
        /*
        rbtn1.setOnClickListener(new RadioButton.OnClickListener(){
           @Override
            public void onClick(View view) {
            }
        });
         */

        comb2 = (RadioButton) findViewById(R.id.comb2);
        /*
        rbtn2.setOnClickListener(new RadioButton.OnClickListener(){
            @Override
            public void onClick(View view) {
            }
        });
         */
        comb3 = (RadioButton) findViewById(R.id.comb3);
        /*
        rbtn2.setOnClickListener(new RadioButton.OnClickListener(){
            @Override
            public void onClick(View view) {
            }
        });
         */

        //라디오 그룹 설정
        radioGroup2 = findViewById(R.id.radioGroup2);
        int checkedRadioButtonId = radioGroup2.getCheckedRadioButtonId();
        if (checkedRadioButtonId == R.id.comb1) {
            // 선택지1
        }
        else{
            if (checkedRadioButtonId == R.id.comb2) {
                // 선택지2
            }
            else {
                // 선택지3
            }
        }

        //다음 단계로 이동
        Button finalselect = findViewById(R.id.finalselect);
        finalselect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //다음 단계로 연결
                Intent intent = new Intent(getApplicationContext(), ShowResult3.class);
                startActivityForResult(intent, AppConstants.FINAL_FIGURE);
            }
        });

    }

}
