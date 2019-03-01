package com.example.network;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.util.JSONPObject;

import java.lang.ref.WeakReference;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Author      :    DongJunJie
 * Date        :    2019/3/1
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
public class Test {
    public static class RewardApi {

        /**
         * 荣耀榜单service
         *
         * @return
         */
        public static synchronized RewardApi.API getService() {
            if (sAPI == null || sAPI.get() == null) {
                RewardApi.API api = HttpFactory.create(RewardApi.API.class);
                sAPI = new WeakReference<>(api);
            }
            return sAPI.get();
        }

        static WeakReference<RewardApi.API> sAPI;


        public interface API {



            @GET("car/factory/lists")
            Observable<ResponseMessage<List<Bean>>> getBrandList(@Query("vehicle_type") String type, @Query("is_need_all") String isNeedAll);


        }
    }


    public static class TestParamBuilder extends ParamBuilder {

        public TestParamBuilder(Fragment var) {
            super(var);
        }

        /**
         * http://car1.i.cacf.cn/car/factory/lists?vehicle_type=moto&is_need_all=1
         * 厂商品牌
         */
        public void getBrandList(String type, final StateCallBack<List<Bean>> callBack) {
            HttpFactory.execute(Test.RewardApi.getService().getBrandList(type, "1"), new Observer<ResponseMessage<List<Bean>>>() {
                @Override
                public void onComplete() {

                }

                @Override
                public void onSubscribe(Disposable disposable) {
                    mSubscriptions.add(disposable);
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                    callBack.onError(0, e.getMessage());
                }

                @Override
                public void onNext(ResponseMessage<List<Bean>> objectResponseMessage) {
                    Log.e("objectResponseMessage",objectResponseMessage.md5);
                    if (objectResponseMessage.statusCode == Constants.NetworkStatusCode.SUCCESS) {
                        callBack.onSuccess(objectResponseMessage.data);
                    } else {
                        callBack.onError(0, objectResponseMessage.statusMessage);
                    }
                }
            });
        }

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Bean  {


        /**
         * brand_logo : /moto/1702/08/362e83b01c04771714be6fa4deb355f2.jpg
         * brand_id : 16
         * brand_name : 重古思特
         * letter : A
         */

        @JsonProperty("brand_logo")
        public String brandLogo;
        @JsonProperty("brand_id")
        public int brandId;
        @JsonProperty("brand_name")
        public String brandName;
        @JsonProperty("letter")
        public String letter;




        transient public boolean isSelected;



        @Override
        public String toString() {
            return "Bean{" +
                    "brandLogo='" + brandLogo + '\'' +
                    ", brandId=" + brandId +
                    ", brandName='" + brandName + '\'' +
                    ", letter='" + letter + '\'' +
                     '}';
        }
    }

}
