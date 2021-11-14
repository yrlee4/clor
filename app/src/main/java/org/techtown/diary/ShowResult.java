package org.techtown.diary;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class ShowResult extends AppCompatActivity {

    private static final String TAG = "ShowResult";


    ImageView colorimage; //색상 칩
    ImageView line1;
    ImageView line2;
    ImageView line3;
    ImageView conicon;
    ImageView tonalicon;

    TextView select_comb;
    TextView category;
    TextView editcategory;
    TextView inputcolor;
    TextView conexp;
    TextView tonalexp;


    RadioButton rbtn1, rbtn2;
    RadioGroup radioGroup1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_result);

        line1 = findViewById(R.id.line1);
        line2 = findViewById(R.id.line2);
        line3 = findViewById(R.id.line3);
        conicon = findViewById(R.id.combicon2);
        tonalicon = findViewById(R.id.tonalicon2);

        category = findViewById(R.id.category);
        inputcolor = findViewById(R.id.inputcolor);
        select_comb = findViewById(R.id.select_comb);
        conexp = findViewById(R.id.conexp);
        tonalexp = findViewById(R.id.tonalexp);

        editcategory = findViewById(R.id.editcategory);

        //색상 변경
        colorimage = findViewById(R.id.colorimage);
        colorimage.setColorFilter(Color.parseColor("#E5DABF"), PorterDuff.Mode.SRC_IN);

        //라디오 버튼 설정
        rbtn1 = (RadioButton) findViewById(R.id.choice1);
        /*
        rbtn1.setOnClickListener(new RadioButton.OnClickListener(){
           @Override
            public void onClick(View view) {
            }
        });
         */

        rbtn2 = (RadioButton) findViewById(R.id.choice2);
        /*
        rbtn2.setOnClickListener(new RadioButton.OnClickListener(){
            @Override
            public void onClick(View view) {
            }
        });
         */

        //라디오 그룹 설정
        radioGroup1 = findViewById(R.id.radioGroup);
        int checkedRadioButtonId = radioGroup1.getCheckedRadioButtonId();
        if (checkedRadioButtonId == R.id.choice1) {
            // 보색 조합으로 연결
        }
        else{
            if (checkedRadioButtonId == R.id.choice2) {
                // 유사색 조합으로 연결
            }
        }

        //다음 단계로 이동
        Button selectFinish = findViewById(R.id.select);
        selectFinish.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //다음 단계로 연결
                Intent intent = new Intent(getApplicationContext(), ShowResult2.class);
                startActivityForResult(intent, AppConstants.REQ_SHOW_COLORS);
            }
        });

    }

    public void setCategory(String data){
        editcategory.setText(data);
        //카테고리 설정
    }
}
