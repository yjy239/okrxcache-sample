package com.yjy.okrxcache;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.yjy.okrxcache.test.ApiService;
import com.yjy.okrxcache_core.rx.core.Cache.CacheStragry;
import com.yjy.okrxcache_core.rx.core.CacheResult;
import com.yjy.okrxcache_core.rx.core.OkRxCache;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button)findViewById(R.id.request);
        Button romve = (Button)findViewById(R.id.remove);
        Button clear = (Button)findViewById(R.id.clear);


                Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.URL_BASE)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
//
        ApiService restApi = retrofit.create(ApiService.class);

        final ApiService proxy = OkRxCache.with(this)
                .setStragry(CacheStragry.ALL)
                .using(ApiService.class)
                .create(restApi);



        restApi.getCommonDict()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<HttpResult<CommonDictResponse.Result>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HttpResult<CommonDictResponse.Result> resultHttpResult) {
                        Log.e("result",resultHttpResult.toString());
                    }
                });



        OkRxCache.with(this).get("222")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Subscriber<CacheResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CacheResult cacheResult) {
                        Log.e("get",cacheResult.getData().toString());
                    }
                });




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proxy.getCommonDict()
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .subscribe(new Observer<HttpResult<CommonDictResponse.Result>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("Throwable",e.toString());
                            }

                            @Override
                            public void onNext(HttpResult<CommonDictResponse.Result> resultHttpResult) {
                                Log.e("result",resultHttpResult.toString());
                            }
                        });
            }
        });




    }



}
