package com.example.myapplication.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.server.LoginData;
import com.example.myapplication.server.RetrofitConnection;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
    TextView edit_login_email, edit_login_password;
    Button btn_login, btn_to_register;
    RetrofitConnection retrofitConnection = new RetrofitConnection();
    String loginName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences pref = getSharedPreferences("pref", Context.MODE_PRIVATE);
        String userId = pref.getString("key1", "");
        if (userId.isEmpty() == false) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        setContentView(R.layout.activity_login);

        ImageView imageView_login = findViewById(R.id.image_login);
        imageView_login.setBackground(new ShapeDrawable(new OvalShape()));
        imageView_login.setClipToOutline(true);

        edit_login_email = (TextView) findViewById(R.id.edit_email);
        edit_login_password = (TextView) findViewById(R.id.edit_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = loginUser(edit_login_email.getText().toString(), edit_login_password.getText().toString());
            }
        });
        btn_to_register = (Button) findViewById(R.id.btn_to_register);
        btn_to_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    private String loginUser(String email, String password) {
        if (email.isEmpty()) {
            Toast.makeText(this, "Email cannot be empty.", Toast.LENGTH_SHORT).show();
            return "fail";
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Password cannot be empty.", Toast.LENGTH_SHORT).show();
            return "fail";
        }

        LoginData data = new LoginData();
        data.setId(email);
        data.setPasswd(password);

        retrofitConnection.server.login(data).enqueue(new Callback<LoginData>() {
            @Override
            public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                if (response.isSuccessful()) {
                    SharedPreferences pref = getSharedPreferences("pref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("key1", response.body().getId());
                    editor.commit();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {

                }
            }

            @Override
            public void onFailure(Call<LoginData> call, Throwable t) {

            }
        });

        return "success";
    }
}