package com.developervishalsehgal.constraintlayoutchallenge.helper;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dafidzeko on 5/11/2016.
 */
public class RestManager {

    private apidata data ;

    public static <S> S createService(Class<S> serviceClass) {

        OkHttpClient httpClient=new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
               .baseUrl("http://webapiregister.000webhostapp.com/tugas_petshop/")
   //             .baseUrl("http://mydeveloper.id/tugas_petshop/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient).build();

        return retrofit.create(serviceClass);

    }

    private OkHttpClient getRequestHeader() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS).cache(null)
                .build();
        return okHttpClient;
    }

    public apidata ambil_data(){
        if(data==null){

            Retrofit retrofit =new Retrofit.Builder()
                    .baseUrl(Constant.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getRequestHeader())
                    .build();


            data = retrofit.create(apidata.class);

        }
        return data;
    }
}
