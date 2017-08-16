package org.railwaymen.esp8266app;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by ola on 16.08.17.<br/>
 * This class creates the connection with server and handles all the requests.
 */

public class EspService {

    private static EspService instance;
    private EspApi api;
    private static final String BASE_URL = "http://___.___.___.___/";   // your module IP

    private EspService() {
        api = createApi();
    }

    public static EspService getInstance() {
        if (instance == null)
            instance = new EspService();

        return instance;
    }

    private EspApi createApi() {
        if (api == null) {

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            api = retrofit.create(EspApi.class);
        }

        return api;
    }

    public Subscription turnLightOn(final OnLedChangedListener listener) {
        return api.turnLightOn()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        listener.onLedOn();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        listener.onErrorOccurred();
                    }
                });
    }

    public Subscription turnLightOff(final OnLedChangedListener listener) {
        return api.turnLightOff()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        listener.onLedOff();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        listener.onErrorOccurred();
                    }
                });
    }

    public Subscription setLightColor(int r, int g, int b, final OnLedChangedListener listener) {
        return api.setLightColor(new RGB(r, g, b))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        listener.onErrorOccurred();
                    }
                });
    }
}
