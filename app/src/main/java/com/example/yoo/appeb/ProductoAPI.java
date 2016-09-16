package com.example.yoo.appeb;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/*import retrofit.Callback;
import retrofit.http.GET;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;*/

/**
 * Created by KissPK on 13/09/2016.
 */

public interface ProductoAPI {

    @GET("/productos.php")
    Call<List<producto>> productsList(
            @Path("idUsuario") String idUsuario);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://127.0.0.1:8080/EasyBWS/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    //public void getProductos(Callback<List<producto>> response);


    /*@GET(“repos/{owner}/{repo}/contributors”)
    Call<List<Contributor>> repoContributors(
            @Path(“owner”) String owner,
            @Path(“repo”) String repo);
*/
}
