package com.yjy.okrxcache;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.yjy.okrxcache.rx.core.OkRxCache;
import com.yjy.okrxcache.rx.core.ProcessHandler;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OkRxCache cache = new OkRxCache.Builder()
                .setCacheDir("111")
                .using(ApiService.class)
                .build();

//        ApiService api = new ApiService() {
//            @Override
//            public Observable<Integer> getHouseListByBuilding(String projectCode) {
//                return null;
//            }
//        };
//        ProcessHandler handler = new ProcessHandler(api);
//        ProcessHandler2 handler2 = new ProcessHandler2(api);
//        ApiService a = (ApiService) Proxy.newProxyInstance(ApiService.class.getClassLoader(),new Class<?>[]{ApiService.class},handler);
//        ApiService a2 = (ApiService) Proxy.newProxyInstance(a.getClass().getClassLoader(),new Class<?>[]{ApiService.class},handler2);
//        a2.getHouseListByBuilding("111");

        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(333);
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.e("num",""+integer);
            }
        });

        ApiService restApi = new Retrofit.Builder()
                .baseUrl(ApiService.URL_BASE)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiService.class);

        ApiService proxy = cache.create(restApi);

        proxy.getUsers(1,1)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Subscriber<List<User>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error",e.toString());
                    }

                    @Override
                    public void onNext(List<User> users) {
                        Log.e("num",""+users.toString());
                    }
                });


//       cache.excute(proxy.getUsers(1,1))
//               .subscribeOn(Schedulers.io())
//               .observeOn(Schedulers.io())
//               .subscribe(new Subscriber<ArrayList>() {
//           @Override
//           public void onCompleted() {
//
//           }
//
//           @Override
//           public void onError(Throwable e) {
//               Log.e("error",e.toString());
//           }
//
//           @Override
//           public void onNext(ArrayList o) {
//               Log.e("num",""+o.toString());
//           }
//       });


    }

    public Observable<String> valueObservable() {
        return Observable.defer(new Func0<Observable<String>>() {
            @Override public Observable<String> call() {
                return Observable.just("11");
            }
        });
    }

}