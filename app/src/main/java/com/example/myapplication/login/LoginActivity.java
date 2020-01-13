package com.example.myapplication.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.server.AccountClient;
import com.example.myapplication.server.IRetrofit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
    TextView edit_login_email, edit_login_password;
    Button btn_login, btn_to_register;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IRetrofit iRetrofit;
    String loginName;

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Retrofit accountClient = AccountClient.getInstance();
        iRetrofit = accountClient.create(IRetrofit.class);

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
        compositeDisposable.add(iRetrofit.loginUser(email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<String>() {
                @Override
                public void accept(String response) throws Exception {
//                    Toast.makeText(LoginActivity.this, "" + response, Toast.LENGTH_SHORT).show();
                    loginName = response.replace("\"", "");
                    Log.d("12312312", loginName);

                    SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("Name", loginName);
                    editor.commit();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("Name", loginName);
                    startActivity(intent);
                }
            }));
        return "success";
    }
}