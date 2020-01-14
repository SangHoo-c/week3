package com.example.myapplication.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

public class FragExample4 extends Fragment {

    private Context mContext;
    SharedPreferences pref;
    String accountName;

    TextView textView1;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref = requireContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
        accountName = pref.getString("key1", "");
    }



}
