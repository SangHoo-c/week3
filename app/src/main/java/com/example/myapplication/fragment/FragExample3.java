package com.example.myapplication.fragment;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.server.LoginData;
import com.example.myapplication.server.MbtiData;
import com.example.myapplication.server.RetrofitConnection;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class FragExample3 extends Fragment {

    private Context mContext;
    private SharedPreferences pref;
    String accountName;
    private TextView mbtiView;
    private Button testMBTI;
    private Uri kakaoUri = null;

    PieChart pieChart;

    String  i_ration, e_ration,s_ration,n_ration,t_ration,f_ration,j_ration,p_ration;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref = requireContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
        accountName = pref.getString("key1", "");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_frag_ex1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mbtiView = view.findViewById(R.id.mbti);
        testMBTI = view.findViewById(R.id.getChat);

        Activity activity = getActivity();
        Intent intent = activity.getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        pieChart = view.findViewById(R.id.piechart_1);

        testMBTI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (kakaoUri == null) {
                        Toast.makeText(requireContext(), "먼저 카카오톡에서 텍스트 파일 내보내기를 통해 파일을 가져와 주세요!", Toast.LENGTH_LONG).show();
                        return;
                    }
                    InputStream is = getContext().getContentResolver().openInputStream(kakaoUri);
                    kakaoUri = null;
                    File f = new File(getContext().getExternalCacheDir(), "tmp");
                    OutputStream os = new FileOutputStream(f);

                    byte[] b = new byte[2048];
                    int length;

                    while ((length = is.read(b)) != -1) {
                        os.write(b, 0, length);
                    }

                    is.close();
                    os.close();

                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), f);

                    MultipartBody.Part body = MultipartBody.Part.createFormData("tmp", f.getName(), requestBody);

                    RetrofitConnection retrofitConnection = new RetrofitConnection();
                    retrofitConnection.server.testMbti(accountName, body).enqueue(new Callback<MbtiData>() {
                        @Override
                        public void onResponse(Call<MbtiData> call, Response<MbtiData> response) {

                            if (!response.isSuccessful()) {
                                Log.d("response not successful", response.toString());
                                setView(view);
                                return;
                            }
                            setView(view);
                        }
                        @Override
                        public void onFailure(Call<MbtiData> call, Throwable t) {
                            Log.d("on Failure", t.toString());
                            setView(view);
                        }
                    });

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        kakaoUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
        setView(view);

    }

    private void setView(View view) {
        RetrofitConnection retrofitConnection = new RetrofitConnection();
        retrofitConnection.server.getUser(accountName).enqueue(new Callback<LoginData>() {
            @Override
            public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                if (response.isSuccessful()) {

                    List<MbtiData> mbti = response.body().getMbti();

                    if (!mbti.isEmpty()) {
                        mbtiView.setText(mbti.get(mbti.size() - 1).getType());

                        pieChart.setUsePercentValues(true);
                        pieChart.getDescription().setEnabled(false);
                        pieChart.setExtraOffsets(5, 10, 5, 5);

                        pieChart.setDragDecelerationFrictionCoef(0.95f);

                        pieChart.setDrawHoleEnabled(false);
                        pieChart.setHoleColor(Color.WHITE);
                        pieChart.setTransparentCircleRadius(61f);

                        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();

                        LoginData data = response.body();
                        if (data.getMbti().size() != 0) {
                            i_ration = data.getMbti().get(mbti.size() - 1).getRatio_I();
                            e_ration = String.valueOf((100 - Integer.parseInt(i_ration)));

                            n_ration = data.getMbti().get(mbti.size() - 1).getRatio_N();
                            s_ration = String.valueOf((100 - Integer.parseInt(n_ration)));

                            j_ration = data.getMbti().get(mbti.size() - 1).getRatio_J();
                            p_ration = String.valueOf((100 - Integer.parseInt(j_ration)));

                            t_ration = data.getMbti().get(mbti.size() - 1).getRatio_T();
                            f_ration = String.valueOf((100 - Integer.parseInt(t_ration)));
                        }

                        yValues.add(new PieEntry(Integer.parseInt(i_ration), "I"));
                        yValues.add(new PieEntry(Integer.parseInt(e_ration), "E"));
                        yValues.add(new PieEntry(Integer.parseInt(s_ration), "S"));
                        yValues.add(new PieEntry(Integer.parseInt(n_ration), "N"));
                        yValues.add(new PieEntry(Integer.parseInt(t_ration), "T"));
                        yValues.add(new PieEntry(Integer.parseInt(f_ration), "F"));
                        yValues.add(new PieEntry(Integer.parseInt(j_ration), "J"));
                        yValues.add(new PieEntry(Integer.parseInt(p_ration), "P"));


                        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic); //애니메이션

                        PieDataSet dataSet = new PieDataSet(yValues, "");
                        dataSet.setSliceSpace(3f);
                        dataSet.setSelectionShift(5f);
                        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

                        PieData data2 = new PieData((dataSet));
                        data2.setValueTextSize(10f);
                        data2.setValueTextColor(Color.GRAY);

                        pieChart.setData(data2);
                    }

                }

            }

            @Override
            public void onFailure(Call<LoginData> call, Throwable t) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK) {

            Log.d("tlqkf", data.getData().getPath());
            File f = new File(getContext().getExternalCacheDir(), "tmp");
            try {
                f.createNewFile();
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), f);

                MultipartBody.Part body = MultipartBody.Part.createFormData("tmp", f.getName(), requestBody);

                RetrofitConnection retrofitConnection = new RetrofitConnection();
                retrofitConnection.server.testMbti(accountName, body).enqueue(new Callback<MbtiData>() {
                    @Override
                    public void onResponse(Call<MbtiData> call, Response<MbtiData> response) {
                        if (!response.isSuccessful()) {
                            Log.d("response not successful", response.toString());
                            return;
                        }
                        MbtiData mbtiData = response.body();
                        mbtiView.setText(mbtiData.getType());
                    }

                    @Override
                    public void onFailure(Call<MbtiData> call, Throwable t) {
                        Log.d("on Failure", t.toString());
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

}
