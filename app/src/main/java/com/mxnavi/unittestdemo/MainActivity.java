package com.mxnavi.unittestdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View,
        View.OnClickListener {
    private static final String TAG = "MainActivity";
    
    private EditText mEtUserName;
    private EditText mEtPwd;
    private Button mBtnLogin;
    private MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mPresenter = new MainPresenter(this);
    }

    private void initView() {
        mEtUserName = findViewById(R.id.et_user_name);
        mEtPwd = findViewById(R.id.et_pwd);
        mBtnLogin = findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                mPresenter.doLogin(mEtUserName.getText().toString(), mEtPwd.getText().toString());
                break;
        }
    }

    @Override
    public void onLogin(boolean login) {
        Log.d(TAG, "onLogin: ");
    }
}
