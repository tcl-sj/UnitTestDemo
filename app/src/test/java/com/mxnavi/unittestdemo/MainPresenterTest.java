package com.mxnavi.unittestdemo;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by jian.shi on 2020/2/5.
 *
 * @Email shijian1@siasun.com
 * <p>
 * 一个JUnit4的单元测试用例执行顺序为：
 * @BeforeClass -> @Before -> @Test -> @After -> @AfterClass;
 * <p>
 * 每一个测试方法的调用顺序为：@Before -> @Test -> @After;
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(MainPresenter.class)
public class MainPresenterTest {

    @Mock
    private MainActivityContract.View mView;

    @InjectMocks
    private MainPresenter mPresenter;

    @Captor
    private ArgumentCaptor<Boolean> captor;

    //针对所有测试，只执行一次，且必须为static void
    @BeforeClass
    public static void init() {
        System.out.println("init() called");
    }

    //针对所有测试，每个测试方法执行前都会执行一次
    @Before
    @Test
    public void setUp() throws Exception {
        System.out.println("setUp() called");
        MockitoAnnotations.initMocks(this);
    }

    /**
     * 测试登录接口
     * {@link MainPresenter#doLogin(String, String)}
     */
    @Test
    public void testDoLoginNormal() throws Exception {
        System.out.println("doLogin() called");
        mPresenter.doLogin("1123", "123");
        Mockito.verify(mView, Mockito.times(1)).onLogin(captor.capture());
        System.out.println(Arrays.toString(captor.getAllValues().toArray()));
        Assert.assertFalse(captor.getValue());
    }

    /**
     * 测试登录接口{@link MainPresenter#doLogin(String, String)}，使用powermockito修改preivate方法返回值
     */
    @Test
    public void testDoLoginMockPrivate() throws Exception {
        System.out.println("checkAccount() called");
        MainPresenter mainPresenter = PowerMockito.spy(mPresenter);
        PowerMockito.doCallRealMethod().when(mainPresenter, "doLogin", Mockito.any(String.class),
                Mockito.any(String.class));
        PowerMockito.when(mainPresenter, "checkUserName", Mockito.any(String.class)).thenReturn(true);
        PowerMockito.when(mainPresenter, "checkPwd", Mockito.any(String.class)).thenReturn(true);
        mainPresenter.doLogin("1123", "123");
        Mockito.verify(mView, Mockito.times(1)).onLogin(captor.capture());
        System.out.println(Arrays.toString(captor.getAllValues().toArray()));
        Assert.assertTrue(captor.getValue());
    }

    /**
     * 测试私有方法{@link MainPresenter#checkUserName(String)}
     */
    @Test
    public void testCheckUserName() throws Exception {
        MainPresenter mainPresenter = PowerMockito.spy(mPresenter);
        Method method = PowerMockito.method(MainPresenter.class, "checkUserName", String.class);
        boolean result = (boolean) method.invoke(mainPresenter, "11");
        Assert.assertFalse(result);
    }

    /**
     * 测试静态有方法{@link MainPresenter#isEmpty(String)} }
     */
    @Test
    public void testIsEmpty() {
        PowerMockito.mockStatic(MainPresenter.class);
        PowerMockito.when(MainPresenter.isEmpty(Mockito.anyString())).thenReturn(false);
        Assert.assertFalse(MainPresenter.isEmpty(""));
        PowerMockito.verifyStatic(MainPresenter.class, Mockito.times(1));
    }

    /**
     * 测试私有成员变量{@link MainPresenter#isLogin()} }
     */
    @Test
    public void testIsLogin() {
        Whitebox.setInternalState(mPresenter, "isLogin", true);
        Assert.assertTrue(mPresenter.isLogin());
    }

    //针对所有测试，每个测试方法执行结束后都会执行一次
    @After
    public void tearDown() throws Exception {
    }

    //针对所有测试，执行结束后只执行一次，且必须为static void
    @AfterClass
    public static void end() {

    }
}