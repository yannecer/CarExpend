package com.necer.carexpend.application;
import com.necer.carexpend.bean.CarBrandBean;
import com.necer.carexpend.bean.CarItemBean;
import com.necer.carexpend.bean.HttpResult;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by necer on 2017/3/8.
 */

public interface ApiService {

    @Headers("Cache-Control: max-age=" + Api.CACHE_STALE_SEC)
    @GET("brand")
    Observable<HttpResult<List<CarBrandBean>>> getCarBrandList(@Query("appkey") String appkey);


    @Headers("Cache-Control: max-age=" + Api.CACHE_STALE_SEC)
    @GET("carlist")
    Observable<HttpResult<List<CarItemBean>>> getCarList(@Query("appkey") String appkey, @Query("parentid") String parentid);


    @GET("carlist")
    Observable<String> getCarList2(@Query("appkey") String appkey, @Query("parentid")String parentid);

}
