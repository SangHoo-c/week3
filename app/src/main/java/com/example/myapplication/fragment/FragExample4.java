package com.example.myapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.login.LoginActivity;
import com.example.myapplication.server.MbtiData;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FragExample4 extends Fragment {

    private Context mContext;
    SharedPreferences pref;
    String accountName;
    //accountName 을 기준으로 유저 디비에서 mbti 성격을 가져온다

    //MBTI 유사도 비교
//    String user_mbti ="ENFJ";
//    String random_mbti = "ENTP";

    TextView textView1;


    //db 에서 가져온 데이터를 여기서 사용해야한다.
    String[] user_mbti_past = new String[]{"INFJ", "ENFJ", "ENFJ", "INFJ", "INFJ"};
    //enfj : 0, enfp : 1 , entj :  2,
    // entp : 3 , esfj  : 4 , esfp : 5, estj : 6,
    // estp : 7 , infj : 8,  infp : 9 ,
    //intj : 10, intp : 11,  isfj : 12,
    // isfp : 13,  istj : 14 , istp : 15
    // 순서대로 인덱스를 가진다

    //index 당 해당되는 숫자를 저장하기 위한 배열 16 개 초기화
    final int[] user_mbti_num ={0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    final String[] user_mbti_string = {
            "enfj", "enfp", "entj",
            "entp", "esfj", "esfp", "estj",
            "estp", "infj", "infp",
            "intj", "intp", "isfj",
            "isfp", "istj", "istp"
    };

    private String cmp_mbti="";
//    List<MbtiData> user_mbti_past_list;

    PieChart pieChart;
    private ImageView user_image;
    private TextView user_mbti, user_naem,  user_id, extra_text;
    private int max_cnt =-100;
    private int idx_cnt = -1;
    private String idx_cnt_string ="";

    Button logout_button, share_button;
    private RelativeLayout container2;

    private View rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref = requireContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
        accountName = pref.getString("key1", "");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_frag_ex4, null);

        logout_button = rootView.findViewById(R.id.logout);
        share_button = rootView.findViewById(R.id.share);

        logout_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SharedPreferences pref = requireContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.remove("key1");
                editor.commit();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });

        container2 = (RelativeLayout)rootView.findViewById(R.id.for_share);
        share_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "hi", Toast.LENGTH_SHORT).show();

                container2.buildDrawingCache();
                Bitmap captureView = container2.getDrawingCache();
                FileOutputStream fos;
                String strFolderPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/DCIM/CAPTURE";
                File folder = new File(strFolderPath);
                if(!folder.exists()){
                    folder.mkdirs();
                }
                String strFilePath = strFolderPath + "/" + System.currentTimeMillis() + ".png";
                File fileCacheItem = new File(strFilePath);

                try{
                    fos = new FileOutputStream(fileCacheItem);
                    captureView.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    Log.v("check 4 ss : ", "hi");
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }finally {
                    Toast.makeText(getActivity(), "캡처완료!", Toast.LENGTH_SHORT).show();
                }

                Intent share = new Intent(Intent.ACTION_SEND_MULTIPLE);
                Uri uri = Uri.fromFile(fileCacheItem);
                Log.v("sharing_intent_name: ", uri.toString());
                share.putExtra(Intent.EXTRA_STREAM, uri);
                share.putExtra(Intent.EXTRA_TEXT, uri.toString());
                share.setType("image/*");
                share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                startActivity(Intent.createChooser(share, "Share image using"));
                Log.v("check 3 ss : ", "hi");
            }
        });

        user_mbti = rootView.findViewById(R.id.random_user_mbti);
        user_naem = rootView.findViewById(R.id.random_user_name);
        user_id = rootView.findViewById(R.id.random_user_id);
        user_image = rootView.findViewById(R.id.random_user_image);
        extra_text = rootView.findViewById(R.id.extra_text);
        extra_text.setText("과거 데이터를 합친 \n분석결과 입니다.");

        user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Intent.ACTION_VIEW);
                Uri uri =Uri.parse("https://www.16personalities.com/personality-types");
                intent2.setData(uri);
                getActivity().startActivity(intent2);
            }
        });

        pieChart = rootView.findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);

        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();

        //배열을 참조하며, 자신의 mbti 갯수를 센다
        for(int i=0; i< user_mbti_past.length; i++){
            cmp_mbti = user_mbti_past[i].toLowerCase();
            Log.d("checking...mbti", cmp_mbti);
            switch (cmp_mbti){
                case  "enfj":
                    user_mbti_num[0] ++;
                    break;
                case  "enfp":
                    user_mbti_num[1] ++;
                    break;
                case  "entj":
                    user_mbti_num[2] ++;
                    break;
                case  "entp":
                    user_mbti_num[3] ++;
                    break;
                case  "esfj":
                    user_mbti_num[4] ++;
                    break;
                case  "esfp":
                    user_mbti_num[5] ++;
                    break;
                case  "estj":
                    user_mbti_num[6] ++;
                    break;
                case  "estp":
                    user_mbti_num[7] ++;
                    break;
                case  "infj":
                    user_mbti_num[8] ++;
                    break;
                case  "infp":
                    user_mbti_num[9] ++;
                    break;
                case  "intj":
                    user_mbti_num[10] ++;
                    break;
                case  "intp":
                    user_mbti_num[11] ++;
                    break;
                case  "isfj":
                    user_mbti_num[12] ++;
                    break;
                case  "isfp":
                    user_mbti_num[13] ++;
                    break;
                case  "istj":
                    user_mbti_num[14] ++;
                    break;
                case  "istp":
                    user_mbti_num[15] ++;
                    break;
                default:
                    break;
            }
        }
        Log.d("checking_max_indx", String.valueOf(max_cnt));

        for(int i=0; i<user_mbti_num.length; i++){
            if(user_mbti_num[i] !=0){
                yValues.add(new PieEntry(user_mbti_num[i],user_mbti_string[i]));
            }
        }

        //배열을 참조하며, 가장 빈도수가 높은 mbti 갯수를 센다.
        for(int i=0; i<user_mbti_num.length; i++){
            if(max_cnt < user_mbti_num[i]){
                max_cnt = user_mbti_num[i];
                idx_cnt = i;
            }
            Log.d("checking_max_indx2", String.valueOf(idx_cnt));
        }
        //빈도수가 가장 높은 인덱스인 idx_cnt 를 바탕으로 데이터를 넣어준다.
        user_mbti.setText(user_mbti_string[idx_cnt].toUpperCase());

        //같은 방법으로 이미지를 넣어준다.
        idx_cnt_string = user_mbti_string[idx_cnt];
        switch (idx_cnt_string){
            case  "enfj":
                user_image.setImageResource(R.drawable.enfj);
                break;
            case  "enfp":
                user_image.setImageResource(R.drawable.enfp);
                break;
            case  "entj":
                user_image.setImageResource(R.drawable.entj);
                break;
            case  "entp":
                user_image.setImageResource(R.drawable.entp);
                break;
            case  "esfj":
                user_image.setImageResource(R.drawable.esfj);
                break;
            case  "esfp":
                user_image.setImageResource(R.drawable.esfp);
                break;
            case  "estj":
                user_image.setImageResource(R.drawable.estj);
                break;
            case  "estp":
                user_image.setImageResource(R.drawable.estp);
                break;
            case  "infj":
                user_image.setImageResource(R.drawable.infj);
                break;
            case  "infp":
                user_image.setImageResource(R.drawable.infp);
                break;
            case  "intj":
                user_image.setImageResource(R.drawable.intj);
                break;
            case  "intp":
                user_image.setImageResource(R.drawable.intp);
                break;
            case  "isfj":
                user_image.setImageResource(R.drawable.isfj);
                break;
            case  "isfp":
                user_image.setImageResource(R.drawable.isfp);
                break;
            case  "istj":
                user_image.setImageResource(R.drawable.istj);
                break;
            case  "istp":
                user_image.setImageResource(R.drawable.istp);
                break;
            default:
                break;
        }

//        Description description = new Description();
//        description.setText("카톡데이터로 보는 나의 성향"); //라벨
//        description.setTextSize(15);
//        pieChart.setDescription(description);

        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic); //애니메이션

        PieDataSet dataSet = new PieDataSet(yValues,"");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);

        pieChart.setData(data);

        return rootView;
    }


}
