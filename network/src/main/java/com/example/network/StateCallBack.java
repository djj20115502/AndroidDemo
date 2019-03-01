package com.example.network;

/**
 * Author      :    DongJunJie
 * Date        :    2019/3/1
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public interface StateCallBack<T> {
    void onError(int errorCode, String msg, Object... other);

    void onSuccess(T data);

    void onLoading(String msg);
}
