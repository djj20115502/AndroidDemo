package com.example.network;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Author      :    DongJunJie
 * Date        :    2019/3/7
 * E-mail      :    dongjunjie.mail@qq.com
 * Description : {@link retrofit2.converter.jackson.JacksonConverterFactory}
 */
public class TestConverFactory extends Converter.Factory {
    /**
     * Create an instance using a default {@link ObjectMapper} instance for conversion.
     */
    public static TestConverFactory create() {
        return create(new ObjectMapper());
    }

    /**
     * Create an instance using {@code mapper} for conversion.
     */
    @SuppressWarnings("ConstantConditions") // Guarding public API nullability.
    public static TestConverFactory create(ObjectMapper mapper) {
        if (mapper == null) throw new NullPointerException("mapper == null");
        return new TestConverFactory(mapper);
    }

    private final ObjectMapper mapper;

    private TestConverFactory(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        JavaType javaType = mapper.getTypeFactory().constructType(type);
        ObjectReader reader = mapper.readerFor(javaType);
        return new JacksonResponseBodyConverter<>(reader);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        JavaType javaType = mapper.getTypeFactory().constructType(type);
        ObjectWriter writer = mapper.writerFor(javaType);
        return new JacksonRequestBodyConverter<>(writer);
    }

    static class JacksonRequestBodyConverter<T> implements Converter<T, RequestBody> {
        private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

        private final ObjectWriter adapter;

        JacksonRequestBodyConverter(ObjectWriter adapter) {
            this.adapter = adapter;
        }

        @Override
        public RequestBody convert(T value) throws IOException {
            byte[] bytes = adapter.writeValueAsBytes(value);
            return RequestBody.create(MEDIA_TYPE, bytes);
        }
    }

    class JacksonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private final ObjectReader adapter;

        JacksonResponseBodyConverter(ObjectReader adapter) {
            this.adapter = adapter;
        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            try {
                BufferedReader in = new BufferedReader(value.charStream());
                StringBuffer buffer = new StringBuffer();
                String line;
                while ((line = in.readLine()) != null) {
                    buffer.append(line);
                }
                String string = buffer.toString();
                T rt = adapter.readValue(string);
                addMD5(rt, string);
                return rt;
            } finally {
                value.close();
            }
        }

        private void addMD5(T t, String value) {
            if (!(t instanceof ResponseMessage)) {
                return;
            }
            ResponseMessage responseMessage = (ResponseMessage) t;
            responseMessage.data.getClass();
            NeedMd5 ca = responseMessage.data.getClass().getAnnotation(NeedMd5.class);
            if (ca == null) {
                HttpLog.log("TestConverFactory", "NeedMd5  false");
                return;
            }
            String md5 = HttpFactory.md5(value);
            responseMessage.md5 = md5;
            HttpLog.log("TestConverFactory", "md5 ", md5);
        }


    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface NeedMd5 {
    }


}



