package com.example.network;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Author      :    DongJunJie
 * Date        :    2019/3/1
 * E-mail      :    dongjunjie.mail@qq.com
 * Description :
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseMessage<T> implements Serializable {
    @JsonProperty("code")
    public int    statusCode;

    @JsonProperty("msg")
    public String statusMessage;

    @JsonProperty("md5")
    public String md5;

    @JsonProperty("data")
    public T      data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseMessage{" + "statusCode=" + statusCode + ", statusMessage='" + statusMessage
                + '\'' + ", data=" + data + '}';
    }
}