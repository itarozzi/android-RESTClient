package it.proesys.restclient;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ivan on 30/01/18.
 */

public interface ProductAPI {
    @GET("/xml")
    Call<XmlTestData> getProducts();
}
