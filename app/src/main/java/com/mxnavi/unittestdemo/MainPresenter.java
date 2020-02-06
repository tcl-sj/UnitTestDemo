package com.mxnavi.unittestdemo;

/**
 * Created by jian.shi on 2020/2/5.
 *
 * @Email shijian1@siasun.com
 */
public class MainPresenter implements MainActivityContract.Presenter {
    private final static String USER_NAME = "test";
    private final static String PWD = "123456";

    private MainActivityContract.View mView;

    private boolean isLogin = false;

    public MainPresenter(MainActivityContract.View view) {
        mView = view;
    }

    @Override
    public void doLogin(String userName, String pwd) {
        if (checkUserName(userName) && checkPwd(pwd)) {
            mView.onLogin(true);
        } else {
            mView.onLogin(false);
        }
    }

    private boolean checkUserName(String userName) {
        if (!isEmpty(userName) && userName.equals(USER_NAME)) {
            return true;
        }
        return false;
    }

    private boolean checkPwd(String pwd) {
        if (!isEmpty(pwd) && pwd != null && pwd.equals(PWD)) {
            return true;
        }
        return false;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
}
