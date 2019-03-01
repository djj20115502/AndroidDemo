package com.example.network;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Author      :    DongJunJie
 * Date        :    2019/3/1
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public class HttpFactory {


    public static <T> T create(Class<T> t) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(OkHttpClientBuilder.getOkHttpClientBuilder())
                .build();
        return retrofit.create(t);
    }

    public static <T> void execute(Observable<T> t, Observer<T> t1) {

        t.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(t1);
    }
}
