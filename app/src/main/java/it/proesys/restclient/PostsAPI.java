package it.proesys.restclient;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by ivan on 30/01/18.
 */

public interface PostsAPI {
    @GET("/posts/{id}")
    Call<JsonTestData>  getPost(@Path("id") int id);
}
