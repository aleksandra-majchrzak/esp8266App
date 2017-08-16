package org.railwaymen.esp8266app;

import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by ola on 14.08.17. <br/>
 * This class specifies the same methods that we created in ESP module
 */

public interface EspApi {
    @POST("turnOn")
    Observable<ResponseBody> turnLightOn();

    @POST("turnOff")
    Observable<ResponseBody> turnLightOff();

    @POST("setColor")
    Observable<ResponseBody> setLightColor(@Body RGB rgb);
}
