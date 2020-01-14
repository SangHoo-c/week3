package com.example.myapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Dialog_event_activity extends AppCompatActivity  {
    PieChart pieChart;

    private Context mContext;
    SharedPreferences pref;
    String accountName;
    //accountName 을 기준으로 유저 디비에서 mbti 성격을 가져온다

    //MBTI 유사도 비교
    String user_mbti ="ENFJ";
    String random_mbti = "ENTP";
    int check,flag;
    private TextView random_user_id, random_user_name,random_user_mbti;
    private ImageView random_user_image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frag_ex4);

        random_user_id = findViewById(R.id.random_user_id);
        random_user_mbti = findViewById(R.id.random_user_mbti);
        random_user_name = findViewById(R.id.random_user_name);
        random_user_image = findViewById(R.id.random_user_image);

        Intent intent = getIntent();
        random_mbti = intent.getExtras().getString("key_i");
        Log.d("get_random_mbti", random_mbti);

        SharedPreferences pref = getSharedPreferences("pref", Context.MODE_PRIVATE);
        accountName = pref.getString("key1", "");




        pieChart = findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);

        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();

        check =0;
        flag=0;
        for(int j=0; j<4;j++){
            if(user_mbti.toLowerCase().charAt(j) == random_mbti.toLowerCase().charAt(j) ){
                check++;
                Log.d("checking_for_", String.valueOf(check));
            }
            flag=1;
        }


        yValues.add(new PieEntry(check,"유사도"));
        yValues.add(new PieEntry(4-check,"비유사도"));

        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic); //애니메이션

        PieDataSet dataSet = new PieDataSet(yValues,"");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);

        pieChart.setData(data);


        //유저에 맞게 정보 변경 해주기
        random_user_mbti.setText(random_mbti.toUpperCase());
        switch (random_mbti){
            case  "enfj":
                random_user_image.setImageResource(R.drawable.enfj);
                break;
            case  "enfp":
                random_user_image.setBackgroundResource(R.drawable.enfp);
                break;
            case  "entj":
                random_user_image.setBackgroundResource(R.drawable.entj);
                break;
            case  "entp":
                random_user_image.setBackgroundResource(R.drawable.entp);
                break;
            case  "esfj":
                random_user_image.setBackgroundResource(R.drawable.esfj);
                break;
            case  "esfp":
                random_user_image.setBackgroundResource(R.drawable.esfp);
                break;
            case  "estj":
                random_user_image.setBackgroundResource(R.drawable.estj);
                break;
            case  "estp":
                random_user_image.setBackgroundResource(R.drawable.estp);
                break;
            case  "infj":
                random_user_image.setBackgroundResource(R.drawable.infj);
                break;
            case  "infp":
                random_user_image.setBackgroundResource(R.drawable.infp);
                break;
            case  "intj":
                random_user_image.setBackgroundResource(R.drawable.intj);
                break;
            case  "intp":
                random_user_image.setBackgroundResource(R.drawable.intp);
                break;
            case  "isfj":
                random_user_image.setBackgroundResource(R.drawable.isfj);
                break;
            case  "isfp":
                random_user_image.setBackgroundResource(R.drawable.isfp);
                break;
            case  "istj":
                random_user_image.setBackgroundResource(R.drawable.istj);
                break;
            case  "istp":
                random_user_image.setBackgroundResource(R.drawable.istp);
                break;
            default:
                break;
        }


    }

}
