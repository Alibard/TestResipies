package bom.dom.com.recipe.rest;

import bom.dom.com.recipe.DetailInfo;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by anton on 10.06.16.
 */
public interface Rest {
    @GET("api/get")
    Call<DetailInfoModel> geiItemInfo(@Query("rId") String id,
                                 @Query("key") String key);

    @GET("/api/search")
    Call<PageModel> getPageItem(@Query("key") String key,
                                   @Query("page") String page,
                                   @Query("sort") String sort,
                                   @Query("q") String q);
}
