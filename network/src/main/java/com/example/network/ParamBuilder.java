package com.example.network;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Author      :    DongJunJie
 * Date        :    2019/3/1
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public class ParamBuilder {
    public transient Activity mActivity;
    protected transient CompositeDisposable mSubscriptions = new CompositeDisposable();

    public ParamBuilder(Activity var) {
        initActivity(var);
    }

    private void initActivity(Activity var) {
        mActivity = var;
        mActivity.getApplication().registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                if (mActivity == activity) {
                    mActivity.getApplication().unregisterActivityLifecycleCallbacks(this);
                    close();
                }
            }
        });
    }

    public transient Fragment mFragment;

    public ParamBuilder(Fragment var) {
        mFragment = var;
        mActivity = mFragment.getActivity();
        if (mFragment.getFragmentManager() != null) {
            mFragment.getFragmentManager().registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
                @Override
                public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
                    super.onFragmentDestroyed(fm, f);
                    if (f == mFragment) {
                        close();
                    }
                    mFragment.getFragmentManager().unregisterFragmentLifecycleCallbacks(this);
                }
            }, false);
            return;
        }
        if (mFragment.getActivity() != null) {
            initActivity(mFragment.getActivity());
        }
    }

    //尽量用内部管理，避免错误
    @Deprecated
    public ParamBuilder() {
    }

    public void addSubscription(Disposable disposable) {
        mSubscriptions.add(disposable);
    }

    public void close() {
        if (mSubscriptions != null && !mSubscriptions.isDisposed())
            mSubscriptions.dispose();
    }

    @CallSuper
    public HashMap<String, String> buildParamsMap() {
        preBuildParamsMap();
        HashMap<String, String> map = new HashMap<>();
        Field[] allFields = this.getClass().getDeclaredFields();
        for (Field field : allFields) {
            field.setAccessible(true);
            String key = field.getName();
            if ((Modifier.TRANSIENT & field.getModifiers()) > 0) {
                continue;
            }
            Object value = null;
            try {
                value = field.get(this);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (value != null && !TextUtils.isEmpty(value.toString())) {
                map.put(key, value.toString());
            } else if ((Modifier.FINAL & field.getModifiers()) > 0) {
                map.put(key, "");
            }
        }
        return map;
    }

    public void preBuildParamsMap(){

    }


    public static class BaseItemHolder {
        public String value;
        public String nullTipWord;

        public BaseItemHolder value(String value) {
            this.value = value;
            return this;
        }

        public BaseItemHolder nullTipWord(String var) {
            this.nullTipWord = var;
            return this;
        }

        public boolean valueIsNull() {
            return TextUtils.isEmpty(value);
        }


        public BaseItemHolder clear() {
            this.value = null;
            return this;
        }

        @Override
        public String toString() {
            return value;
        }
    }


    public interface BackState<E> {

        void onSuccess(E data);

        void onError(String msg);
    }

    public static <T> void checkState(ResponseMessage<T> message, BackState<T> backState) {
        if (message == null || backState == null) {
            return;
        }
        if (message.statusCode == Constants.NetworkStatusCode.SUCCESS) {
            backState.onSuccess(message.data);
        } else {
            backState.onError(message.statusMessage);
        }
    }


}

