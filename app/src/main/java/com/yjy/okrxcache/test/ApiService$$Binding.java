package com.yjy.okrxcache.test;

import com.yjy.okexcache_base.LifeCache;
import com.yjy.okrxcache.User;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

/**
 * <pre>
 *     author : yjy
 *     e-mail : yujunyu12@gmail.com
 *     time   : 2018/04/24
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public interface ApiService$$Binding {

    String HEADER_API_VERSION = "Accept: application/vnd.github.v3+json";

    @LifeCache(duaration = 2,unit = TimeUnit.MINUTES,setFromNet = false)
    @Headers({HEADER_API_VERSION})
    @GET("/users")
    Observable<Response<ResponseBody>> getUsers(@Query("since") int lastIdQueried, @Query("per_page") int perPage);
}
