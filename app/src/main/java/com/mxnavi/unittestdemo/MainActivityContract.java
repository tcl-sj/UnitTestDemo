package com.mxnavi.unittestdemo;

/**
 * Created by jian.shi on 2020/2/5.
 *
 * @Email shijian1@siasun.com
 */
public interface MainActivityContract {
    interface View {
        void onLogin(boolean login);
    }

    interface Presenter {
        void doLogin(String userName, String pwd);
    }
}
