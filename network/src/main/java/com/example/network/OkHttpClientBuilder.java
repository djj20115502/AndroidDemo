package com.example.network;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

/**
 * Author      :    DongJunJie
 * Date        :    2019/3/1
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public class OkHttpClientBuilder {
    static OkHttpClient client;
    private static int TIME_OUT = 30 * 1000;

    public static OkHttpClient getOkHttpClientBuilder() {
        if (client == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            try {
                final X509TrustManager trustManager = new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {

                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                };
                SSLContext sslContext = null;
                sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, new TrustManager[]{trustManager}, new SecureRandom());
                SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
                builder.sslSocketFactory(sslSocketFactory, trustManager)
                        .hostnameVerifier(new HostnameVerifier() {
                            @Override
                            public boolean verify(String hostname, SSLSession session) {
                                return true;
                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }

            builder.connectTimeout(TIME_OUT, java.util.concurrent.TimeUnit.MILLISECONDS);
            builder.readTimeout(TIME_OUT, java.util.concurrent.TimeUnit.MILLISECONDS);
            builder.writeTimeout(TIME_OUT, java.util.concurrent.TimeUnit.MILLISECONDS);
//        SSLSocketFactory sslSocketFactory=  SSLSocketFactory.getSocketFactory();
//        sslSocketFactory.setHostnameVerifier(new AllowAllHostnameVerifier());
//        mAsyncHttpClient.setSSLSocketFactory(sslSocketFactory);
            //        OK_BUILD.cache(new Cache())
            builder.addInterceptor(new RequestInterceptor());
            client = builder.build();
        }
        return client;
    }
}
